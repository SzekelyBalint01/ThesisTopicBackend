package hu.pte.thesistopicbackend.dto;

import hu.pte.thesistopicbackend.model.GroupConnectToUser;
import hu.pte.thesistopicbackend.model.ItemConnectToUser;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private long id;
    private String username;
    private String email;
    private String password;
    List<ItemDto> items;
    List<GroupDto> groups;
}
