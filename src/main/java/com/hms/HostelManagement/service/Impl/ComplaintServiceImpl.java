package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.Complaint;
import com.hms.HostelManagement.repository.ComplaintRepository;
import com.hms.HostelManagement.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    ComplaintRepository complaintRepository;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository){
        super();
        this.complaintRepository = complaintRepository;
    }


    @Override
    public List<Complaint> getAllComplaint() {
        return complaintRepository.getAllComplaint();
    }

    @Override
    public List<Complaint> getStudentComplaint(int roll,int hrId) {
        return complaintRepository.getStudentComplaint(roll,hrId);
    }

    @Override
    public List<Complaint> getEmployeeComplaint(int hrId) {
        return complaintRepository.getAllComplaintByHostelRegId(hrId);
    }

    @Override
    public void createComplaint(Complaint complaint)
    {
        complaintRepository.createComplaint(complaint);
    }

    @Override
    public void updateComplaintStatus(int complaintID)
    {
        complaintRepository.updateComplaintStatus(complaintID);
    }
    @Override
    public void updateComplaintStatusDel(int complaintID)
    {
        complaintRepository.updateComplaintStatusDel(complaintID);
    }
    @Override
    public void updateComplaintStatusAll()
    {
        complaintRepository.updateComplaintStatusAll();
    }

    @Override
    public List<Complaint> getAllComplaintByRoll(int rollNo)
    {
        return complaintRepository.getAllComplaintByRollNo(rollNo);
    }
}
