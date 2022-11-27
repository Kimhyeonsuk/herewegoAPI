package com.herewego.herewegoapi.controller;

import com.herewego.herewegoapi.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {
    @GetMapping(value = "/health")
    public Object health()  {

        return ApiResponse.ok();
    }
}
