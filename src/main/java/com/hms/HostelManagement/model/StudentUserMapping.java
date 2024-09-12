package com.hms.HostelManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class StudentUserMapping {
    @Id
    private String username;
    private int roll;
    private int hostelRegistrationId;
}
