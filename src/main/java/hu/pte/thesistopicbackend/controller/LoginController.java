package hu.pte.thesistopicbackend.controller;

import hu.pte.thesistopicbackend.dto.CredentialsDto;
import hu.pte.thesistopicbackend.model.User;
import hu.pte.thesistopicbackend.service.Login;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
public class LoginController {

    private final Login login;

    public LoginController(Login login) {
        this.login = login;
    }

    @PostMapping("/login")
    public ResponseEntity<User> userAuthentication(@RequestBody CredentialsDto credentialsDto) throws FileNotFoundException {

        User user = login.userAuth(credentialsDto);

        if (user.equals(null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
}
