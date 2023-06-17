package com.abutua.agenda.domain.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.domain.entites.WorkScheduleItem;
import com.abutua.agenda.dto.WorkScheduleItemResponse;
import com.abutua.agenda.dto.WorkScheduleResponse;

public class WorkScheduleMapper {
    
    public static WorkScheduleItemResponse toWorkScheduleItemResponseDTO(WorkScheduleItem workScheduleItem) {
        return new WorkScheduleItemResponse(workScheduleItem.getId(), workScheduleItem.getDayOfWeek(), workScheduleItem.getStartTime(), workScheduleItem.getEndTime());
    }

    public static WorkScheduleResponse toWorkScheduledDTO(Professional professional) {
        List<WorkScheduleItemResponse> workSchedule = professional.getWorkSchedule().stream()
                                                                                        .map( wsi -> WorkScheduleMapper.toWorkScheduleItemResponseDTO(wsi))
                                                                                        .collect(Collectors.toList());
                                                                        
        return new WorkScheduleResponse(professional.getId(), professional.getName(),workSchedule);
    }


}
