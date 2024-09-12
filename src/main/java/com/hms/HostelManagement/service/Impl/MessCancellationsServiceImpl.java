package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.HostelRegistration;
import com.hms.HostelManagement.model.AllMessCancellations;
import com.hms.HostelManagement.model.MessCancellations;
import com.hms.HostelManagement.model.User;
import com.hms.HostelManagement.repository.MessCancellationsRepository;
import com.hms.HostelManagement.service.MessCancellationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessCancellationsServiceImpl implements MessCancellationsService {
    @Autowired
    MessCancellationsRepository messCancellationsRepository;


    public MessCancellationsServiceImpl(MessCancellationsRepository messCancellationsRepository) {
        super();
        this.messCancellationsRepository = messCancellationsRepository;
    }

    @Override
    public List<AllMessCancellations> getAll() {
        return messCancellationsRepository.getAll();
    }

    @Override
    public void createMessCancellation(MessCancellations messCancellations, HostelRegistration hostelRegistration, User user){
        messCancellationsRepository.createMessCancellation(messCancellations,hostelRegistration,user);
    }
    @Override
    public void updateMessCancellation(MessCancellations messCancellations) {
        messCancellationsRepository.updateMessCancellations(messCancellations);
    }
    @Override
    public MessCancellations getById(Integer id) {
        return messCancellationsRepository.getById(id);
    }

    @Override
    public void deleteMessCancellations(Integer id) {
        messCancellationsRepository.deleteMessCancellations(id);
    }

    @Override
    public List<MessCancellations> filterbyId(Integer hostelRegistrationid) {
        return null;
    }


    @Override
    public List<MessCancellations> filterById(Integer hostelRegistrationid) {
        return messCancellationsRepository.filterById(hostelRegistrationid);
    }

    @Override
    public List<AllMessCancellations> filterBySession(int year) {
        return messCancellationsRepository.filterBySession(year);
    }

    @Override
    public List<MessCancellations> filterBySessionAndHostel(Integer hostelRegistrationid, int year) {
        return messCancellationsRepository.filterBySessionAndhostel(hostelRegistrationid, year);
    }

    @Override
    public List<AllMessCancellations> filterByRollNo(Integer rollNo) {
        return messCancellationsRepository.filterByRollNo(rollNo);
    }

    @Override
    public List<AllMessCancellations> filterByDate(Date start, Date end) {
        return messCancellationsRepository.filterByDate(start, end);
    }
    @Override
    public List<AllMessCancellations> filterByDateAndRoll(Date start, Date end,Integer roll) {
        return messCancellationsRepository.filterByDateAndRoll(start, end,roll);
    }

    @Override
    public List<AllMessCancellations> filterByRollNoAndSession(Integer rollNo, Integer year) {
        return messCancellationsRepository.filterByRollNoAndSession(rollNo, year);
    }

    @Override
    public List<AllMessCancellations> findByKeyword(String keyword) {
        return messCancellationsRepository.findByKeyword(keyword);
    }

    @Override
    public List<AllMessCancellations> findByKeywordAndRollNo(String keyword, Integer rollNo) {
        return messCancellationsRepository.findByRollNoAndKeyword(keyword, rollNo);
    }

    @Override
    public List<MessCancellations> balanceByRollNoAndSession(Integer rollNo,Integer year){
         return messCancellationsRepository.balanceByRollNoAndSession(rollNo,year);
    }

    @Override
    public List<AllMessCancellations> filterByHostelIdAndSessionId(Integer hostelId, Integer sessionId) {
        return messCancellationsRepository.filterByHostelIdAndSessionId(hostelId, sessionId);
    }

    @Override
    public List<AllMessCancellations> filterByHostel(Integer hostelId) {
        return messCancellationsRepository.filterByHostel(hostelId);
    }

    @Override
    public Integer getEntryCount(int hrId, int roll,Date d) {
        return messCancellationsRepository.getEntryCount(hrId,roll,d);
    }

}