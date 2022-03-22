package com.bouaziz.saraha.controller;

import com.bouaziz.saraha.dto.AuthentificationRequest;
import com.bouaziz.saraha.dto.AuthentificationResponse;
import com.bouaziz.saraha.dto.UserDto;
import com.bouaziz.saraha.services.UserService;
import com.bouaziz.saraha.services.auth.AppUserDetailsService;
import com.bouaziz.saraha.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")//issm ressource à la fin 's':pluriels
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;


    @PostMapping("/register")
    public ResponseEntity<UserDto> save(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthentificationResponse> login(@RequestBody AuthentificationRequest request) {
       //this code snippet does all the authentification process
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        final UserDetails userDetails=userDetailsService.loadUserByUsername(request.getEmail());
        final String jwt=jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(
                AuthentificationResponse.builder()
                        .token(jwt)
                        .build()
        );
    }
    @GetMapping("/{user-email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable("user-email") String userEmail) {
        return ResponseEntity.ok(userService.findByEmail(userEmail));
    }

    @GetMapping("recently-joined-users")
    public ResponseEntity<List<UserDto>> recentlyJoinedUsers(){
        return ResponseEntity.ok(userService.recentlyJoinedUsers());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(
            @RequestParam(value= "first-name",required = false) String firstName,
            @RequestParam(value="last-name", required = false) String lastName,
            @RequestParam(value="user-name", required = false) String userName
            ){
        return ResponseEntity
                .ok(userService.searchUser(firstName,lastName,userName));
    }
}
