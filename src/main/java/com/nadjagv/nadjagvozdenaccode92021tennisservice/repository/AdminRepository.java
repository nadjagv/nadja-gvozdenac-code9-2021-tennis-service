package com.nadjagv.adminloginservice.repository;

import com.nadjagv.adminloginservice.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
