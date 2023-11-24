package hu.pte.thesistopicbackend.model;

import jakarta.persistence.*;
@Entity
public class Due {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long oweuser;
    private int price;
    private String currency;

    // getters és setters
}
