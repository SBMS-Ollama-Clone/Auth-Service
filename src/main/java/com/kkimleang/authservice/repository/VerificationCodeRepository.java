package com.kkimleang.authservice.repository;

import com.kkimleang.authservice.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {
    Optional<VerificationCode> findByCode(String code);
}
