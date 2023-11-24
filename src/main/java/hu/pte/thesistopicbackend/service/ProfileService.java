package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.dto.ProfileDto;
import hu.pte.thesistopicbackend.model.User;
import hu.pte.thesistopicbackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProfileService {
    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserInfo(int userId) {
        User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new EntityNotFoundException());

        User userInfo = User.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

        return userInfo;
    }


    @Transactional
    public User saveUser(ProfileDto profileDto) {

        Optional<User> user = userRepository.findByEmail(profileDto.getEmail());

        if (user.isEmpty()|| user.get().getId()==profileDto.getUserId()){
            User userNew = userRepository.findById(Long.valueOf(profileDto.getUserId())).orElseThrow(() -> new EntityNotFoundException());

            userNew.setUsername(profileDto.getUsername());
            userNew.setEmail(profileDto.getEmail());

            userRepository.save(userNew);
            return userNew;
        }
        return null;
    }
}
