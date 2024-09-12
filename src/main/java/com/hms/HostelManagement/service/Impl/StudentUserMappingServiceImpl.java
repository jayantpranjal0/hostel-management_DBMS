package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.Student;
import com.hms.HostelManagement.model.StudentUserMapping;
import com.hms.HostelManagement.repository.StudentUserMappingRepository;
import com.hms.HostelManagement.service.StudentUserMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentUserMappingServiceImpl implements StudentUserMappingService {

    @Autowired
    private StudentUserMappingRepository studentUserMappingRepository;

    @Override
    public void createStudentUserMapping(StudentUserMapping s) {
        studentUserMappingRepository.createStudentUserMapping(s);
    }

//    @Override
//    public Integer getRollNofromUsername(String username) {
//        return studentUserMappingRepository.getRollNoFromUsername(username);
//    }


    @Override
    public int getHostelRegIdFromUsername(String username) {
        return studentUserMappingRepository.getStudentUserMappingFromUsername(username).getHostelRegistrationId();
    }



    @Override
    public int getHostelRegistrationIdFromUsername(String username){
        return studentUserMappingRepository.getStudentUserMappingFromUsername(username).getHostelRegistrationId();
    }

    @Override
    public List<StudentUserMapping> getAllStudentUserMapping() {
        return studentUserMappingRepository.getAllStudentUserMapping();
    }

    @Override
    public StudentUserMapping getRollNoFromUsername(String username){
        return studentUserMappingRepository.getRollNoFromUsername(username);
    }

    @Override
    public Student getStudentFromUsername(String username) {
        return studentUserMappingRepository.getStudentFromUsername(username);
    }
//    @Override
//    public int getRollNoFromUsername(String username) {
//        return -1;
//    }

}
