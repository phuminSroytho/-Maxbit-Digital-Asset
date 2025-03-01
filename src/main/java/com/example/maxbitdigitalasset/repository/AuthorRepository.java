package com.example.maxbitdigitalasset.repository;

import com.example.maxbitdigitalasset.model.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, String> {
}
