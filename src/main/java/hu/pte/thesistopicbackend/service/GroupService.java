package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.dto.NewGroupDto;
import hu.pte.thesistopicbackend.model.*;
import hu.pte.thesistopicbackend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupConnectToUserRepository groupConnectToUser;
    private final ItemConnectToUserRepository itemConnectToUser;


    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository,
                        GroupConnectToUserRepository groupConnectToUser,
                        ItemConnectToUserRepository itemConnectToUser, ItemRepository itemRepository,
                        UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.groupConnectToUser = groupConnectToUser;
        this.itemConnectToUser = itemConnectToUser;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public GroupEssential createGroup(NewGroupDto newGroupDto) {
        GroupEssential group = GroupEssential.builder()
                .groupName(newGroupDto.getGroupName())
                .build();

        groupRepository.save(group);


        return group;
    }

    public Group getGroupById(Long groupId) throws FileNotFoundException {


        GroupEssential groupEssential = groupRepository.findById(groupId).orElseThrow(()-> new EntityNotFoundException());
        ArrayList<ItemConnectToUser> itemList = itemConnectToUser.findItemByGroupId(groupId);
        ArrayList<GroupConnectToUser> userList = groupConnectToUser.findUserByGroupId(groupId);

        ArrayList<Item> items = new ArrayList<>();
        for (ItemConnectToUser itemConnectToGroup: itemList) {
            items.add(itemRepository.findById(itemConnectToGroup.getItem()).orElseThrow(()-> new FileNotFoundException()));
        }

        ArrayList<String> users = new ArrayList<>();
        for (GroupConnectToUser groupConnectToUser: userList) {
            users.add(userRepository.findById(groupConnectToUser.getUser()).orElseThrow(()-> new FileNotFoundException()).getUsername());
        }


        Group group = Group.builder()
                .id(groupEssential.getId())
                .groupName(groupEssential.getGroupName())
                .Items(items)
                .Users(users)
                .build();

        return null;
    }
}
