package hu.pte.thesistopicbackend.model;

import lombok.*;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {

    private Long id;
    private String groupName;
    private ArrayList<Item> Items;

    private ArrayList<String[]> Users;


}
