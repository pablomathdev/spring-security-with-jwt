package com.github.pablomathdev.login.Domain.Entities;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tb_password_reset_token")
public class PasswordResetToken {
 
   
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String token;
 
   
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
 
    private Date expireDate = generateExpDate();

   @Transient
    private Date generateExpDate() {
		 Instant exp = Instant.now().plus(15, ChronoUnit.MINUTES);
	        return Date.from(exp);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public Date getExpiryDate() {
		return expireDate;
	}

	
}