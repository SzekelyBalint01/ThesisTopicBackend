package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.dto.CredentialsDto;
import hu.pte.thesistopicbackend.model.User;
import hu.pte.thesistopicbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class Login {

    private final UserRepository userRepository;

    public Login(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userAuth(CredentialsDto credentialsDto) throws FileNotFoundException {

       User user = userRepository.findByEmail(credentialsDto.getEmail()).orElseThrow(()-> new FileNotFoundException());

        if (user.getEmail().equals(credentialsDto.getEmail()) && user.getPassword().equals(credentialsDto.getPassword())){

            return user;
        }

        return null;
    }
}
