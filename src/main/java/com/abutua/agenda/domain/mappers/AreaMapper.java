package com.abutua.agenda.domain.mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.abutua.agenda.domain.entites.Area;
import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.dto.AreaRequest;
import com.abutua.agenda.dto.AreaResponse;
import com.abutua.agenda.dto.AreaWithProfessionalsResponse;
import com.abutua.agenda.dto.ProfessionalResponse;



public class AreaMapper {

    public static Area areaFromDTO(AreaRequest areaRequest) {
        Area area = new Area(areaRequest.name());
        areaRequest.professionals()
                .stream()
                .map(p -> new Professional(p.id()))
                .forEach(area.getProfessionals()::add);

        return area;
    }

    public static AreaResponse toResponseAreaDTO(Area area) {
        return new AreaResponse(area.getId(), area.getName());
    }

    public static List<AreaResponse> toListResponseAreaDTO(List<Area> areas) {
        return areas.stream()
                .map(a -> toResponseAreaDTO(a))
                .collect(Collectors.toList());
    }

    public static void updateArea(Area area, AreaRequest areaResponseDTO){
        final String name = StringUtils.hasText(areaResponseDTO.name()) ? areaResponseDTO.name() : area.getName();
        area.setName(name);
        area.getProfessionals().clear();
        areaResponseDTO.professionals()
                .stream()
                .map(p -> new Professional(p.id()))
                .forEach(area.getProfessionals()::add);
    }

    public static AreaWithProfessionalsResponse toResponseAreaWithProfessionalDTO(Area area) {
        Set<ProfessionalResponse> professionals = new HashSet<ProfessionalResponse>();
        area.getProfessionals()
                .stream()
                .map(p -> ProfessionalMapper.toResponseProfessionalDTO(p))
                .forEach(professionals::add);

        return new AreaWithProfessionalsResponse(area.getId(), area.getName(), professionals);
    }
}
