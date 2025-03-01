package com.example.maxbitdigitalasset.repository;

import com.example.maxbitdigitalasset.model.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,String> {
}
