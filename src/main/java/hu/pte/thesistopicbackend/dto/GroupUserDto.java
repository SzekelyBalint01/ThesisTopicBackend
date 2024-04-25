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
    //ha minus akkor ő tartozik, ha plussz akkor neki tartoznak.
    private double debt;

}
