package hu.pte.thesistopicbackend.repository;

import hu.pte.thesistopicbackend.model.GroupConnectToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface GroupConnectToUserRepository extends JpaRepository<GroupConnectToUser, Long> {
    ArrayList<GroupConnectToUser> findUsersByGroupId(Long groupId);

    @Query("select g from GroupConnectToUser g where g.user.id = ?1")
    List<GroupConnectToUser> findByUserId(Long userId);


    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM GroupConnectToUser e WHERE e.group.id = :groupId AND e.user.id = :userId")
    boolean existsByGroupIdAndUserId(@Param("groupId") Long groupId, @Param("userId") Long userId);

}
