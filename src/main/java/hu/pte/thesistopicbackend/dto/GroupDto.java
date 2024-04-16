package hu.pte.thesistopicbackend.dto;

import hu.pte.thesistopicbackend.model.GroupConnectToUser;
import hu.pte.thesistopicbackend.model.Item;

import hu.pte.thesistopicbackend.model.ItemConnectToGroup;
import hu.pte.thesistopicbackend.model.User;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDto {

    private Long id;

    private String groupName;

    private List<ItemConnectToGroup> itemConnectToGroup;

    private List<GroupConnectToUser> groupConnectToUsers;
}
