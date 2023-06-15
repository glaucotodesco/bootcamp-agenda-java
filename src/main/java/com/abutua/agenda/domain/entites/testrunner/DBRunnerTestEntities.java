package com.abutua.agenda.domain.entites.testrunner;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.abutua.agenda.domain.entites.Appointment;
import com.abutua.agenda.domain.entites.AppointmentType;
import com.abutua.agenda.domain.entites.Area;
import com.abutua.agenda.domain.entites.Client;
import com.abutua.agenda.domain.entites.Professional;
import com.abutua.agenda.domain.entites.WorkScheduleItem;
import com.abutua.agenda.domain.repositories.AppointmentRepository;
import com.abutua.agenda.domain.repositories.AppointmentTypeRepository;
import com.abutua.agenda.domain.repositories.AreaRepository;
import com.abutua.agenda.domain.repositories.ClientRepository;
import com.abutua.agenda.domain.repositories.ProfessionalRepository;
import com.abutua.agenda.domain.repositories.WorkScheduleItemRepository;

@Component
@Profile("runner")
public class DBRunnerTestEntities implements ApplicationRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private AppointmentTypeRepository appointmentTypeRepository;

    
    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private WorkScheduleItemRepository workScheduleItemRepository;

    

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Client c1 = new Client("Ana Maria", "15 997079576", LocalDate.parse("2000-08-02"));
        Client c2 = new Client("Pedro Silva", "15 923233212", LocalDate.parse("1998-01-22"));

        clientRepository.save(c1);
        clientRepository.save(c2);

        Area a1 = new Area("Fisioterapia");
        Area a2 = new Area("Terapia Ocupacional");

        areaRepository.save(a1);
        areaRepository.save(a2);

        AppointmentType apty1 = new AppointmentType("Particular");
        AppointmentType apty2 = new AppointmentType("Convênio");

        appointmentTypeRepository.save(apty1);
        appointmentTypeRepository.save(apty2);

        Professional p1 = new Professional("Marco Nunes", "11 90232322", true);
        Professional p2 = new Professional("Marcelo Silva", "13 99921212", true);

        a1 = areaRepository.findById(1).get();
        a2 = areaRepository.findById(2).get();

        p1.addArea(a1);
        p1.addArea(a2);
        p2.addArea(a2);

        p1.addWorkScheduleItem(DayOfWeek.MONDAY, LocalTime.parse("08:00:00"), LocalTime.parse("12:00:00"));
        p1.addWorkScheduleItem(DayOfWeek.THURSDAY, LocalTime.parse("08:00:00"), LocalTime.parse("12:00:00"));
        p1.addWorkScheduleItem(DayOfWeek.WEDNESDAY, LocalTime.parse("14:00:00"), LocalTime.parse("18:00:00"));
        p2.addWorkScheduleItem(DayOfWeek.WEDNESDAY, LocalTime.parse("14:00:00"), LocalTime.parse("18:00:00"));

        professionalRepository.save(p1);
        professionalRepository.save(p2);


            // Create a appointment do Client 1
        c1 = clientRepository.getReferenceById(1L);
        apty1 = appointmentTypeRepository.getReferenceById(1);
        p1 = professionalRepository.getReferenceById(3L);
        

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



        Appointment ap1 = new Appointment();
        ap1.setClient(c1);
        ap1.setDate(LocalDate.parse("2023-08-01"));
        ap1.setStartTime(LocalTime.parse("10:00:00"));
        ap1.setEndTime(LocalTime.parse("10:30:00"));
        ap1.setType(apty1);
        ap1.setProfessional(p1);
        ap1.setComments("Primeiro atendimento");

        appointmentRepository.save(ap1);

        // Create a appointment do Client 1
        c2 = clientRepository.getReferenceById(2L);
        apty2 = appointmentTypeRepository.getReferenceById(2);
        p2 = professionalRepository.getReferenceById(4L);

        Appointment ap2 = new Appointment();
        ap2.setClient(c2);
        ap2.setDate(LocalDate.parse("2023-10-01"));
        ap2.setStartTime(LocalTime.parse("11:00:00"));
        ap2.setEndTime(LocalTime.parse("11:30:00"));
        ap2.setType(apty2);
        ap2.setProfessional(p2);
        ap2.setComments("Parcelar em 3x");

        appointmentRepository.save(ap2);


    

    }

}
