package com.ljh.back.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "sales")
@Getter
@Setter
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int adminId;

    @Column
    private int price;

    @Column(length = 50, nullable = false)
    private String name;

    @Column
    private int itemId;

    @Column
    private String orderName;

}
