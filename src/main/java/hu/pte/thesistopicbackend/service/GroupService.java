package hu.pte.thesistopicbackend.service;

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

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupConnectToUserRepository groupConnectToUserRepository;
    private final ItemConnectToUserRepository itemConnectToUser;

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
        this.itemConnectToUser = itemConnectToUser;
        this.itemConnectToGroupRepository = itemConnectToGroupRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemService = itemService;
        this.paymentService = paymentService;
    }

    public GroupEssential createGroup(NewGroupDto newGroupDto, Long userId) {
        GroupEssential group = GroupEssential.builder()
                .groupName(newGroupDto.getGroupName())
                .build();

        groupRepository.save(group);

        GroupConnectToUser newConnection = GroupConnectToUser.builder()
                .groupId(group.getId())
                .userId(userId)
                .build();

        groupConnectToUserRepository.save(newConnection);

        return group;
    }

    public Group getGroupById(Long groupId) throws FileNotFoundException {


        GroupEssential groupEssential = groupRepository.findById(groupId).orElseThrow(()-> new EntityNotFoundException());
        ArrayList<ItemConnectToGroup> itemList = itemConnectToGroupRepository.findItemByGroupId(groupId);
        ArrayList<GroupConnectToUser> userList = groupConnectToUserRepository.findUserByGroupId(groupId);

        ArrayList<Item> items = new ArrayList<>();
        for (ItemConnectToGroup itemConnectToGroup: itemList) {
            items.add(itemRepository.findById(itemConnectToGroup.getItemId()).orElseThrow(()-> new FileNotFoundException()));
        }

        ArrayList<String []> users = new ArrayList<>();
        for (GroupConnectToUser groupConnectToUser: userList) {
            String[] user = {
                    String.valueOf(userRepository.findById(groupConnectToUser.getUserId()).orElseThrow(()-> new FileNotFoundException()).getId()),
                    userRepository.findById(groupConnectToUser.getUserId()).orElseThrow(()-> new FileNotFoundException()).getUsername()
            };
            users.add(user);
        }


        Group group = Group.builder()
                .id(groupEssential.getId())
                .groupName(groupEssential.getGroupName())
                .Items(items)
                .Users(users)
                .build();

        return group;
    }

    public List<GroupEssential> getAllGroupByUserId(Long userId){
        List<GroupConnectToUser> groups = groupConnectToUserRepository.findByUserId(userId) ;
        List<GroupEssential> groupEssentials = new ArrayList<>();

        for (GroupConnectToUser groupConnectToUser: groups) {
            groupEssentials.add(groupRepository.findById(groupConnectToUser.getGroupId()).orElseThrow(()-> new EntityNotFoundException()));
        }


        return groupEssentials;
    }


    public String inviteUser(InviteUserDto inviteUserDto) {

        Optional<User> user = userRepository.findByEmail(inviteUserDto.getEmail());

        boolean exits = groupConnectToUserRepository.existsByGroupIdAndUserId((long) inviteUserDto.getGroupId(), user.get().getId());

        if (user.isPresent() && exits == false) {

            GroupConnectToUser groupConnectToUser = GroupConnectToUser.builder()
                    .userId(user.get().getId())
                    .groupId((long) inviteUserDto.getGroupId())
                    .build();
            groupConnectToUserRepository.save(groupConnectToUser);
            return "Invite successful";
        }
        else{
            return "User does not exists or already exists";
        }
    }

    public List<GroupUser> getAllUserByGroupId(InviteUserDto inviteUserDto) {

        ArrayList<GroupConnectToUser> groupConnectToUsers = groupConnectToUserRepository.findUserByGroupId((long) inviteUserDto.getGroupId());

        return getUserList(groupConnectToUsers, inviteUserDto.getGroupId());
    }

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
                        groupUsers.get(i).setDebt(
                                groupUsers.get(i).getDebt()+debtPerPerson
                        );
                    }
                }
            }
        }


        return groupUsers;
    }

    private List<GroupUser> getUserList(ArrayList<GroupConnectToUser> groupConnectToUsers, int groupId) {
        ArrayList<GroupUser> groupUsers = new ArrayList<>();

        for ( GroupConnectToUser users: groupConnectToUsers) {

            Optional<User> user = userRepository.findById(users.getUserId());



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
