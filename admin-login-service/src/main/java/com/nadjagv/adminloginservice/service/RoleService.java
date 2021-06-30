package com.nadjagv.adminloginservice.service;

import com.nadjagv.adminloginservice.domain.Role;
import com.nadjagv.adminloginservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findById(Integer id) {
        Role auth = this.roleRepository.findOneById(id);
        return auth;
    }

    public Role findByName(String name) {
        Role role = this.roleRepository.findOneByName(name);
        return role;
    }

}
