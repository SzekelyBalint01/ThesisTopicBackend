package hu.pte.thesistopicbackend.repository;

import hu.pte.thesistopicbackend.model.GroupConnectToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface GroupConnectToUserRepository extends JpaRepository<GroupConnectToUser, Long> {
    ArrayList<GroupConnectToUser> findUserByGroupId(Long groupId);
}
