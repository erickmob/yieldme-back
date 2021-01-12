package com.erickmob.yieldme.repository;

import javax.transaction.Transactional;

import com.erickmob.yieldme.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

}