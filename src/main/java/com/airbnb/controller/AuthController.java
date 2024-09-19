package com.airbnb.controller;


import com.airbnb.entity.AppUser;
import com.airbnb.exception.UserExists;
import com.airbnb.payload.JWTToken;
import com.airbnb.payload.LoginDto;
import com.airbnb.repository.AppUserRepository;
import com.airbnb.service.AppUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {

    private AppUserRepository appUserRepository;
    private AppUserServiceImpl appUserService;

    @Autowired
    public AuthController(AppUserRepository appUserRepository, AppUserServiceImpl appUserService){
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
    }

    @PostMapping("/createUser")
    public ResponseEntity <AppUser> createUser(@RequestBody AppUser user){

        Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            throw new UserExists("Email Id Exists");
        }

        Optional<AppUser> opUsername = appUserRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            throw new UserExists("username Exists");
        }
        user.setRole("ROLE_USER");
        AppUser savedUser = appUserService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/createpropertyowner")
    public ResponseEntity <AppUser> createPropertyOwner(@RequestBody AppUser user){

        Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            throw new UserExists("Email Id Exists");
        }

        Optional<AppUser> opUsername = appUserRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            throw new UserExists("username Exists");
        }
        user.setRole("ROLE_OWNER");
        AppUser savedUser = appUserService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/createpropertymanager")
    public ResponseEntity <AppUser> createPropertyManager(@RequestBody AppUser user){

        Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            throw new UserExists("Email Id Exists");
        }

        Optional<AppUser> opUsername = appUserRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            throw new UserExists("username Exists");
        }
        user.setRole("ROLE_MANAGER");
        AppUser savedUser = appUserService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody LoginDto loginDto){
        String token=appUserService.verifyLogin(loginDto);
        JWTToken jwtToken = new JWTToken();
        if(token!=null){
            jwtToken.setTokenType("JWT");
            jwtToken.setToken(token);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
        }

    }
}