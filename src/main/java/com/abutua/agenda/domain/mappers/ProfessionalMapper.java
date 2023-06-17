package com.abutua.agenda.domain.mappers;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.abutua.agenda.domain.entites.Area;
import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.dto.AreaResponse;
import com.abutua.agenda.dto.ProfessionalRequest;
import com.abutua.agenda.dto.ProfessionalResponse;
import com.abutua.agenda.dto.ProfessionalWithAreasResponse;

public class ProfessionalMapper {

    public static ProfessionalResponse toResponseProfessionalDTO(Professional professional) {
        return new ProfessionalResponse(professional.getId(), professional.getName(), professional.getPhone(), professional.isActive());
    }

    public static ProfessionalWithAreasResponse toResponseProfessionalWithAreasDTO(Professional professional) {
        List<AreaResponse> areasResponse = professional.getAreas().stream()
                         .map( a -> AreaMapper.toResponseAreaDTO(a))
                         .collect(Collectors.toList());
        
        return new ProfessionalWithAreasResponse(professional.getId(),professional.getName(),professional.getPhone(),professional.isActive(),areasResponse);
    }
    

    public static Professional professionalFromDTO(ProfessionalRequest professionalRequest) {
        Professional professional = new Professional();
        BeanUtils.copyProperties(professionalRequest, professional);

        professional.getAreas().addAll(professionalRequest.areas()
                               .stream()
                               .map(a -> new Area(a.id()))
                               .collect(Collectors.toList()));
        return professional;
    }

      public static void updateProfessional(Professional professional, ProfessionalRequest professionalRequest){
        final String name = StringUtils.hasText( professionalRequest.name()) ? professionalRequest.name() : professional.getName();
        final String phone = StringUtils.hasText( professionalRequest.phone()) ? professionalRequest.phone() : professional.getPhone();
        professional.setName(name);
        professional.setPhone(phone);
        professional.setActive(professionalRequest.active());
        professional.getAreas().clear();
        professionalRequest.areas().stream().map( a -> new Area(a.id())).forEach(professional.getAreas()::add);
    }
}
