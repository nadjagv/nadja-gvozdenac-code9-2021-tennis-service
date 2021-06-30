package com.nadjagv.timeslotservice.util;

public interface Mapper<E, D> {

    E dtoToEntity(D dto);

    D entityToDto(E entity);
}
