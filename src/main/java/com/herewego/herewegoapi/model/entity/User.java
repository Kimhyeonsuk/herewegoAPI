package com.herewego.herewegoapi.model.entity;

import com.herewego.herewegoapi.common.AuthProvider;
import com.herewego.herewegoapi.common.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "League")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String name;

    private String description;

    private String img;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String githubUrl;
    private String blogUrl;

    private String refreshToken;
}
