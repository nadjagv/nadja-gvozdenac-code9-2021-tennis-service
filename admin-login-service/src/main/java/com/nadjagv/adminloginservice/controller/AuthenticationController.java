package com.nadjagv.adminloginservice.controller;

import com.nadjagv.adminloginservice.config.WebSecurityConfig;
import com.nadjagv.adminloginservice.domain.Admin;
import com.nadjagv.adminloginservice.dto.AdminTokenState;
import com.nadjagv.adminloginservice.dto.JwtAuthenticationRequest;
import com.nadjagv.adminloginservice.service.RoleService;
import com.nadjagv.adminloginservice.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private RoleService roleService;
//    @Autowired
//    private WebSecurityConfig ws;


    @PostMapping("/login")
    public ResponseEntity<AdminTokenState> createAuthenticationToken
            (@RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) throws Exception {

        //System.out.println(ws.passwordEncoder().encode(authenticationRequest.getPassword()));

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Admin admin = (Admin) authentication.getPrincipal();

        if (!admin.isEnabled()) {
            throw new Exception();
        }

        String jwt = tokenUtils.generateToken(admin);
        long expiresIn = (long) tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new AdminTokenState(jwt, expiresIn));
    }
}


