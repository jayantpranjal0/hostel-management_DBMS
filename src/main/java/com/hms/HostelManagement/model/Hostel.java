package com.hms.HostelManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
public class Hostel {
    @Id
    private Integer hostelId;
    private String hostelName, hostelAddress;
}
