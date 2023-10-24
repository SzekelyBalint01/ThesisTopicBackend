package hu.pte.thesistopicbackend.repository;

import hu.pte.thesistopicbackend.model.Item;
import hu.pte.thesistopicbackend.model.ItemConnectToUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ItemConnectToUserRepository extends JpaRepository<ItemConnectToUser, Long> {

    ArrayList<ItemConnectToUser> findItemByGroupId(Long groupId);
}
