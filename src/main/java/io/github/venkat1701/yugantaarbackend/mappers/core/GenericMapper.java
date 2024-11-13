package io.github.venkat1701.yugantaarbackend.mappers.core;

public interface GenericMapper<DTO, ENTITY> {
    DTO toDTO(ENTITY entity);

    ENTITY toEntity(DTO dto);

}
