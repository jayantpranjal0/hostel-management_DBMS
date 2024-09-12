use ${MYSQL_DATABASE};

show tables;

Create DATABASE hms2;

use hms2;

CREATE TABLE IF NOT EXISTS User
(
    username varchar(55) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Hostel
(
    hostel_id      INT AUTO_INCREMENT PRIMARY KEY,
    hostel_name    VARCHAR(255),
    hostel_address VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS Session
(
    session_id INT AUTO_INCREMENT PRIMARY KEY,
    session_name VARCHAR(255),
    start_date Date
);


CREATE TABLE IF NOT EXISTS hostel_registration
(
    hostel_registration_id INT AUTO_INCREMENT PRIMARY KEY,
    hostel_id              INT,
    session_id             INT,
    room_count             INT,
    FOREIGN KEY (hostel_id) REFERENCES Hostel (hostel_id) ON DELETE CASCADE,
    FOREIGN KEY (session_id) REFERENCES Session (session_id) ON DELETE SET NULL
);


# CREATE TABLE IF NOT EXISTS HostelFacilities (
#                                                 facility_id INT AUTO_INCREMENT PRIMARY KEY,
#                                                 hostel_registration_id INT,
#                                                 Description VARCHAR(255),
#                                                 Condition_status INT,
#                                                 FOREIGN KEY (hostel_registration_id) REFERENCES Hostel_registration(hostel_registration_id) ON DELETE SET NULL
# );

# CREATE TABLE IF NOT EXISTS Room
# (
#     hostelId int,
#     roomNo   int,
#     primary key (hostelId, roomNo)
# );


CREATE TABLE IF NOT EXISTS Student
(
    roll    INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255),
    email   VARCHAR(255),
    phone   VARCHAR(15),
    branch  VARCHAR(255),
    balance INT,
    dob     date
);
CREATE TABLE IF NOT EXISTS StudentUserMapping
(
    username             varchar(55) primary key,
    roll               int,
    hostelRegistrationId int,
    foreign key (hostelRegistrationId) references Hostel_registration (hostel_registration_id) on delete set null,
    foreign key (username) references User (username) on delete cascade,
    foreign key (roll) references Student (roll) on delete cascade
);

# CREATE TABLE IF NOT EXISTS RoomRegistration
# (
#     hostel_registration_id INT NOT NULL,
#     roomNo                 INT NOT NULL,
#     rollNo                 int,
#     PRIMARY KEY (hostel_registration_id, roomNo),
#     FOREIGN KEY (hostel_registration_id) REFERENCES Hostel_registration (hostel_registration_id) ON DELETE CASCADE,
#     foreign key (rollNo) REFERENCES Student (roll) ON DELETE CASCADE
# );

CREATE TABLE IF NOT EXISTS Complaint
(
    complaint_id           INT AUTO_INCREMENT PRIMARY KEY,
    hostel_registration_id INT NOT NULL,
    RollNo                 INT,
    Description            VARCHAR(1000),
    TimeStamp              TIMESTAMP,
    Status                 VARCHAR(255),
    FOREIGN KEY (hostel_registration_id) REFERENCES Hostel_registration (hostel_registration_id) ON DELETE CASCADE,
    FOREIGN KEY (RollNo) REFERENCES Student (Roll) ON DELETE SET NULL

);

CREATE TABLE IF NOT EXISTS Notice
(
    notice_id              INT AUTO_INCREMENT PRIMARY KEY,
    hostel_registration_id INT,
    description            VARCHAR(10000),
    date                   DATETIME,
    FOREIGN KEY (hostel_registration_id) REFERENCES Hostel_registration (hostel_registration_id) ON DELETE SET NULL
);


CREATE TABLE IF NOT EXISTS Job
(
    job_type VARCHAR(255) PRIMARY KEY ,
    job_salary int,
    job_details VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Employee
(
    employee_id      INT AUTO_INCREMENT PRIMARY KEY,
    employee_name    CHAR(255),
    employee_address VARCHAR(255),
    employee_details VARCHAR(255)
);



CREATE TABLE IF NOT EXISTS EmployeeUserMapping
(

    username             varchar(55) primary key,
    employee_id          int,
    job_type VARCHAR(255),
    hostel_registration_id int,
    foreign key (hostel_registration_id) references Hostel_registration (hostel_registration_id) on delete set null,
    foreign key (username) references User (username) on delete cascade,
    foreign key (employee_id) references Employee (employee_id) on delete cascade,
    foreign key (job_type) references Job (job_type) on delete cascade
);



CREATE TABLE IF NOT EXISTS MessCancellations
(
    entryNo              INT AUTO_INCREMENT PRIMARY KEY,
    hostelRegistrationId int,
    rollNo               int,
    date_                date,
    foreign key (rollNo) references Student (roll) on delete set null,
    FOREIGN KEY (hostelRegistrationId) REFERENCES Hostel_registration (hostel_registration_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Transaction
(
    roll int,
    hostel_registration_id int,
    payment_id VARCHAR(1000) ,
    signature VARCHAR(1000),
    amount int,
    currency VARCHAR(255),
    description VARCHAR(2000),
    foreign key (roll) references Student (roll) on delete set null,
    FOREIGN KEY (hostel_registration_id) REFERENCES Hostel_registration (hostel_registration_id) ON DELETE SET NULL
);



