package hu.pte.thesistopicbackend.dto;

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

    private List<ItemDto> items;

    private List<UserDto> users;
}
