package com.nadjagv.adminloginservice.repository;

import com.nadjagv.adminloginservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findOneByName(String name);

    Role findOneById(Integer id);


}
