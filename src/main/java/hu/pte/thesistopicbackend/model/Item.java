package hu.pte.thesistopicbackend.model;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "items")

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private String currency;
    private String mapUrl;
    private String description;
    private Long paid;


    @OneToMany(mappedBy = "item")
    private List<ItemConnectToUser> itemConnectToUsers;


    @OneToMany(mappedBy = "item")
    private List<ItemConnectToGroup> itemConnectToGroups;
    // getters és setters
}
