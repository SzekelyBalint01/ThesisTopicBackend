package hu.pte.thesistopicbackend.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupUser {
    private String username;

    private Long id;

    private int debt;
}
