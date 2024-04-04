package hu.pte.thesistopicbackend.model;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "itemConnectToUser")
public
class ItemConnectToUser {

    @EmbeddedId
    ItemConnectToUserKey id;


    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    private Item item;

    // getters és setters
}
