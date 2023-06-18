package com.example.bloggingapp.tag.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "tag")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Tag {
    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(length = 20, unique = true, nullable = false)
    private String name;

    public Tag(String name) {
        this.name = name;
    }

}
