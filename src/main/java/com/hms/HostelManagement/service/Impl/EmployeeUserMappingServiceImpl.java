package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.Employee;
import com.hms.HostelManagement.model.EmployeeUserMapping;
import com.hms.HostelManagement.model.Job;
import com.hms.HostelManagement.repository.EmployeeUserMappingRepository;
import com.hms.HostelManagement.service.EmployeeUserMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeUserMappingServiceImpl implements EmployeeUserMappingService {
    @Autowired
    EmployeeUserMappingRepository employeeUserMappingRepository;

    public EmployeeUserMappingServiceImpl(EmployeeUserMappingRepository employeeUserMappingRepository) {
        super();
        this.employeeUserMappingRepository = employeeUserMappingRepository;
    }

    @Override
    public void createEmployeeUserMapping(EmployeeUserMapping e) {
        employeeUserMappingRepository.createEmployeeUserMappingSerivce(e);
    }

    @Override
    public List<EmployeeUserMapping> getAllEmployeeUserMapping() {
        return employeeUserMappingRepository.getAllEmployeeUserMapping();
    }

    @Override
    public Employee getEmployeeFromUsername(String username) {
        return employeeUserMappingRepository.getEmployeeFromUsername(username);
    }

    @Override
    public EmployeeUserMapping getEmployeeUserMappingFromUsername(String username) {
        return employeeUserMappingRepository.getEmployeeUserMappingFromUsername(username);
    }

    @Override
    public Job getJobFromEmployeeUsername(String username) {
        return employeeUserMappingRepository.getJobFromEmployeeUsername(username);
    }
}
