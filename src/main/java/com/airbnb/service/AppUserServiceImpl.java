package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.airbnb.payload.LoginDto;
import com.airbnb.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AppUserServiceImpl {
    private AppUserRepository appUserRepository;
    private JWTService jwtService;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    public AppUser createUser(AppUser user) {
        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));
        user.setPassword(hashpw);
        return appUserRepository.save(user);
    }

    public String verifyLogin(LoginDto loginDto) {
        //Optional<AppUser> user = appUserRepository.findByUsername(loginDto.getUsername());

        Optional<AppUser> user = appUserRepository.findByEmailOrUsername(loginDto.getEmail(), loginDto.getUsername());
        if (user.isPresent()) {
            AppUser appUser = user.get();
            if (BCrypt.checkpw(loginDto.getPassword(), appUser.getPassword())) {
                return jwtService.generateToken(appUser);
            }
        }
        return null;
    }

}
