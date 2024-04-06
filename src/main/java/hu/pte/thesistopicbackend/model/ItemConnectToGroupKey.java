package hu.pte.thesistopicbackend.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemConnectToGroupKey implements Serializable {

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "item_id")
    private Long itemId;

}

