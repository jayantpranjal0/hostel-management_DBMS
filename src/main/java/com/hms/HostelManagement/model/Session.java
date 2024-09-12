package com.hms.HostelManagement.model;

import lombok.Data;

import java.util.Date;
@Data
public class Session {
    private Integer sessionId;
    private String sessionName;
    private Date startDate;
}
