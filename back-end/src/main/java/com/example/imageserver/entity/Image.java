package com.example.imageserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "image")
@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 65)
    private String name;

    @Column(name = "file")
    private String file;

    @Column(name = "user_id")
    private Integer userId;


    @OneToMany(targetEntity = Comment.class,cascade={CascadeType.ALL})//体现依赖的主表
    @JoinColumn(name = "image_id",referencedColumnName = "id")
    private List<Comment> comments;


}