package com.parksw.app.user.repository;

import com.parksw.app.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
