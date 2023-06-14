package com.abutua.agenda.dto;

import java.util.List;

public record WorkScheduleResponseDTO(
        Long id,
        String name,
        List<WorkScheduleItemResponseDTO> workshedule

) {
}
