package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.Employee;
import com.hms.HostelManagement.repository.EmployeeRepository;
import com.hms.HostelManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        super();
        this.employeeRepository = employeeRepository;
    }

    @Override
    public boolean checkIdExists(int id) {
        Employee e=employeeRepository.getEmployeeFromId(id);
        if(e==null)
            return false;
        else
            return true;
    }

    @Override
    public void createEmployee(Employee e) {
        employeeRepository.createEmployee(e);
    }

    @Override
    public Employee getEmployeeFromId(int id) {
        return employeeRepository.getEmployeeFromId(id);
    }

    @Override
    public void updateEmployeeFromId(Employee e, int id) {
        employeeRepository.updateEmployeeFromId(e,id);
    }

}
