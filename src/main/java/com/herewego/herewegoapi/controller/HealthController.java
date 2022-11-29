package com.herewego.herewegoapi.controller;

import com.herewego.herewegoapi.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {
    @GetMapping(value = "/health")
    public ResponseEntity<?> health()  {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
