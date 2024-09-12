package com.hms.HostelManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class HostelFacilities {
    @Id
    private Integer facilityId;
    private Integer hostelRegistrationId;
    private String description;
    private Integer conditionStatus;
}
