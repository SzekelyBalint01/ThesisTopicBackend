package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.dto.CredentialsDto;
import hu.pte.thesistopicbackend.dto.UserDto;
import hu.pte.thesistopicbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Optional;

@Service
public class Login {

    private final UserRepository userRepository;

    private final PasswordEncoderImpl passwordEncoderImpl;

    public Login(UserRepository userRepository, PasswordEncoderImpl passwordEncoderImpl) {
        this.userRepository = userRepository;
        this.passwordEncoderImpl = passwordEncoderImpl;
    }

    public boolean authentication(CredentialsDto credentialsDto) throws FileNotFoundException {

        boolean authenticated = passwordEncoderImpl.auth(credentialsDto.getPassword(), credentialsDto.getEmail());

        return authenticated;
    }


    public Optional<UserDto> getUserByEmail(CredentialsDto credentialsDto){

        Optional<UserDto> user = userRepository.findByEmail(credentialsDto.getEmail()).stream().map(userdto -> {
            return UserDto.builder()
                    .id(userdto.getId())
                    .username(userdto.getUsername())
                    .build();
        }).findFirst();

        return user;
    }
}
