package com.hms.HostelManagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class HelperHostelSessionName {
    private String hostelName;
    private String sessionName;
    private Integer uint;
}
