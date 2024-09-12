package com.hms.HostelManagement.model;

import lombok.Data;

import java.util.Date;


@Data
public class MessCancellations {
    private Integer entryNo;
    private Integer hostelRegistrationId;
    private Integer rollNo;
    private Date date;
}
