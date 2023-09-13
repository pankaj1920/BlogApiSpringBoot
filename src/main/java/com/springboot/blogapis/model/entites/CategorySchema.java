package com.springboot.blogapis.model.entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class CategorySchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name = "category_title",length = 100,nullable = false)
    private String categoryTitle;
    private String categoryDescription;

    //FetchType.Lazy mean hum parent ko nikal re h child ko nhi
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<PostSchema> post = new ArrayList<>();
}
