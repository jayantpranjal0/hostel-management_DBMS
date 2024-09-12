package com.hms.HostelManagement.repository;

import com.hms.HostelManagement.model.MessCancellations;
import com.hms.HostelManagement.model.Student;
import com.hms.HostelManagement.model.StudentUserMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentUserMappingRepository {
    @Autowired
    JdbcTemplate template;
    RowMapper<StudentUserMapping> rowMapper = (rs, rowNum) -> {
        StudentUserMapping studentUserMapping = new StudentUserMapping();
        studentUserMapping.setRoll(rs.getInt("roll"));
        studentUserMapping.setUsername(rs.getString("username"));
        studentUserMapping.setHostelRegistrationId(rs.getInt("hostelRegistrationId"));
        return studentUserMapping;

    };
    public List<StudentUserMapping> getAllStudentUserMapping()
    {
        String sql = "Select * from StudentUserMapping";
        return template.query(sql,new BeanPropertyRowMapper<>(StudentUserMapping.class));
    }
    public StudentUserMapping getStudentUserMappingFromUsername(String username)
    {
        String sql = "Select * from StudentUserMapping where username=?";
        return template.queryForObject(sql,new Object[]{username}, new BeanPropertyRowMapper<>(StudentUserMapping.class));
    }


    public Student getStudentFromUsername(String username){
        int roll=getStudentUserMappingFromUsername(username).getRoll();
//        System.out.println(roll + 4);
        String sql = "SELECT * FROM student WHERE roll = ?";
        return template.queryForObject(sql, new Object[]{roll},new BeanPropertyRowMapper<>(Student.class));
    }

    public void createStudentUserMapping(StudentUserMapping s){
        String sql = "insert into StudentUserMapping(username,roll,hostelRegistrationId) values (?, ?, ?)";
        template.update(sql,s.getUsername(),s.getRoll(),s.getHostelRegistrationId());
    }

    public StudentUserMapping getRollNoFromUsername(String username) {
        String sql = "select * from studentusermapping where username = ?";
//        System.out.println(template.queryForObject(sql, new Object[]{username},new BeanPropertyRowMapper<>(Integer.class)));
        System.out.println("Log StudentUserMapping: "+template.queryForObject(sql, new Object[]{username},rowMapper));
        return template.queryForObject(sql, new Object[]{username},rowMapper);
    }

    // getRollNoFromUsername,getHostelRegistrationIdFromUsername

}
