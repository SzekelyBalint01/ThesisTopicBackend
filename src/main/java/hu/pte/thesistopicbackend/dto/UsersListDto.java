package hu.pte.thesistopicbackend.dto;

import hu.pte.thesistopicbackend.model.GroupUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersListDto {

    private List<GroupUser> userList;

    private String message;

}
