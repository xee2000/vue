package com.ljh.back.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "members")
@Getter
@Setter
@Entity
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false,unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false,unique = true)
    private String memId;
}
