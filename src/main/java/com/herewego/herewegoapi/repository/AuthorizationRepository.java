package com.herewego.herewegoapi.repository;

import com.herewego.herewegoapi.model.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

    @Query("SELECT u.refreshToken FROM Authorization u WHERE u.id=:id")
    String getRefreshTokenById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Authorization u SET u.refreshToken=:token WHERE u.email=:email")
    void updateRefreshToken(@Param("email") String email, @Param("token") String token);

    @Transactional
    @Modifying
    @Query("UPDATE Authorization u SET u.accessToken=:token WHERE u.email=:email")
    void updateAccessToken(@Param("email") String email, @Param("token") String token);

}
