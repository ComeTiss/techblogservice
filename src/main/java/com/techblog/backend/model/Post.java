package com.techblog.backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    public Post() {
    }

    public Post(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
