package com.hms.HostelManagement.model;

import lombok.Data;

import java.util.Date;

@Data
public class AllMessCancellations {
    private Integer entryNo;
    private Integer hostelId;
    private String hostelName;
    private Integer studentRollNo;
    private String studentName;
    private Date date;
    private Integer sessionId;
    private Date sessionStartDate;
}
