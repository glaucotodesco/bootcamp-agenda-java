INSERT INTO TBL_AREA (name) VALUES('Fisioterapia');
INSERT INTO TBL_AREA (name) VALUES('Terapia Ocupacional');

INSERT INTO TBL_APPOINTMENT_TYPE (type) VALUES('Particular');
INSERT INTO TBL_APPOINTMENT_TYPE (type) VALUES('Convênio');  


INSERT INTO TBL_PERSON (phone,name) VALUES('15 992231122', 'Ana Maria');
INSERT INTO TBL_PERSON (phone,name) VALUES('15 923233212', 'Pedro Silva');
INSERT INTO TBL_PERSON (phone,name) VALUES('11 902324322', 'Marco Nunes');
INSERT INTO TBL_PERSON (phone,name) VALUES('13 999216212', 'Marcelo Silva');
INSERT INTO TBL_PERSON (phone,name) VALUES('13 999216212', 'Fernanda Cruz');

INSERT INTO TBL_CLIENT VALUES(DATE '2000-08-02', 1);
INSERT INTO TBL_CLIENT VALUES(DATE '1998-01-22', 2);

INSERT INTO TBL_PROFESSIONAL VALUES(TRUE, 3);
INSERT INTO TBL_PROFESSIONAL VALUES(FALSE, 4);
INSERT INTO TBL_PROFESSIONAL VALUES(TRUE, 5);


INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '11:00:00-03', TIME WITH TIME ZONE '11:30:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '11:30:00-03', TIME WITH TIME ZONE '12:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '12:00:00-03', TIME WITH TIME ZONE '12:30:00-03', 1, 3, 1,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '12:30:00-03', TIME WITH TIME ZONE '13:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '13:00:00-03', TIME WITH TIME ZONE '13:30:00-03', 1, 3, 1,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '13:30:00-03', TIME WITH TIME ZONE '14:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '14:00:00-03', TIME WITH TIME ZONE '14:30:00-03', 1, 3, 1,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '14:30:00-03', TIME WITH TIME ZONE '15:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '17:00:00-03', TIME WITH TIME ZONE '17:30:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '17:30:00-03', TIME WITH TIME ZONE '18:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '18:00:00-03', TIME WITH TIME ZONE '18:30:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '18:30:00-03', TIME WITH TIME ZONE '19:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '19:00:00-03', TIME WITH TIME ZONE '19:30:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '19:30:00-03', TIME WITH TIME ZONE '20:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '20:00:00-03', TIME WITH TIME ZONE '20:30:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-05 00:00:00-03', TIME WITH TIME ZONE '20:30:00-03', TIME WITH TIME ZONE '21:00:00-03', 2, 3, 2,'OPEN',1);


INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '11:00:00-03', TIME WITH TIME ZONE '11:30:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '11:30:00-03', TIME WITH TIME ZONE '12:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '12:00:00-03', TIME WITH TIME ZONE '12:30:00-03', 1, 3, 1,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '12:30:00-03', TIME WITH TIME ZONE '13:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '13:00:00-03', TIME WITH TIME ZONE '13:30:00-03', 1, 3, 1,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '13:30:00-03', TIME WITH TIME ZONE '14:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '14:00:00-03', TIME WITH TIME ZONE '14:30:00-03', 1, 3, 1,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '14:30:00-03', TIME WITH TIME ZONE '15:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '17:00:00-03', TIME WITH TIME ZONE '17:30:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '17:30:00-03', TIME WITH TIME ZONE '18:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '18:00:00-03', TIME WITH TIME ZONE '18:30:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '18:30:00-03', TIME WITH TIME ZONE '19:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '19:00:00-03', TIME WITH TIME ZONE '19:30:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '19:30:00-03', TIME WITH TIME ZONE '20:00:00-03', 2, 3, 2,'OPEN',1);
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-12 00:00:00-03', TIME WITH TIME ZONE '20:00:00-03', TIME WITH TIME ZONE '20:30:00-03', 2, 3, 2,'OPEN',1);

INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status,area_id) VALUES (TIMESTAMP WITH TIME ZONE '2023-06-19 00:00:00-03', TIME WITH TIME ZONE '12:30:00-03', TIME WITH TIME ZONE '13:00:00-03', 2, 3, 2,'OPEN',1);



INSERT INTO TBL_AREA_PROFESSIONAL VALUES(1, 3);
INSERT INTO TBL_AREA_PROFESSIONAL VALUES(2, 3);
INSERT INTO TBL_AREA_PROFESSIONAL VALUES(2, 4);
INSERT INTO TBL_AREA_PROFESSIONAL VALUES(1, 5);

INSERT INTO TBL_WORK_SCHEDULE_ITEM (professional_id, day_Of_Week,start_Time,end_Time, slots, slot_size) VALUES (3,0,TIME WITH TIME ZONE '17:00:00-03',TIME WITH TIME ZONE '21:00:00-03', 8, 30);
INSERT INTO TBL_WORK_SCHEDULE_ITEM (professional_id, day_Of_Week,start_Time,end_Time, slots, slot_size) VALUES (3,0,TIME WITH TIME ZONE '11:00:00-03',TIME WITH TIME ZONE '15:00:00-03', 8, 30);
INSERT INTO TBL_WORK_SCHEDULE_ITEM (professional_id, day_Of_Week,start_Time,end_Time, slots, slot_size) VALUES (5,0,TIME WITH TIME ZONE '11:00:00-03',TIME WITH TIME ZONE '15:00:00-03', 8, 30);
INSERT INTO TBL_WORK_SCHEDULE_ITEM (professional_id, day_Of_Week,start_Time,end_Time, slots, slot_size) VALUES (5,1,TIME WITH TIME ZONE '11:00:00-03',TIME WITH TIME ZONE '15:00:00-03', 8, 30);
INSERT INTO TBL_WORK_SCHEDULE_ITEM (professional_id, day_Of_Week,start_Time,end_Time, slots, slot_size) VALUES (5,2,TIME WITH TIME ZONE '11:00:00-03',TIME WITH TIME ZONE '15:00:00-03', 8, 30);
INSERT INTO TBL_WORK_SCHEDULE_ITEM (professional_id, day_Of_Week,start_Time,end_Time, slots, slot_size) VALUES (5,3,TIME WITH TIME ZONE '11:00:00-03',TIME WITH TIME ZONE '15:00:00-03', 8, 30);
INSERT INTO TBL_WORK_SCHEDULE_ITEM (professional_id, day_Of_Week,start_Time,end_Time, slots, slot_size) VALUES (5,4,TIME WITH TIME ZONE '11:00:00-03',TIME WITH TIME ZONE '15:00:00-03', 8, 30);



