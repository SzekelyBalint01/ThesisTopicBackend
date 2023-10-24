package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.dto.UserDto;
import hu.pte.thesistopicbackend.repository.UserRepository;
import hu.pte.thesistopicbackend.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Registration {


    private final UserRepository userRepository;

    public Registration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addNewUser(UserDto userDto) {

       Optional<User> user = userRepository.findByEmail(userDto.getEmail());

       if (user.isEmpty()){
           User newUser = User.builder()
                   .username(userDto.getUsername())
                   .email(userDto.getEmail())
                   .password(userDto.getPassword()).build();

           userRepository.save(newUser);
           return true;
       }
       return false;
    }
}
