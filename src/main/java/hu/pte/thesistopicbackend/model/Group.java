package hu.pte.thesistopicbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "groups")
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;


    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<ItemConnectToGroup> itemConnectToGroup;


    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<GroupConnectToUser> groupConnectToUsers;


}
