package hu.pte.thesistopicbackend.repository;

import hu.pte.thesistopicbackend.model.ItemConnectToGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ItemConnectToGroupRepository extends JpaRepository<ItemConnectToGroup, Long> {
    ArrayList<ItemConnectToGroup> findItemByGroupId(Long groupId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ItemConnectToGroup ic WHERE ic.item.id = :itemId")
    void deleteByItemId(@Param("itemId") long itemId);
}