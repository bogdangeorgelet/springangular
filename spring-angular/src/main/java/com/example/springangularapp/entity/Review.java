package com.example.springangularapp.entity;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "text")
    private String text;

    @Column(name = "value")
    private double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_company")
    private Company company;
}
