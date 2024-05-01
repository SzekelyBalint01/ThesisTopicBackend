package hu.pte.thesistopicbackend.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupUserDto {

    private Long id;

    private String username;

    //ő tartozok nekem
    private float owe;

    // én tartozok neki
    private float debt;

}
