package com.herewego.herewegoapi.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserVO {
    String email;

    String accessToken;

    TeamInfoVO homeTeam;

    List<FavoriteTeamVO> favorites;

    List<Integer> gameUnit;

    @Override
    public String toString() {
        return "{" +
                "email='" + email + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", homeTeam=" + homeTeam +
                ", favorites=" + favorites +
                ", gameUnit=" + gameUnit +
                '}';
    }
}
