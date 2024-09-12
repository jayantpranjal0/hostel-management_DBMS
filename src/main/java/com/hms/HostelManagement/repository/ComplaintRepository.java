package com.hms.HostelManagement.repository;

import com.hms.HostelManagement.model.Complaint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

@Repository
public class ComplaintRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public void createComplaint(Complaint complaint)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());

        String sql = "INSERT INTO COMPLAINT(rollNo, hostel_registration_id,description, timestamp, status) values (?,?,?,? , 'Pending')";
        jdbcTemplate.update(sql, complaint.getRollNo(), complaint.getHostelRegistrationId(),complaint.getDescription(),ts);
    }

    public List<Complaint> getAllComplaint()
    {
        String sql = "SELECT * FROM COMPLAINT";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Complaint.class));
    }

    public List<Complaint> getAllComplaintByRollNo(int rollNo) {
        String sql = "SELECT * FROM COMPLAINT WHERE rollNo LIKE ?";
        String rollNoPattern = rollNo + "%";
        return jdbcTemplate.query(sql, new Object[]{rollNoPattern}, new BeanPropertyRowMapper<>(Complaint.class));
    }
    public List<Complaint> getStudentComplaint(int roll,int hrId)
    {
        String sql = "SELECT * FROM COMPLAINT WHERE rollNo = ? and hostel_registration_id=?";
        return jdbcTemplate.query(sql,new Object[] {roll,hrId},new BeanPropertyRowMapper<>(Complaint.class));
    }

//    public void deleteComplaint(int complaintID){
//        String sql = "delete from Complaint where complaint_id=?";
//        jdbcTemplate.update(sql,complaintID);
//    }

    public void updateComplaintStatus(int complaintId) {
        String sql = "UPDATE Complaint SET status = ? WHERE complaint_id = ?";
        jdbcTemplate.update(sql, "Completed", complaintId);
    }
    public void updateComplaintStatusDel(int complaintId) {
        String sql = "DELETE FROM Complaint WHERE complaint_id = ?";
        jdbcTemplate.update(sql, complaintId);
    }
    public void updateComplaintStatusAll() {
        String sql = "UPDATE Complaint SET status = ?";
        jdbcTemplate.update(sql, "Completed");
    }
    public List<Complaint> getAllComplaintByHostelRegId(int hrId) {
        String sql = "SELECT * FROM COMPLAINT WHERE hostel_registration_id= ?";
        return jdbcTemplate.query(sql, new Object[]{hrId}, new BeanPropertyRowMapper<>(Complaint.class));
    }

}
