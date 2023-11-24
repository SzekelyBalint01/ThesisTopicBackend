package hu.pte.thesistopicbackend.repository;

import hu.pte.thesistopicbackend.model.ItemConnectToGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ItemConnectToGroupRepository extends JpaRepository<ItemConnectToGroup, Long> {
    ArrayList<ItemConnectToGroup> findItemByGroupId(Long groupId);
}