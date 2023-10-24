package hu.pte.thesistopicbackend.repository;

import hu.pte.thesistopicbackend.model.GroupEssential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEssential, Long> {
}
