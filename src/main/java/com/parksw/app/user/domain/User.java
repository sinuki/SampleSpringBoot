package com.parksw.app.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @Column(length = 20, unique = true, nullable = false)
    private String username;

    @Column(length = 255, nullable = false)
    private String credential;

    @Column(length = 20, unique = true, nullable = false)
    private String nickname;

    private Integer loginFailedCount = 0;

    private LocalDateTime lastLoginAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void loginFailed() {
        this.loginFailedCount += 1;
    }

    public void loginSuccess() {
        this.lastLoginAt = LocalDateTime.now();
        this.loginFailedCount = 0;
    }
}
