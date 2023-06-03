package com.abutua.agenda.entites.runners;


import java.time.DayOfWeek;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.abutua.agenda.entites.WorkScheduleItem;
import com.abutua.agenda.repositories.ProfessionalRepository;
import com.abutua.agenda.repositories.WorkScheduleItemRepository;

@Component
@Order(6)
@Profile("runner")
public class DBRunner06WorkSchedule implements ApplicationRunner {

    @Autowired
    private WorkScheduleItemRepository workScheduleItemRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    

    @Override
    public void run(ApplicationArguments args) throws Exception {

        WorkScheduleItem wsi1 = new WorkScheduleItem(DayOfWeek.MONDAY, LocalTime.parse("08:00:00"), LocalTime.parse("12:00:00"));
        wsi1.setProfessional(professionalRepository.findById(3L).get());
        wsi1.setSlotSize(30);
        wsi1.setSlots(8);

        WorkScheduleItem wsi2 = new WorkScheduleItem(DayOfWeek.MONDAY, LocalTime.parse("14:00:00"), LocalTime.parse("18:00:00"));
        wsi2.setProfessional(professionalRepository.findById(3L).get());
        wsi2.setSlotSize(30);
        wsi2.setSlots(8);


        workScheduleItemRepository.save(wsi1);
        workScheduleItemRepository.save(wsi2);

    
    }

}
