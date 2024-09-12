package com.hms.HostelManagement.repository;


import com.hms.HostelManagement.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
    @Autowired
    private JdbcTemplate template;


    public void createEmployee(Employee e){
        String sql = "insert into employee(employee_id,employee_name,employee_address,employee_details) values (?, ?, ?,?)";
        template.update(sql,e.getEmployeeId(),e.getEmployeeName(),e.getEmployeeAddress(),e.getEmployeeDetails());
    }

    public Employee getEmployeeFromId(Integer id) {
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        try {
            return template.queryForObject(sql, new Object[]{id},new BeanPropertyRowMapper<>(Employee.class));
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public void updateEmployeeFromId(Employee e,int id){
        String sql="UPDATE Employee SET employee_name=?,employee_address=?,employee_details=? where employee_id=?";
        template.update(sql,e.getEmployeeName(),e.getEmployeeAddress(),e.getEmployeeDetails(),id);
    }

}
