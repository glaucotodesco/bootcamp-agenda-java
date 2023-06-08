package com.abutua.agenda.dto;

import java.util.List;

public record WorkScheduleDTO (
    Long id,
    String name,
    List<WorkScheduleItemDTO> workshedule

){}
    
