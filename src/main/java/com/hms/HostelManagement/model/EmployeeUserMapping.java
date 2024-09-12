package com.hms.HostelManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class EmployeeUserMapping {
    private String username;
    private Integer employeeId;
    private String jobType;
    private Integer hostelRegistrationId;
}
