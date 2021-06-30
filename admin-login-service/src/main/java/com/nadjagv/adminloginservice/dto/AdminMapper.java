package com.nadjagv.adminloginservice.dto;

import com.nadjagv.adminloginservice.domain.Admin;
import com.nadjagv.adminloginservice.util.Mapper;

public class AdminMapper implements Mapper<Admin, AdminDTO> {
    @Override
    public Admin dtoToEntity(AdminDTO dto) {
        return Admin.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    @Override
    public AdminDTO entityToDto(Admin entity) {
        return AdminDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }
}
