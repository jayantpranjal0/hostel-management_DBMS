package com.hms.HostelManagement.service;

import com.hms.HostelManagement.model.Complaint;

import java.util.List;

public interface ComplaintService {
    List<Complaint> getAllComplaint();
    List<Complaint> getStudentComplaint(int roll,int hrId);
    List<Complaint> getEmployeeComplaint(int hrId);
    public void createComplaint(Complaint complaint);

    public void updateComplaintStatus(int complaintID);
    public void updateComplaintStatusDel(int complaintID);
    public void updateComplaintStatusAll();

    public List<Complaint> getAllComplaintByRoll(int rollNo);


}
