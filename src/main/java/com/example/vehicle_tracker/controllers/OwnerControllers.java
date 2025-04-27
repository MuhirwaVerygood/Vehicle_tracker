package com.example.vehicle_tracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerControllers {
    @GetMapping
    public String getHello(){
        return "Hello World";
    }
}
