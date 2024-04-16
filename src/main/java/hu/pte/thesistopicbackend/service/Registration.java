package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.dto.UserDto;
import hu.pte.thesistopicbackend.repository.UserRepository;
import hu.pte.thesistopicbackend.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class Registration {
    private final PasswordEncoderImpl passwordEncoderImpl;
    private final UserRepository userRepository;

    public Registration(PasswordEncoderImpl passwordEncoderImpl, UserRepository userRepository) {
        this.passwordEncoderImpl = passwordEncoderImpl;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean createUser(UserDto userDto) {

        Optional<User> user = userRepository.findByEmail(userDto.getEmail());

        if (user.isEmpty()){
            Map<String, byte[]> hashedPasswordAndSalt = passwordEncoderImpl.hashPassword(userDto.getPassword());

            User newUser =
                    User.builder()
                            .email(userDto.getEmail())
                            .passwordHash(hashedPasswordAndSalt.get("hash"))
                            .passwordSalt(hashedPasswordAndSalt.get("salt"))
                            .username(userDto.getUsername())
                            .build();

            userRepository.save(newUser);

            return  true;
        }
        return false;
    }
}
