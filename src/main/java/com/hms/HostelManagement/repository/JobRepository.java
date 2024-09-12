package com.hms.HostelManagement.repository;


import com.hms.HostelManagement.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobRepository {
    @Autowired
    JdbcTemplate template;


    public void createJob(Job j){
        String sql="insert into job(job_type,job_salary,job_details) values(?,?,?)";
        template.update(sql,j.getJobType(),j.getJobSalary(),j.getJobDetails());
    }

     public List<Job> getAllJobs(){
        String sql = "select * from job";
        return template.query(sql, new BeanPropertyRowMapper<>(Job.class));
    }

    public Job getJobFromType(String id){
        String sql="select * from job where job_type=?";
        return template.queryForObject(sql,new Object[]{id},new BeanPropertyRowMapper<>(Job.class));
    }

    public void updateJobFromType( Job j,String type){
        String sql = "UPDATE job SET job_salary =?,job_details=? WHERE job_type = ?";
        template.update(
                sql,j.getJobSalary(),j.getJobDetails(),type);
    }}
