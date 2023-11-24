package hu.pte.thesistopicbackend.controller;

import hu.pte.thesistopicbackend.dto.InviteUserDto;
import hu.pte.thesistopicbackend.dto.NewGroupDto;
import hu.pte.thesistopicbackend.dto.UsersListDto;
import hu.pte.thesistopicbackend.model.Group;
import hu.pte.thesistopicbackend.model.GroupEssential;
import hu.pte.thesistopicbackend.model.GroupUser;
import hu.pte.thesistopicbackend.repository.UserRepository;
import hu.pte.thesistopicbackend.service.GroupService;
import hu.pte.thesistopicbackend.service.ItemService;
import hu.pte.thesistopicbackend.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class GroupController {


    private final GroupService groupService;
    private final UserRepository userRepository;


    public GroupController(GroupService groupService, ItemService itemService,
                           UserRepository userRepository, PaymentService paymentService) {
        this.groupService = groupService;
        this.userRepository = userRepository;
    }

    @PostMapping("/createGroup")
    public ResponseEntity<GroupEssential> newGroup(@RequestBody NewGroupDto newGroupDto, @RequestParam int userId ){
        GroupEssential group = groupService.createGroup(newGroupDto, (long) userId);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }



    @GetMapping("groupData")
    public ResponseEntity<Group> getGroupDataById(@RequestParam int groupId) throws FileNotFoundException {

        Group group = groupService.getGroupById((long) groupId);

        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @GetMapping("/allGroup")
    public  ResponseEntity<List<GroupEssential>> getAllGroupByUserId(@RequestParam("userId") int userId){

        List<GroupEssential> groups = groupService.getAllGroupByUserId((long) userId);

        return new ResponseEntity<>(groups, HttpStatus.OK);
    }


    @PostMapping("/addUserToGroup")
    public ResponseEntity<UsersListDto> addNewUserToGroup(@RequestBody InviteUserDto inviteUserDto){

        String message = groupService.inviteUser(inviteUserDto);

        List<GroupUser> users = groupService.getAllUserByGroupId(inviteUserDto);

        UsersListDto usersListDto = new UsersListDto(users, message);

        return new ResponseEntity<>(usersListDto,HttpStatus.OK);
    }


    @GetMapping("/getAllGroupUser")
    public ResponseEntity<List<GroupUser>> getAllGroupUser(@RequestParam("groupId") int groupId){

        List<GroupUser> usersList = groupService.getAllUserByGroupId(groupId);

        return new ResponseEntity<>(usersList,HttpStatus.OK);
    }

}
