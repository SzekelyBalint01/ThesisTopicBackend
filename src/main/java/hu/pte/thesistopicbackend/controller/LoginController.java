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
import java.util.Optional;

@RestController
public class LoginController {

    private final Login login;

    public LoginController(Login login) {
        this.login = login;
    }


    @PostMapping("/login")
    public ResponseEntity<Optional<User>> userAuthentication(@RequestBody CredentialsDto credentialsDto) throws FileNotFoundException {

        Boolean authenticated = (login.authentication(credentialsDto));


        Optional<User> user = login.getUserByEmail(credentialsDto);

        if (authenticated && user.isPresent()){
            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
