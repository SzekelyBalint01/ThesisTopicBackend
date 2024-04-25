package hu.pte.thesistopicbackend.dto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    private Long id;
    private String name;
    private int price;
    private String currency;
    private String mapUrl;
    private String description;
    private Long paid;
    private List<UserDto> users;
}
