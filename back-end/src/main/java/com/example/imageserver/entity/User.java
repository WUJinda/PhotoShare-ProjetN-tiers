package com.example.imageserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "user")
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", length = 65)
    private String username;

    @Column(name = "password", length = 65)
    private String password;


    @OneToMany(targetEntity = Image.class,cascade={CascadeType.ALL})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Image> images;

}