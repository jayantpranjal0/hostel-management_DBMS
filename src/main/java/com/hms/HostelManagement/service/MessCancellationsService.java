package com.hms.HostelManagement.service;

import com.hms.HostelManagement.model.AllMessCancellations;
import com.hms.HostelManagement.model.HostelRegistration;
import com.hms.HostelManagement.model.MessCancellations;
import com.hms.HostelManagement.model.User;

import java.util.Date;
import java.util.List;

public interface MessCancellationsService {
    List<AllMessCancellations> getAll();

    void createMessCancellation(MessCancellations messCancellations, HostelRegistration hostelRegistration, User user);
    void updateMessCancellation(MessCancellations messCancellations);

    MessCancellations getById(Integer id);

    void deleteMessCancellations(Integer id);

    List<MessCancellations> filterbyId(Integer hostelRegistrationid);

    List<MessCancellations> filterById(Integer hostelRegistrationid);

    List<AllMessCancellations> filterBySession(int year);

    List<MessCancellations> filterBySessionAndHostel(Integer hostelRegistrationid, int year);
    List<AllMessCancellations> filterByRollNo(Integer rollNo);
    List<AllMessCancellations> filterByDate(Date start, Date end);
    List<AllMessCancellations> filterByDateAndRoll(Date start, Date end,Integer Roll);
    List<AllMessCancellations> filterByRollNoAndSession(Integer rollNo, Integer year);
    List<AllMessCancellations> findByKeyword(String keyword);
    List<AllMessCancellations> findByKeywordAndRollNo(String keyword, Integer rollNo);
     List<MessCancellations> balanceByRollNoAndSession(Integer rollNo, Integer year);


    List<AllMessCancellations> filterByHostelIdAndSessionId(Integer hostelId, Integer sessionId);

    List<AllMessCancellations> filterByHostel(Integer hostelId);

    public Integer getEntryCount(int hrId,int roll,Date d);
}
