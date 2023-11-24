package hu.pte.thesistopicbackend.controller;

import hu.pte.thesistopicbackend.dto.UserDto;
import hu.pte.thesistopicbackend.model.User;
import hu.pte.thesistopicbackend.service.Registration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final Registration registration;
    public RegistrationController(Registration registration) {

        this.registration = registration;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> Registration(@RequestBody UserDto userDto){
        boolean user = registration.createUser(userDto);
        if (user){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("This email already registered",HttpStatus.CONFLICT);
    }
}
