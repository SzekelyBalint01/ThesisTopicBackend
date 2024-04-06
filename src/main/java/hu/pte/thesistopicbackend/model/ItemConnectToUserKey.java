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
public class ItemConnectToUserKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "item_id")
    private Long itemId;

}
