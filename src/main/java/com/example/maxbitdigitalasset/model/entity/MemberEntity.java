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
@Table(name = "member_entity")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int id;

    @Column(name = "member_name")
    private String name;
    @Column(name = "member_email")
    private String email;

    @ManyToMany
    @JoinTable(
            name = "member_book",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns =  @JoinColumn(name = "book_id")
    )
    private Set<BookEntity> borrowedBooks = new HashSet<>();
}
