package com.hms.HostelManagement.service;

import com.hms.HostelManagement.model.Session;

import java.util.Date;
import java.util.List;

public interface SessionService {
    List<Session> getAll();
    List<Session> getAllSession();
    public Session getSessionFromId(int id);
    public void createSession(Session session);
    public Date getStartDateFromSession(int sessionId);
}
