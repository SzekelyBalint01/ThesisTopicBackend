package hu.pte.thesistopicbackend.dto;

import hu.pte.thesistopicbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewItemDto {

    private int groupId;
    private String name;
    private int price;
    private String currency;
    private String mapUrl;
    private String description;
    private Long paidBy;
    private List<Integer> users;
}
