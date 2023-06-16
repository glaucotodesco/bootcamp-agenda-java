package com.abutua.agenda.dto;

import java.util.List;

public record WorkScheduleResponse(
        Long id,
        String name,
        List<WorkScheduleItemResponse> workshedule

) {
}
