package hu.pte.thesistopicbackend.model;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private byte[] passwordSalt;
    private byte[] passwordHash;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<ItemConnectToUser> itemConnectToUsers;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<GroupConnectToUser> groupConnectToUsers;

}
