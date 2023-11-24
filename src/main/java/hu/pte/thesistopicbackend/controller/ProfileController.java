package hu.pte.thesistopicbackend.controller;

import hu.pte.thesistopicbackend.dto.ProfileDto;
import hu.pte.thesistopicbackend.model.User;
import hu.pte.thesistopicbackend.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {


    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/userInfo")
    public ResponseEntity<User> getUserData (@RequestParam int userId){

        User user = profileService.getUserInfo(userId);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/saveProfile")
    public ResponseEntity<User> saveUser(@RequestBody ProfileDto profileDto){

        User user = profileService.saveUser(profileDto);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
