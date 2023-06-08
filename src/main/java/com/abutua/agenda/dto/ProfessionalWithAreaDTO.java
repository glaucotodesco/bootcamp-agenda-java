package com.abutua.agenda.dto;

import java.util.List;

public record ProfessionalWithAreaDTO (
    long id,
    String name,
    String phone,
    String email,
    String comments,
    boolean active,
    List<AreaDTO> areas
) {

    
    
}
