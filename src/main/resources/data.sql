Insert into hostel
values (1, 'Visweswaraya', 'IIT BHU HG');
Insert into hostel
values (2, 'SC DEY', 'IIT BHU Lanka');
select * from hostel;
insert into user values('user2', '1234', 'student');
insert into user values('user3', '1234', 'student');
insert into user values('user4', '1234', 'student');
insert into user values('user5', '1234', 'student');
insert into user values('user6', '1234', 'student');
insert into user values('user7', '1234', 'student');
insert into user values('user8', '1234', 'student');
insert into user values('user9', '1234', 'student');

select *
from hostel;
select *
from User;
select *
from studentusermapping;
insert into session values (2020, '2020 Even','2020-01-01');
insert into session values (2021, '2021 Even','2021-01-01');
insert into session values (2022, '2022 Even','2022-01-01');
insert into session values (2023, '2023 Even','2023-01-01');
insert into hostel_registration values (1, 1,2020, 50);
insert into hostel_registration values (2, 1,2021, 50);
insert into hostel_registration values (3, 1,2022, 50);
insert into hostel_registration values (4, 1,2023, 50);
insert into hostel_registration values (5, 2,2020, 50);
insert into hostel_registration values (6, 2,2021, 50);
insert into hostel_registration values (7, 2,2022, 50);
insert into hostel_registration values (8, 2,2023, 50);

insert into student(name, email, phone, branch, balance, Dob)
values ('harman', 'harman@gmail.com', 90239, 'CSE', '15000', 20 / 02 / 2002);

insert into student(name, email, phone, branch, balance, Dob)
values ('hardik', 'hardik@gmail.com', 90232198, 'CSE', 15000, '2003-07-07');

insert into studentusermapping values ('user2', 1, 1);
insert into studentusermapping values ('user3', 1, 2);
insert into studentusermapping values ('user4', 1, 3);
insert into studentusermapping values ('user5', 1, 4);
insert into studentusermapping values ('user6', 2, 1);
insert into studentusermapping values ('user7', 2, 2);
insert into studentusermapping values ('user8', 2, 3);
insert into studentusermapping values ('user9', 2, 4);


select *
from student;
insert into session(start_date)
values ('2016-02-02');
Insert into User(username, password, role)
values ('admin1', '1234', 'admin');
Insert into User(username, password, role)
values ('user1', '1234', 'student');
insert into hostel_registration(hostel_id, session)
values (1, 1);
insert into hostel_registration(hostel_id, session)
values (2, 1);
insert into messcancellations(hostelRegistrationId, rollNo, date_)
values (1, 1, '2020-07-12');
insert into messcancellations(hostelRegistrationId, rollNo, date_)
values (1, 1, '2020-07-01');

select *
from messcancellations;
update messcancellations
set date_ = '2014-03-16 00:00:00.000'
where entryNo = 2;


Insert into User values("admin1","1234","admin");


show tables;
select * from session;
select * from hostel;
select * from User;
select * from hostel_registration;
select * from student;
select * from studentusermapping;
select * from notice;
ALTER TABLE student
MODIFY COLUMN phone VARCHAR(15);

drop table notice;
select m.entryNo,
       H.hostel_id,
       H.hostel_name,
       m.rollNo,
       S.name,
       m.date_,
       s2.session_id,
       s2.start_date
from MessCancellations m
         inner join Student S on m.rollNo = S.roll
         inner join Hostel_registration Hr on m.hostelRegistrationId = Hr.hostel_registration_id
         inner join Hostel H on Hr.hostel_id = H.hostel_id
         inner join Session S2 on Hr.session_id = S2.session_id
where H.hostel_name like '%i%'
   or convert(H.hostel_id, char) like '%i%'
   or convert(rollNo, char) like '%i%'
   or name like '%i%'
   or DATE_FORMAT(date_, '%d-%m-%Y') like '%i%';
select *
from Hostel
where hostel_name like '%i%';
select *
from session;
insert into session
values (2023, '2023-01-01');
select *
from hostel_registration;
select * from user;
select * from messcancellations;
insert into messcancellations values(1,1,1,'2020-03-02');
select * from session;


truncate complaint;