package com.example.maxbitdigitalasset.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@Entity
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private String id;

    @Column(name = "book_title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    @ManyToMany
    private Set<MemberEntity> members = new HashSet<>();
}
