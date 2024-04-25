package hu.pte.thesistopicbackend.dto;

import hu.pte.thesistopicbackend.model.ItemConnectToGroup;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemConnectToGroupDTO {

    private Long itemId;
    private Long groupId;


    // Konstruktor a másik osztály alapján
    public ItemConnectToGroupDTO(ItemConnectToGroup itemConnectToGroup) {
        this.itemId = itemConnectToGroup.getItem().getId();
        this.groupId = itemConnectToGroup.getGroup().getId();
    }


}
