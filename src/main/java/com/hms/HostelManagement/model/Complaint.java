package com.hms.HostelManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Complaint {
    @Id
    private Integer complaintId;
    private Integer hostelRegistrationId;
    private Integer rollNo;
    private String description;
    private Timestamp timestamp;
    private String status;
}
