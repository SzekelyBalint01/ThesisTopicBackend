package hu.pte.thesistopicbackend.repository;

import hu.pte.thesistopicbackend.model.ItemConnectToUser;
import hu.pte.thesistopicbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ItemConnectToUserRepository extends JpaRepository<ItemConnectToUser, Long> {

    ArrayList<ItemConnectToUser> findItemByUserId(Long UserId);


    @Query("SELECT u FROM User u JOIN ItemConnectToUser icu ON u.id = icu.userId WHERE icu.itemId = :itemId")
    ArrayList<User> findUsersByItemId(@Param("itemId") Long itemId);
}
