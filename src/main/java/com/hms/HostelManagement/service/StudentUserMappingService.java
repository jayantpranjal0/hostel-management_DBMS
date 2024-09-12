package com.hms.HostelManagement.service;

import com.hms.HostelManagement.model.Student;
import com.hms.HostelManagement.model.StudentUserMapping;

import java.util.List;

public interface StudentUserMappingService {

    public void createStudentUserMapping(StudentUserMapping s);
    public int getHostelRegIdFromUsername(String username);
    public List<StudentUserMapping> getAllStudentUserMapping();
//    public StudentUserMapping getRollNoFromUsername(String username);
    public StudentUserMapping getRollNoFromUsername(String username);
    public int getHostelRegistrationIdFromUsername(String username);

    public Student getStudentFromUsername(String Username);


}
