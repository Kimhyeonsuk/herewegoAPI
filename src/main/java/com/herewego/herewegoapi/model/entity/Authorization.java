package com.herewego.herewegoapi.model.entity;

import com.herewego.herewegoapi.common.AuthProvider;
import com.herewego.herewegoapi.common.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Authorization")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authorization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @org.hibernate.annotations.Generated(GenerationTime.INSERT) @Column(name = "created_date")
    LocalDateTime createdDate;

    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS) @Column(name = "updated_date")
    LocalDateTime updatedDate;
}
