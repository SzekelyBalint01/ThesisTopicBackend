package hu.pte.thesistopicbackend.controller;

import hu.pte.thesistopicbackend.dto.NewGroupDto;
import hu.pte.thesistopicbackend.model.Group;
import hu.pte.thesistopicbackend.model.GroupEssential;
import hu.pte.thesistopicbackend.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileNotFoundException;

@RestController
public class GroupController {


    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/crateGroup")
    public ResponseEntity<GroupEssential> newGroup(@RequestBody NewGroupDto newGroupDto){

       GroupEssential group = groupService.createGroup(newGroupDto);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }
    @GetMapping("groupData/{groupId}")
    public ResponseEntity<Group> getGroupDataById(@PathVariable Long groupId) throws FileNotFoundException {

        Group group = groupService.getGroupById(groupId);

        return new ResponseEntity<>(group, HttpStatus.OK);
    }
}
