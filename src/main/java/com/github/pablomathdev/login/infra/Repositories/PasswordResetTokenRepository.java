package com.github.pablomathdev.login.infra.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.pablomathdev.login.Domain.Entities.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

}