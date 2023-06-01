
INSERT INTO TBL_AREA (name) VALUES('Fisioterapia');
INSERT INTO TBL_AREA (name) VALUES('Terapia Ocupacional');

INSERT INTO TBL_APPOINTMENT_TYPE (type) VALUES('Particular');
INSERT INTO TBL_APPOINTMENT_TYPE (type) VALUES('Convênio');


INSERT INTO TBL_PERSON (phone,email,name,comments) VALUES('15 997079576', 'ana@gmail.com', 'Ana Maria', 'Cliente Vip');
INSERT INTO TBL_PERSON (phone,email,name,comments) VALUES('15 923233212', 'pedro@gmail.com', 'Pedro Silva', '');
INSERT INTO TBL_PERSON (phone,email,name,comments) VALUES('11 90232322', 'marco@gmail.com', 'Marco Nunes', '');
INSERT INTO TBL_PERSON (phone,email,name,comments) VALUES('13 99921212', 'marcelo@gmail.com', 'Marcelo Silva', 'Particular');

INSERT INTO TBL_CLIENT VALUES(DATE '2000-08-02', 1, 'FEMALE');
INSERT INTO TBL_CLIENT VALUES(DATE '1998-01-22', 2, 'MALE');

INSERT INTO TBL_PROFESSIONAL VALUES(TRUE, 3);
INSERT INTO TBL_PROFESSIONAL VALUES(TRUE, 4);


INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status) VALUES(TIMESTAMP WITH TIME ZONE '2023-08-01 00:00:00-03', TIME WITH TIME ZONE '13:30:00-03', TIME WITH TIME ZONE '13:00:00-03', 1, 3, 1,'OPEN');
INSERT INTO TBL_APPOINTMENT (date, start_Time, end_Time,client_id,professional_id,appointment_type_id, status) VALUES(TIMESTAMP WITH TIME ZONE '2023-10-01 00:00:00-03', TIME WITH TIME ZONE '14:30:00-03', TIME WITH TIME ZONE '14:00:00-03', 2, 4, 2,'OPEN');


INSERT INTO TBL_APPOINTMENT_COMMENT (appointment_id, moment, comment) VALUES(1, TIMESTAMP WITH TIME ZONE '2023-05-31 22:10:04.483722+00', 'Primeiro atendimento');
INSERT INTO TBL_APPOINTMENT_COMMENT (appointment_id, moment, comment) VALUES(2, TIMESTAMP WITH TIME ZONE '2023-05-31 22:10:04.497721+00', 'Parcelar em 3x');

INSERT INTO TBL_AREA_PROFESSIONAL VALUES(1, 3);
INSERT INTO TBL_AREA_PROFESSIONAL VALUES(2, 3);
INSERT INTO TBL_AREA_PROFESSIONAL VALUES(2, 4);

