package com.hms.HostelManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Visitor
{
        @Id
        private Integer visitor_id;
        private Integer hostel_registration;
        private String visitor_name;
        private String visitor_purpose;
        private String entry_time;
        private String exit_time;
}
