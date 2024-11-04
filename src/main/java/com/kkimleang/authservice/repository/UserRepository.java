package com.kkimleang.authservice.repository;

import com.kkimleang.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByIdIn(List<UUID> ids);

    @Modifying
    @Query("UPDATE User u SET u.profileURL = :profileURL WHERE u.id = :id")
    Integer updateUserProfile(UUID id, String profileURL);

    @Modifying
    @Query("UPDATE User u SET u.isVerified = :isVerified WHERE u.id = :id")
    Integer updateVerification(UUID id, Boolean isVerified);
}
