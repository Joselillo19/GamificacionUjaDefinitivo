package com.mycompany.gamificacionuja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.gamificacionuja.model.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
}
