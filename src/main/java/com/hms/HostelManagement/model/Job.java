package com.hms.HostelManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Job {
    @Id
    private String jobType;
    private int jobSalary;
    private String jobDetails;
}
