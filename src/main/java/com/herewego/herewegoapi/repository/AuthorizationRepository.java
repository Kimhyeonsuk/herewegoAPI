package com.herewego.herewegoapi.repository;

import com.herewego.herewegoapi.common.AuthProvider;
import com.herewego.herewegoapi.model.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

    @Query("SELECT u.refreshToken FROM Authorization u WHERE u.id=:id")
    String getRefreshTokenById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Authorization u SET u.refreshToken=:token WHERE u.userId=:userId")
    void updateRefreshToken(@Param("userId") String userId, @Param("token") String token);

    @Transactional
    @Modifying
    @Query("UPDATE Authorization u SET u.accessToken=:token WHERE u.userId=:userId")
    void updateAccessToken(@Param("userId") String userId, @Param("token") String token);

    Authorization findByAccessToken(String accessToken);

    Authorization findByUserId(String userId);

    @Transactional
    void deleteByUserId(String userId);


}
