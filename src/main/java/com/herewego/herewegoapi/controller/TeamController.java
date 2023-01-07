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
    public Object getUserInformation(
            @RequestHeader(value = "Account-Token") String accountToken,
            @RequestHeader(value = "Account-Id") String accountId,
            @PathVariable("teamName") String teamName,
            @PathVariable("league") String leagueName)  {

        return ApiResponse.ok();
    }

    @PutMapping(value = "/favorites")
    public Object updateFavoriteTeam(
            @RequestHeader(value = "Authorization") String accountToken,
            @RequestHeader(value = "Email") String email,
            @RequestBody UpdateFavoriteTeamVO updateFavoriteTeamVO) throws ForwardException {

        teamService.updateFavoriteTeam(email, updateFavoriteTeamVO.getTeamName(), updateFavoriteTeamVO.getLeagueName());
        return ApiResponse.ok();

    }
}
