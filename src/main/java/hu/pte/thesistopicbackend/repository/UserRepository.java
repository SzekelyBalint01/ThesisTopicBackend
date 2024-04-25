package hu.pte.thesistopicbackend.repository;

import hu.pte.thesistopicbackend.dto.GroupUserDto;
import hu.pte.thesistopicbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String registrationEmail);

    @Query("SELECT u FROM User u JOIN GroupConnectToUser gcu ON u.id = gcu.user.id WHERE gcu.group.id =:groupId" )
    List<User> findAllByGroupId(@Param("groupId") Long groupId);
}
