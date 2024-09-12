package com.hms.HostelManagement.model;


import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class HostelRegistration {
    @Id
    private Integer hostelRegistrationId;
    private Integer hostelId;
    private Integer sessionId;
    private Integer roomCount;
}
