package com.hms.HostelManagement.repository;

import com.hms.HostelManagement.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public class SessionRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<Session> getAll() {
        String sql = "select * from Session";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Session.class));
    }
     public List<Session> getAllSession() {
        String sql = "select * from Session";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Session.class));
    }
    public void createSession(Session session){
        String sql = "insert into Session(session_name, start_date) values (?, ?)";
        jdbcTemplate.update(sql, session.getSessionName(), session.getStartDate());
    }
    public Session getSessionFromId(int id){
        String sql="select * from session where session_id=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},new BeanPropertyRowMapper<>(Session.class));
    };



}
