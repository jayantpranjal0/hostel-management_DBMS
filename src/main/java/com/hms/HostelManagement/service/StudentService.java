package com.hms.HostelManagement.service;

import com.hms.HostelManagement.model.Student;

import java.util.List;

public interface StudentService {
//    public Student getStudentByRollNo(String rollNo);
//
//    public void deleteStudent(Student student);
//
//    public void updateStudent(Student student) ;
    List<Student> getAll();
    public boolean checkRollNoExists(Integer roll);

    public void createStudent(Student s);

    public Student getStudentFromRoll(Integer roll);

    public void updateStudentByRoll(Student s,Integer roll);
    public void updateStudentBalanceByRoll(int val,int roll);

}
