package com.example.vehicle_tracker.controllers;

import java.io.IOException;
import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.vehicle_tracker.dto.AuthenticationRequest;
import com.example.vehicle_tracker.dto.AuthenticationResponse;
import com.example.vehicle_tracker.dto.ChangePasswordRequest;
import com.example.vehicle_tracker.dto.RegisterRequest;
import com.example.vehicle_tracker.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<?> register(

      @RequestBody RegisterRequest request
  ) {
    return service.register(request);
  }
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return service.authenticate(request);
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


  @PatchMapping
  public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
  ) {
      service.changePassword(request, connectedUser);
      return ResponseEntity.ok().build();
  }


}