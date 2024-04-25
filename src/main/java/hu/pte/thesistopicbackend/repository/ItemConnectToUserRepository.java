package hu.pte.thesistopicbackend.repository;

import hu.pte.thesistopicbackend.model.ItemConnectToUser;
import hu.pte.thesistopicbackend.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ItemConnectToUserRepository extends JpaRepository<ItemConnectToUser, Long> {

    ArrayList<ItemConnectToUser> findItemByUserId(Long UserId);


    @Query("SELECT u FROM User u JOIN ItemConnectToUser icu ON u.id = icu.user.id WHERE icu.item.id = :itemId")
    ArrayList<User> findUsersByItemId(@Param("itemId") Long itemId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ItemConnectToUser ic WHERE ic.item.id = :itemId")
    void deleteByItemId(@Param("itemId") long itemId);
}
