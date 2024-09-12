package com.hms.HostelManagement.model;
import lombok.Data;

import java.util.Date;
@Data
public class Transaction {

    private int roll;
    private int hostelRegistrationId;
    private String paymentId;
    private String signature;
    private int amount;
    private String currency;
    private String description;
}