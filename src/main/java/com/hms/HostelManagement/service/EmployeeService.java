package com.hms.HostelManagement.service;


import com.hms.HostelManagement.model.Employee;

public interface EmployeeService {

    boolean checkIdExists(int id);

    public void createEmployee(Employee e);

    public Employee getEmployeeFromId(int id);

    public void updateEmployeeFromId(Employee e,int id);


}
