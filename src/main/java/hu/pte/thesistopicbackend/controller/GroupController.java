package hu.pte.thesistopicbackend.controller;

import hu.pte.thesistopicbackend.dto.GroupDto;
import hu.pte.thesistopicbackend.dto.InviteUserDto;
import hu.pte.thesistopicbackend.dto.NewGroupDto;
import hu.pte.thesistopicbackend.dto.UsersListDto;
import hu.pte.thesistopicbackend.model.Group;
import hu.pte.thesistopicbackend.model.GroupUser;
import hu.pte.thesistopicbackend.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class GroupController {


    private final GroupService groupService;


    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/createGroup")
    public ResponseEntity<GroupDto> newGroup(@RequestBody NewGroupDto newGroupDto, @RequestParam int userId ){
        GroupDto groupDto = groupService.createGroup(newGroupDto, (long) userId);
        return new ResponseEntity<>(groupDto, HttpStatus.CREATED);
    }



    @GetMapping("groupData")
    public ResponseEntity<GroupDto> getGroupDataById(@RequestParam int groupId)  {

        GroupDto groupDto = groupService.getGroupById((long) groupId);

        return new ResponseEntity<>(groupDto, HttpStatus.OK);
    }

    @GetMapping("/allGroup")
    public ResponseEntity<List<GroupDto>> getAllGroupByUserId(@RequestParam("userId") int userId){

        List<GroupDto> groups = groupService.getAllGroupByUserId((long) userId);

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
    public ResponseEntity<List<GroupUser>> getAllGroupUser(@RequestParam("groupId") int groupId, int userId){

        List<GroupUser> usersList = groupService.getAllUserByGroupId((long)groupId, userId);

        return new ResponseEntity<>(usersList,HttpStatus.OK);
    }

}
