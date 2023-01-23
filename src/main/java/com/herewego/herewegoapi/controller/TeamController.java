package com.herewego.herewegoapi.controller;


import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.model.request.UpdateFavoriteTeamVO;
import com.herewego.herewegoapi.response.ApiResponse;
import com.herewego.herewegoapi.service.TeamService;
import com.herewego.herewegoapi.vo.RegisterUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class TeamController {
    @Autowired
    TeamService teamService;

    @GetMapping(value = "/teams")
    public Object getTeamInformation(
            @RequestHeader(value = "UserId") String userId,
            @RequestHeader(value = "Authorization") String accountToken,
            @PathVariable("teamId") String teamName,
            @PathVariable("leagueId") String leagueName)  {

        return ApiResponse.ok();
    }

    @PutMapping(value = "teams/favorites")
    public Object updateFavoriteTeam(
            @RequestHeader(value = "Authorization") String accountToken,
            @RequestHeader(value = "UserId") String userId,
            @RequestParam("teamId") Integer teamId) throws ForwardException {

        teamService.updateFavoriteTeam(userId, teamId);
        return ApiResponse.ok();

    }
}
