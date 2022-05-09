package com.unborn.blogger.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="categories")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name="title", nullable = false, length = 100)
    private String categoryTitle;

    @Column(name="description", nullable = true)
    private String categoryDescription;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<Post> posts = new HashSet<Post>();

}
