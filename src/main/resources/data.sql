INSERT INTO doctor (id, name, address, phone_number, email_address, specialization)
VALUES (1, 'Dr. Piet Bakker', 'Dorpstraat 45', '06-12345612', 'p.bakker@gezondheidcentrumcompagnie.nl', 'Huisarts'),
       (2, 'Dr. Marie Elzinga', 'Eekhoornstraat 54', '06-45455667', 'm.elzinga@gezondheidcentrumcompagnie.nl', 'Huisarts'),
       (3, 'Dr. Mark Jensen', 'Badhoevepleain 14', '06-78599995', 'm.jensen@gezondheidcentrumcompagnie.nl', 'Kinderarts'),
       (4, 'Dr. Mo Amin', 'Hollandiastraat 34', '06-878577824', 'm.amin@gezondheidcentrumcompagnie.nl', 'KNO arts'),
       (5, 'Dr. Sandra Boudewijn', 'Bloemenbuurt 15', '06-458364495', 's.boudewijn@gezondheidcentrumcompagnie.nl', 'Gynaecoloog');

INSERT INTO doctor_available_days (doctor_id, available_days)
VALUES (1, 1), (1, 3), (1, 5),
       (2, 2), (2, 4), (2, 6),
       (3, 1), (3, 3), (3, 4),
       (4, 2), (4, 4), (4, 5),
       (5, 1), (5, 3), (5, 6);

INSERT INTO doctor_available_times (doctor_id, available_times)
VALUES (1, '09:00:00'), (1, '17:00:00'),
       (2, '10:00:00'), (2, '18:00:00'),
       (3, '11:00:00'), (3, '19:00:00'),
       (4, '12:00:00'), (4, '20:00:00'),
       (5, '13:00:00'), (5, '21:00:00');

INSERT INTO patient (id, first_name, last_name, email, phone_number, address, insurance_provider, insurance_number)
VALUES (1, 'Jan', 'Smith', 'jan.smith@outlook.com', '06-563344668', 'Seizoenstraat 34', 'OHRA', 'ABC123'),
       (2, 'Bob', 'Miedema', 'bob.miedema@yahoo.com', '06-678966678', 'Bouwmeesterstraat 25', 'VGZ', 'BCD234'),
       (3, 'Carol', 'Bottse', 'carol.bottse@outlook.com', '06-664233354', 'Markerkant 14', 'Achmea', 'CDE345'),
       (4, 'David', 'Jansen', 'david.jansen@gmail.com', '06-543266775', 'Parkstraat 78', 'FBTO', 'DEF456'),
       (5, 'Eve', 'Hoekstra', 'eve.hoekstra@gmail.com', '06-876565623', 'Waterwijk 34', 'asr', 'EFG567'),
       (6, 'Jill', 'Davis', 'jill.davis@outlook.com', '06-334455776', 'Kruidenwijk 34', 'CZ Direct', 'EDG446');

INSERT INTO appointments (patient_id, doctor_id, date, start_time, end_time, location, status) VALUES
                                                                                              (1, 2, '2023-05-01', '10:00:00', '11:00:00', 'Archipel','Bevestigd'),
                                                                                              (2, 3, '2023-05-02', '14:00:00', '15:00:00', 'DeBoog','In afwachting'),
                                                                                              (3, 1, '2023-05-03', '09:30:00', '10:30:00', 'Filmwijk','Bevestigd'),
                                                                                              (4, 5, '2023-05-04', '11:00:00', '12:00:00', 'Castrovalva','In afwachting bevestiging patient'),
                                                                                              (5, 4, '2023-05-05', '13:30:00', '14:30:00', 'Prisma','Bevestigd');

INSERT INTO invoice (id, patient_id, invoice_amount, paid, overdue, submitted)
VALUES (1, 1, 100.0, true, false, true),
       (2, 2, 150.0, false, false, true),
       (3, 3, 200.0, true, false, true),
       (4, 4, 250.0, false, true, true),
       (5, 5, 300.0, false, false, false);

INSERT INTO medical_record (patient_id, notes)
VALUES (1, 'Allergie voor penicilline. Laatste controle: 2023-04-28.'),
       (2, 'Geen bekende allergieën. Laatste controle: 2023-04-27.'),
       (3, 'Allergie voor noten. Laatste controle: 2023-04-26.'),
       (4, 'Allergie voor bijensteken. Laatste controle: 2023-04-25.'),
       (5, 'Geen bekende allergieën. Laatste controle: 2023-04-24.');

INSERT INTO users (username, password, email, enabled) VALUES ('kbhaggoe1535', '$2a$12$SEWy4ggZaD24H71T9wvVo.d8.LeBUlcZPJz87BHfIwTR7yQmSzthS
','k.bhaggoe@outlook.nl', TRUE);
INSERT INTO users (username, password, email, enabled) VALUES ('mhuizinga1353', '$2a$12$EXmJf5dYI7ZQFQe/jUbtiOk/Xp15GEgPMSiXEHpuCNQDLKdpKIfEK', 'm.huizinga@gezondheidcentrumcompagnie.nl', TRUE);
INSERT INTO users (username, password, email, enabled) VALUES ('pbakker4542', '$2a$12$bH6AJvdfMwJyTPJP5SoneeDOYzOgtqGa65/eX2qTiMOmj0hu/jebO', 'p.bakker@gezondheidcentrumcompagnie.nl', TRUE);

INSERT INTO authorities (username, authority) VALUES ('kbhaggoe1535', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('mhuizinga1353', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('pbakker4542', 'ROLE_ADMIN');