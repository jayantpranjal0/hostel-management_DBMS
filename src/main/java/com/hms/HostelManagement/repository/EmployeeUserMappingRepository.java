package com.hms.HostelManagement.repository;

import com.hms.HostelManagement.model.Employee;
import com.hms.HostelManagement.model.EmployeeUserMapping;
import com.hms.HostelManagement.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeUserMappingRepository {

    @Autowired
    JdbcTemplate template;


    public void createEmployeeUserMappingSerivce(EmployeeUserMapping e){
        String sql = "insert into EmployeeUserMapping(username,employee_id,job_type,hostel_registration_id) values (?, ?, ?,?)";
        template.update(sql,e.getUsername(),e.getEmployeeId(),e.getJobType(),e.getHostelRegistrationId());
    }

     public List<EmployeeUserMapping> getAllEmployeeUserMapping()
    {
        String sql = "Select * from EmployeeUserMapping";
        return template.query(sql,new BeanPropertyRowMapper<>(EmployeeUserMapping.class));
    }

     public EmployeeUserMapping getEmployeeUserMappingFromUsername(String username)
    {
        String sql = "Select * from EmployeeUserMapping where username=?";
        return template.queryForObject(sql,new Object[]{username}, new BeanPropertyRowMapper<>(EmployeeUserMapping.class));
    }

    public Employee getEmployeeFromUsername(String username){
        int id=getEmployeeUserMappingFromUsername(username).getEmployeeId();
        String sql = "SELECT * FROM Employee WHERE employee_id = ?";
        return template.queryForObject(sql, new Object[]{id},new BeanPropertyRowMapper<>(Employee.class));
    }



    public Job getJobFromEmployeeUsername(String username){

        String jobType=getEmployeeUserMappingFromUsername(username).getJobType();
        String sql="select * from job where job_type=?";
        return template.queryForObject(sql,new Object[]{jobType},new BeanPropertyRowMapper<>(Job.class));
    }
}
