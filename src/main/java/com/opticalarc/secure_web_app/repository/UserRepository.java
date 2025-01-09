package com.opticalarc.secure_web_app.repository;

import com.opticalarc.secure_web_app.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

//    Optional<User> findByEmailToken(String token);

    Optional<User> findByEmail(String email);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = ?1 WHERE u.email = ?2")
    void updatePassword(String password, String email);

}
