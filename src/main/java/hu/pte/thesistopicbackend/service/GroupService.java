package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.dto.GroupDto;
import hu.pte.thesistopicbackend.dto.InviteUserDto;
import hu.pte.thesistopicbackend.dto.NewGroupDto;
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

        GroupConnectToUserKey groupConnectToUserKey = new GroupConnectToUserKey(group.getId(),userId);

        GroupConnectToUser newConnection = GroupConnectToUser.builder()
                .id(groupConnectToUserKey)
                .user(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new))
                .group(group)
                .build();

        groupConnectToUserRepository.save(newConnection);

        GroupDto groupDto = GroupDto.builder()
                .id(group.getId())
                .groupName(group.getGroupName())
                .build();

        return groupDto;
    }

    public GroupDto getGroupById(Long groupId){


        Group group = groupRepository.findById(groupId).orElseThrow(()-> new EntityNotFoundException());

        GroupDto groupDto = GroupDto.builder()
                .id(group.getId())
                .groupName(group.getGroupName())
                .itemConnectToGroup(group.getItemConnectToGroup())
                .groupConnectToUsers(group.getGroupConnectToUsers())
                .build();


        return groupDto;
    }

    public List<GroupDto> getAllGroupByUserId(Long userId){
        List<GroupConnectToUser> groups = groupConnectToUserRepository.findByUserId(userId) ;
        List<GroupDto> groupEssentials = new ArrayList<>();

        for (GroupConnectToUser groupConnectToUser: groups) {
            Group group = groupRepository.findById(groupConnectToUser.getGroup().getId()).orElseThrow(()-> new EntityNotFoundException());

            GroupDto groupDto = GroupDto.builder()
                    .id(group.getId())
                    .groupName(group.getGroupName())
                    .groupConnectToUsers(group.getGroupConnectToUsers())
                    .itemConnectToGroup(group.getItemConnectToGroup())
                    .build();

            groupEssentials.add(groupDto);
        }


        return groupEssentials;
    }


    public String inviteUser(InviteUserDto inviteUserDto) {

        Optional<User> user = userRepository.findByEmail(inviteUserDto.getEmail());

        boolean exits = groupConnectToUserRepository.existsByGroupIdAndUserId((long) inviteUserDto.getGroupId(), user.get().getId());

        if (user.isPresent() && exits == false) {

            GroupConnectToUserKey groupConnectToUserKey = new GroupConnectToUserKey((long) inviteUserDto.getGroupId(),user.get().getId());
            GroupConnectToUser groupConnectToUser = GroupConnectToUser.builder()
                    .id(groupConnectToUserKey)
                    .user(user.get())
                    .group(groupRepository.findById((long) inviteUserDto.getGroupId()).orElseThrow(EntityNotFoundException::new))
                    .build();
            groupConnectToUserRepository.save(groupConnectToUser);
            return "Invite successful";
        }
        else{
            return "User does not exists or already exists";
        }
    }

    public List<GroupUser>  getAllUserByGroupId(InviteUserDto inviteUserDto) {

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
    public  List<GroupUser> getAllUserByGroupId(Long groupId, int userId){
/*
        List<Item> items = itemService.getAllItemByGroupId(groupId);

        ArrayList<GroupConnectToUser> groupUsers = groupConnectToUserRepository.findUsersByGroupId(groupId);

        groupUsers.stream().map(groupConnectToUser -> {
                 items.stream().filter(
                    (item)->
                        !Objects.equals(item.getPaid(), groupConnectToUser.getUser().getId())
            ).map(item->{
                int debt = item.getPrice() / item.getItemConnectToUsers().size();
                return debt;
            })
        });
        */
        return null;

    }

    private List<GroupUser> getUserList(ArrayList<GroupConnectToUser> groupConnectToUsers, int groupId) {
        ArrayList<GroupUser> groupUsers = new ArrayList<>();

        for ( GroupConnectToUser users: groupConnectToUsers) {

            Optional<User> user = userRepository.findById(users.getUser().getId());



            if (user.isPresent()){

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
