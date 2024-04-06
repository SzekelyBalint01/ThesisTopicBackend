package hu.pte.thesistopicbackend.dto;

import hu.pte.thesistopicbackend.model.Item;

import hu.pte.thesistopicbackend.model.User;
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

    private List<String> users;

    private List<Item> items;
}
