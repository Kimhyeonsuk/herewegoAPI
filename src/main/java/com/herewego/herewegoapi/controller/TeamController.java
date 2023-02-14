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

    @PutMapping(value = "/teams/favorites")
    public Object updateFavoriteTeam (
            @RequestHeader(value = "Authorization") String accountToken,
            @RequestHeader(value = "UserId") String userId,
            @RequestParam("teamId") Integer teamId) throws ForwardException {

        teamService.updateFavoriteTeam(userId, teamId);
        return ApiResponse.ok();
    }

    @GetMapping(value = "/teams")
    public Object getTeamList (
            @RequestHeader(value = "Authorization") String accountToken,
            @RequestHeader(value = "UserId") String userId) throws ForwardException {
        return teamService.getTeamList();
    }

    @GetMapping(value = "/teams/{teamId}")
    public Object getTeamInfo (
            @RequestHeader(value = "Authorization") String acocuntToken,
            @RequestHeader(value = "UserId") String userId,
            @PathVariable(value = "teamId") Integer teamId) throws ForwardException {
        return teamService.getTeamDetails(userId, teamId);
    }
}
