package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.Role;
import com.codecool.solarwatch.model.UserEntity;
import com.codecool.solarwatch.model.payload.CreateUserRequest;
import com.codecool.solarwatch.model.payload.JwtResponse;
import com.codecool.solarwatch.model.payload.UserRequest;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import com.codecool.solarwatch.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest signUpRequest) {
        System.out.println(signUpRequest);
        UserEntity userEntity = new UserEntity(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()), Role.ROLE_USER);
        userService.createUser(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        GrantedAuthority roleAuthority = userDetails.getAuthorities().stream().findFirst().orElse(null);

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getUsername(), roleAuthority.getAuthority()));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> authenticateAdmin(@RequestBody UserRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        GrantedAuthority roleAuthority = userDetails.getAuthorities().stream().findFirst().orElse(null);

        if (roleAuthority != null && roleAuthority.getAuthority().equals("ROLE_ADMIN")) {
            JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getUsername(), roleAuthority.getAuthority());
            return ResponseEntity.ok(jwtResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @PutMapping("/promote/{username}")
    public ResponseEntity<Void> promoteUserToAdmin(@PathVariable String username) {
        return userService.promoteUserToAdmin(username);
    }

    @PutMapping("/unpromote/{username}")
    public ResponseEntity<Void> unPromoteUserFromAdmin(@PathVariable String username) {
        return userService.unPromoteUser(username);
    }

}
