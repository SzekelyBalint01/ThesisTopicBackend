package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.dto.*;
import hu.pte.thesistopicbackend.model.*;
import hu.pte.thesistopicbackend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupConnectToUserRepository groupConnectToUserRepository;

    private final ItemConnectToGroupRepository itemConnectToGroupRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private final ItemService itemService;

    private final PaymentService paymentService;

    public GroupService(GroupRepository groupRepository,
                        GroupConnectToUserRepository groupConnectToUserRepository, ItemConnectToUserRepository itemConnectToUser,
                        ItemConnectToGroupRepository itemConnectToGroupRepository, ItemRepository itemRepository,
                        UserRepository userRepository, ItemService itemService, PaymentService paymentService) {
        this.groupRepository = groupRepository;
        this.groupConnectToUserRepository = groupConnectToUserRepository;
        this.itemConnectToGroupRepository = itemConnectToGroupRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemService = itemService;
        this.paymentService = paymentService;
    }

    public GroupDto createGroup(NewGroupDto newGroupDto, Long userId) {
        Group group = Group.builder()
                .groupName(newGroupDto.getGroupName())
                .build();
        groupRepository.save(group);

        GroupConnectToUserKey groupConnectToUserKey = new GroupConnectToUserKey(group.getId(), userId);

        GroupConnectToUser newConnection = GroupConnectToUser.builder()
                .id(groupConnectToUserKey)
                .user(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new))
                .group(group)
                .build();

        groupConnectToUserRepository.save(newConnection);


        Group newGroup = groupRepository.findById(group.getId()).orElseThrow(EntityNotFoundException::new);

        return GroupDto.builder()
                .id(newGroup.getId())
                .groupName(newGroup.getGroupName())
                .build();
    }

    public GroupDto getGroupById(Long groupId) {

        Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);

        return GroupDto.builder()
                .id(group.getId())
                .groupName(group.getGroupName())
                .items(group.getItemConnectToGroup().stream().map(ItemConnectToGroup::getItem).map(this::mapItemToItemDTO).toList())
                .build();
    }

    public ItemDto mapItemToItemDTO(Item item) {

        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .currency(item.getCurrency())
                .mapUrl(item.getMapUrl())
                .description(item.getDescription())
                .paid(item.getPaid())
                .build();
    }

    public List<GroupDto> getAllGroupByUserId(Long userId) {

        List<GroupConnectToUser>  groupConnectToUserList = groupConnectToUserRepository.findByUserId(userId);

        List<GroupDto> groupDtoList;

        groupDtoList = groupConnectToUserList.stream().map(groupConnectToUser -> GroupDto.builder()
                .groupName(groupConnectToUser.getGroup().getGroupName())
                .id(groupConnectToUser.getId().getGroupId())
                .build()).collect(Collectors.toList());

        return groupDtoList;
    }


    public String inviteUser(InviteUserDto inviteUserDto) {

        Optional<User> user = userRepository.findByEmail(inviteUserDto.getEmail());

        boolean exists = groupConnectToUserRepository.existsByGroupIdAndUserId((long) inviteUserDto.getGroupId(), user.get().getId());

        if (user.isPresent() && exists == false) {

            GroupConnectToUserKey groupConnectToUserKey = new GroupConnectToUserKey((long) inviteUserDto.getGroupId(), user.get().getId());
            GroupConnectToUser groupConnectToUser = GroupConnectToUser.builder()
                    .id(groupConnectToUserKey)
                    .user(user.get())
                    .group(groupRepository.findById((long) inviteUserDto.getGroupId()).orElseThrow(EntityNotFoundException::new))
                    .build();
            groupConnectToUserRepository.save(groupConnectToUser);
            return "Invite successful";
        } else {
            return "User does not exists or already exists";
        }
    }

    public List<GroupUser> getAllUserByGroupId(InviteUserDto inviteUserDto) {

        ArrayList<GroupConnectToUser> groupConnectToUsers = groupConnectToUserRepository.findUsersByGroupId((long) inviteUserDto.getGroupId());

        return getUserList(groupConnectToUsers, inviteUserDto.getGroupId());
    }


    /*
    public List<GroupUser> getAllUserByGroupId(int groupId, int userId) {

        List<Item> groupItems = itemService.getAllItemByGroupId(Long.valueOf(groupId));

        ArrayList<GroupConnectToUser> groupConnectToUsers = groupConnectToUserRepository.findUserByGroupId((long) groupId);

        List<GroupUser> groupUsers = getUserList(groupConnectToUsers,groupId);
        if (groupItems == null){
            return groupUsers;
        }
        for (Item item : groupItems) {
            int price = item.getPrice();
            ArrayList<User>  usersId = itemConnectToUser.findUsersByItemId(item.getId());
            Long paid = item.getPaid();


            int debtPerPerson = price/usersId.size();


            for (int i = 0; i < groupUsers.size(); i++) {
                for (int j = 0; j < usersId.size(); j++) {
                    if (groupUsers.get(i).getId() == usersId.get(j).getId()){
                        if (groupUsers.get(i).getId()!=paid){
                            groupUsers.get(i).setDebt(
                                    groupUsers.get(i).getDebt()+debtPerPerson);
                        }
                        else{
                        }
                    }
                }
            }
        }


        return groupUsers;
    }
 */
    public List<GroupUserDto> getAllUserByGroupId(Long groupId, int userId, String currency) {


        List<Item> itemList = itemRepository.findByGroupId(groupId);

        List<GroupUserDto> groupUserDtoList = userRepository.findAllByGroupId(groupId).stream()
                .map(user -> GroupUserDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .build()).toList();

        for (Item item: itemList) {

            float pricePerPerson = (float)item.getPrice()/ (float) item.getItemConnectToUsers().size();

            //én fizettem és benne vagyok az itemben
            if (item.getPaid() == userId && item.getItemConnectToUsers().stream().anyMatch(itemConnectToUser -> itemConnectToUser.getUser().getId() == userId)){
                for (GroupUserDto groupUser :groupUserDtoList) {
                    if (item.getItemConnectToUsers().stream().anyMatch(itemConnectToUser -> itemConnectToUser.getUser().getId().equals(groupUser.getId()))){
                        if (groupUser.getId() !=userId){
                            float value = groupUser.getOwe();
                            value = value + pricePerPerson;
                            groupUser.setOwe(value);
                        }
                    }
                }
            }

            //nem én fizettem de benne vagyok az itemben
            if (item.getPaid() != userId && item.getItemConnectToUsers().stream().anyMatch(itemConnectToUser -> itemConnectToUser.getUser().getId() == userId)){
                for (GroupUserDto groupUser :groupUserDtoList) {
                    if (item.getItemConnectToUsers().stream().anyMatch(itemConnectToUser -> itemConnectToUser.getUser().getId() == userId)){
                        if (groupUser.getId().longValue() == item.getPaid().longValue()){
                            float value = groupUser.getDebt();
                            value = value + pricePerPerson;
                            groupUser.setDebt(value);
                        }
                    }
                }
            }


        }
        for (GroupUserDto groupUserDto: groupUserDtoList) {

            if (groupUserDto.getOwe() == groupUserDto.getDebt()){
                groupUserDto.setOwe(0);
                groupUserDto.setDebt(0);
            }

            if (groupUserDto.getOwe()<groupUserDto.getDebt()){
                float value = groupUserDto.getDebt()-groupUserDto.getOwe();
                groupUserDto.setDebt(value);
                groupUserDto.setOwe(0);
            }
            if (groupUserDto.getOwe()>groupUserDto.getDebt()){
                float value = groupUserDto.getOwe()-groupUserDto.getDebt();
                groupUserDto.setOwe(value);
                groupUserDto.setDebt(0);
            }
        }


        return groupUserDtoList;

    }

    private List<GroupUser> getUserList(ArrayList<GroupConnectToUser> groupConnectToUsers, int groupId) {
        ArrayList<GroupUser> groupUsers = new ArrayList<>();

        for (GroupConnectToUser users : groupConnectToUsers) {

            Optional<User> user = userRepository.findById(users.getUser().getId());


            if (user.isPresent()) {

                GroupUser groupUser = GroupUser.builder()
                        .id(user.get().getId())
                        .username(user.get().getUsername())
                        .debt(paymentService.userDebt(groupId))
                        .build();

                groupUsers.add(groupUser);
            }
        }

        return groupUsers;
    }
}
