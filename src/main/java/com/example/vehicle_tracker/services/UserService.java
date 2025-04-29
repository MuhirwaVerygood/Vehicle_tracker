package com.example.vehicle_tracker.services;

import com.example.vehicle_tracker.config.JwtService;
import com.example.vehicle_tracker.dto.AuthenticationRequest;
import com.example.vehicle_tracker.dto.AuthenticationResponse;
import com.example.vehicle_tracker.dto.ChangePasswordRequest;
import com.example.vehicle_tracker.dto.RegisterRequest;
import com.example.vehicle_tracker.exceptions.UserAlreadyExistsException;
import com.example.vehicle_tracker.models.User;
import com.example.vehicle_tracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    public AuthenticationResponse register(RegisterRequest request){
        Optional<User> userExists = repository.findByEmail(request.getEmail());
        if(userExists.isPresent()){
            throw new UserAlreadyExistsException("User with that email already exists");
        }

        var user = User
                .builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phoneNumber(request.getPhoneNumber())
                .nationalId(request.getNationalId())
                .build();

        var  savedUser = repository.save(user);
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }







    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
      try{
          // authenticate the user
          authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(
                          request.getEmail(),
                          request.getPassword()
                  )
          );

          var user = repository.findByEmail(request.getEmail())
                  .orElseThrow(()-> new UsernameNotFoundException("User not found"));

          var accessToken = jwtService.generateToken(user);
          String refreshToken = jwtService.generateRefreshToken(user);

          System.out.println(user.getAuthorities());
          return AuthenticationResponse
                  .builder()
                  .accessToken(accessToken)
                  .refreshToken(refreshToken)
                  .build();

      }catch (BadCredentialsException e){
          throw new BadCredentialsException("Invalid email or password");
      }
    }
}