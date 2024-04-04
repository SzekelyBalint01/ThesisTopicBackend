package hu.pte.thesistopicbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "itemConnectToGroups")
public class ItemConnectToGroup {

    @EmbeddedId
    ItemConnectToGroupKey id;


    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;


    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id")
    private Group group;
}
