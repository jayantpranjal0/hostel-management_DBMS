package com.hms.HostelManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.sql.Date;

@Data
public class Student{
    @Id
    private Integer roll ;
    private String Name;
    private String email;
    private String phone;
    private String branch;
    private Integer balance;
    private Date dob;
}
