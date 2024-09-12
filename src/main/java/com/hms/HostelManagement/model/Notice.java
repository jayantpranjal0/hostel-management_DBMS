package com.hms.HostelManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Notice {
    @Id
    private Integer noticeId;
    private Integer hostelRegistrationId;
    private String description;
    private Date date;
}
