package hu.pte.thesistopicbackend.repository;

import hu.pte.thesistopicbackend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i JOIN ItemConnectToGroup icg ON i.id = icg.item.id WHERE icg.group.id =:groupId" )
    List<Item> findByGroupId(@Param("groupId") Long groupId);
}
