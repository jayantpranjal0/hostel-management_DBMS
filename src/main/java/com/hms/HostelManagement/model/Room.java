package com.hms.HostelManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Room {
    @Id
    private Integer hostelRegistrationId;
    @Id
    private Integer roomId;

}
