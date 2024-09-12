package com.hms.HostelManagement.service.Impl;


import com.hms.HostelManagement.model.Student;
import com.hms.HostelManagement.repository.StudentRepository;
import com.hms.HostelManagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    //
    public StudentServiceImpl(StudentRepository studentRepository) {
        super();
        this.studentRepository = studentRepository;
    }

    //    @Override
//    public Student getStudentByRollNo(String rollNo) {
//        Student student = studentRepository.getByRollNo(rollNo);
//        return student;
//    }
    @Override
    public List<Student> getAll() {
        return studentRepository.getAll();
    }
//
//    @Override
//    public void deleteStudent(Student student) {
//        studentRepository.delete(student.getRollNo());
//    }
//
//    @Override
//    public void updateStudent(Student student) {
//        studentRepository.update(student);
//    }
    @Override
    public boolean checkRollNoExists(Integer roll) {
        Student s=studentRepository.getStudentFromRoll(roll);
        if(s==null)
            return false;
        else
            return true;
    }

    @Override
    public void createStudent(Student s) {
        studentRepository.createStudent(s);
    }

    @Override
    public Student getStudentFromRoll(Integer roll) {
        return studentRepository.getStudentFromRoll(roll);
    }

    @Override
    public void updateStudentBalanceByRoll(int val, int roll) {
        studentRepository.updateStudentBalanceFromRoll(val,roll);
    }

    @Override
    public void updateStudentByRoll(Student s, Integer roll) {
        studentRepository.updateStudentFromRoll(s,roll);
    }
}
