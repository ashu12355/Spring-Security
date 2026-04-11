package com.ashu.SpringSecurity.repository;

import com.ashu.SpringSecurity.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByUsername(String username);
}
