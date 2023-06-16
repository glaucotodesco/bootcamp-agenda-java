package com.abutua.agenda.domain.mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.abutua.agenda.domain.entites.Area;
import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.dto.AreaRequestDTO;
import com.abutua.agenda.dto.AreaResponseDTO;
import com.abutua.agenda.dto.AreaWithProfessionalsResponseDTO;
import com.abutua.agenda.dto.ProfessionalResponseDTO;



public class AreaMapper {

    public static Area areaFromDTO(AreaRequestDTO areaRequest) {

        Area area = new Area(areaRequest.name());
        areaRequest.professionals()
                .stream()
                .map(p -> new Professional(p.id()))
                .forEach(area.getProfessionals()::add);

        return area;
    }

    public static AreaResponseDTO toResponseAreaDTO(Area area) {
        return new AreaResponseDTO(area.getId(), area.getName());
    }

    public static List<AreaResponseDTO> toListResponseAreaDTO(List<Area> areas) {
        return areas.stream()
                .map(a -> toResponseAreaDTO(a))
                .collect(Collectors.toList());
    }

    public static void updateArea(Area area, AreaRequestDTO areaResponseDTO){
        final String name = StringUtils.hasText(areaResponseDTO.name()) ? areaResponseDTO.name() : area.getName();
        area.setName(name);
        area.getProfessionals().clear();
        areaResponseDTO.professionals()
                .stream()
                .map(p -> new Professional(p.id()))
                .forEach(area.getProfessionals()::add);
    }

    public static AreaWithProfessionalsResponseDTO toResponseAreaWithProfessionalDTO(Area area) {
        Set<ProfessionalResponseDTO> professionals = new HashSet<ProfessionalResponseDTO>();

        area.getProfessionals()
                .stream()
                .map(p -> ProfessionalMapper.toResponseProfessionalDTO(p))
                .forEach(professionals::add);

        return new AreaWithProfessionalsResponseDTO(area.getId(), area.getName(), professionals);
    }
}
