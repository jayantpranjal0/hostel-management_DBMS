package com.hms.HostelManagement.service;

import com.hms.HostelManagement.model.Employee;
import com.hms.HostelManagement.model.EmployeeUserMapping;
import com.hms.HostelManagement.model.Job;

import java.util.List;

public interface EmployeeUserMappingService {
    public void createEmployeeUserMapping(EmployeeUserMapping e);

    public List<EmployeeUserMapping>getAllEmployeeUserMapping();

    public Employee getEmployeeFromUsername(String username);
    public EmployeeUserMapping getEmployeeUserMappingFromUsername(String username);

    public Job getJobFromEmployeeUsername(String username);

}
