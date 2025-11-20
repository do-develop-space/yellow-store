package com.example.demo.member.infrastructure;

import com.example.demo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, UUID> {

}
