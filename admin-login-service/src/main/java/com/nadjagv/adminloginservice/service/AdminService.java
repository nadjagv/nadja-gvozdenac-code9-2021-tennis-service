package com.nadjagv.adminloginservice.service;

import com.nadjagv.adminloginservice.domain.Admin;
import com.nadjagv.adminloginservice.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminRepository.findOneByEmail(username);
        if(admin == null) {
            throw new UsernameNotFoundException(String.format("User with email %s not found.", username));
        }else {
            return admin;
        }
    }
}
