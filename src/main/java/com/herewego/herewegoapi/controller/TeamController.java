package com.herewego.herewegoapi.controller;


import com.herewego.herewegoapi.response.ApiResponse;
import com.herewego.herewegoapi.vo.RegisterUserVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class TeamController {
    @GetMapping(value = "/teams")
    public Object getUserInformation(
            @RequestHeader(value = "Account-Token") String accountToken,
            @RequestHeader(value = "Account-Id") String accountId,
            @PathVariable("teamName") String teamName,
            @PathVariable("league") String leagueName)  {

        return ApiResponse.ok();
    }
}
