package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.Session;
import com.hms.HostelManagement.repository.SessionRepository;
import com.hms.HostelManagement.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    SessionRepository sessionRepository;

    @Override
    public List<Session> getAll() {
        return sessionRepository.getAll();
    }

    @Override
    public List<Session> getAllSession() {
        return sessionRepository.getAllSession();
    }

    @Override
    public Session getSessionFromId(int id){
        return sessionRepository.getSessionFromId(id);
    }
   @Override
   public void createSession(Session session){
         sessionRepository.createSession(session);
    }
    @Override
    public Date getStartDateFromSession(int sessionId){
        return sessionRepository.getSessionFromId(sessionId).getStartDate();
    }
}
