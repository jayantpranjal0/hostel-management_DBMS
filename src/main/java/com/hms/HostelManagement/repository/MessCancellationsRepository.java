package com.hms.HostelManagement.repository;

import com.hms.HostelManagement.model.AllMessCancellations;
import com.hms.HostelManagement.model.HostelRegistration;
import com.hms.HostelManagement.model.MessCancellations;
import com.hms.HostelManagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class MessCancellationsRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    RowMapper<MessCancellations> rowMapper = (rs, rowNum) -> {
        MessCancellations messCancellations = new MessCancellations();
        messCancellations.setEntryNo(rs.getInt("entryNo"));
        messCancellations.setRollNo(rs.getInt("rollNo"));
        messCancellations.setHostelRegistrationId(rs.getInt("hostelRegistrationId"));
        messCancellations.setDate(rs.getDate("date_"));
        return messCancellations;
    };

    RowMapper<AllMessCancellations> rowMapper1 = (rs, rowNum) -> {
        AllMessCancellations allMessCancellations = new AllMessCancellations();
        allMessCancellations.setEntryNo(rs.getInt("entryNo"));
        allMessCancellations.setHostelId(rs.getInt("hostel_id"));
        allMessCancellations.setHostelName(rs.getString("hostel_name"));
        allMessCancellations.setStudentRollNo(rs.getInt("rollNo"));
        allMessCancellations.setStudentName(rs.getString("name"));
        allMessCancellations.setDate(rs.getDate("date_"));
        allMessCancellations.setSessionId(rs.getInt("session_id"));
        allMessCancellations.setSessionStartDate(rs.getDate("start_date"));
        return allMessCancellations;
    };

    public void createMessCancellation(MessCancellations messCancellations, HostelRegistration hostelRegistration, User user) {
        Date date = messCancellations.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        messCancellations.setDate(sqlDate);
        System.out.println(hostelRegistration.getHostelRegistrationId());
        System.out.println(messCancellations.getHostelRegistrationId());
        String sql2 = "insert into MessCancellations(hostelRegistrationId, rollno, date_) values (?,?,?)";
        jdbcTemplate.update(sql2, hostelRegistration.getHostelRegistrationId(), messCancellations.getRollNo(), messCancellations.getDate());
    }
    public List<AllMessCancellations> getAll() {
        String sql = "select m.entryNo, H.hostel_id, H.hostel_name, m.rollNo, S.name, m.date_, s2.session_id , s2.start_date\n" +
                "from MessCancellations m\n" +
                "inner join Student S on m.rollNo = S.roll\n" +
                "inner join Hostel_registration Hr on m.hostelRegistrationId = Hr.hostel_registration_id\n" +
                "inner join Hostel H on Hr.hostel_id = H.hostel_id\n" +
                "inner join Session S2 on Hr.session_id = S2.session_id";
        return jdbcTemplate.query(sql, rowMapper1);
    }
    public MessCancellations getById(Integer id) {
        String sql = "select * from MessCancellations where entryNo = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    }

    public List<AllMessCancellations> findByKeyword(String keyword) {
        String sql = "select m.entryNo, H.hostel_id, H.hostel_name, m.rollNo, S.name, m.date_, s2.session_id , s2.start_date\n" +
                "from MessCancellations m\n" +
                "inner join Student S on m.rollNo = S.roll\n" +
                "inner join Hostel_registration Hr on m.hostelRegistrationId = Hr.hostel_registration_id\n" +
                "inner join Hostel H on Hr.hostel_id = H.hostel_id\n" +
                "inner join Session S2 on Hr.session_id = S2.session_id\n" +
                "where H.hostel_name like ? or convert(H.hostel_id, char) like ? or convert(rollNo, char) like ?\n" +
                "or name like ? or date_format(date_, '%d/%m/%Y') like ?";
        Object[] ar = new Object[5];
        Arrays.fill(ar, "%" + keyword + "%");
        return jdbcTemplate.query(sql, ar, rowMapper1);
    }
    public List<AllMessCancellations> findByRollNoAndKeyword(String keyword, Integer rollNo) {
        String sql = "select m.entryNo, H.hostel_id, H.hostel_name, m.rollNo, S.name, m.date_, s2.session_id , s2.start_date\n" +
                "from MessCancellations m\n" +
                "inner join Student S on m.rollNo = S.roll\n" +
                "inner join Hostel_registration Hr on m.hostelRegistrationId = Hr.hostel_registration_id\n" +
                "inner join Hostel H on Hr.hostel_id = H.hostel_id\n" +
                "inner join Session S2 on Hr.session_id = S2.session_id\n" +
                "where (H.hostel_name like ? or convert(H.hostel_id, char) like ? or convert(rollNo, char) like ?\n" +
                "or name like ? or date_format(date_, '%d/%m/%Y') like ?) and m.rollNo = ?";
        String key = "%" + keyword + "%";
        return jdbcTemplate.query(sql, new Object[]{key, key, key, key, key, rollNo}, rowMapper1);
    }
    public void updateMessCancellations(MessCancellations messCancellations) {
        String sql = "update MessCancellations set date_ = ? where entryNo = ?";
        jdbcTemplate.update(sql, messCancellations.getDate(), messCancellations.getEntryNo());
    }

    public void deleteMessCancellations(Integer entryNo) {
        String sql = "delete from MessCancellations where entryNo = ?";
        jdbcTemplate.update(sql, entryNo);
    }

    public List<MessCancellations> filterById(Integer hostelRegistrationid) {
        String sql = "select * from MessCancellations where hostelRegistrationId = ?";
        return jdbcTemplate.query(sql, new Object[]{hostelRegistrationid}, rowMapper);
    }

    public List<AllMessCancellations> filterBySession(int year) {
        String sql = "select m.entryNo, H.hostel_id, H.hostel_name, m.rollNo, S.name, m.date_, s2.session_id , s2.start_date\n" +
                "from MessCancellations m\n" +
                "inner join Student S on m.rollNo = S.roll\n" +
                "inner join Hostel_registration Hr on m.hostelRegistrationId = Hr.hostel_registration_id\n" +
                "inner join Hostel H on Hr.hostel_id = H.hostel_id\n" +
                "inner join Session S2 on Hr.session_id = S2.session_id\n" +
                "where year(s2.start_date) = ?";
//        System.out.println(year);
        return jdbcTemplate.query(sql, new Object[]{year}, rowMapper1);
    }

    public List<MessCancellations> filterBySessionAndhostel(Integer hostelRegistrationid, int year) {
        String sql = "select * from MessCancellations where hostelRegistrationid=? and YEAR(date_)= ?";
        return jdbcTemplate.query(sql, new Object[]{hostelRegistrationid, year}, rowMapper);
    }

    public List<AllMessCancellations> filterByRollNo(Integer rollNo) {
        String sql = "select m.entryNo, H.hostel_id, H.hostel_name, m.rollNo, S.name, m.date_, s2.session_id , s2.start_date\n" +
                "from MessCancellations m\n" +
                "inner join Student S on m.rollNo = S.roll\n" +
                "inner join Hostel_registration Hr on m.hostelRegistrationId = Hr.hostel_registration_id\n" +
                "inner join Hostel H on Hr.hostel_id = H.hostel_id\n" +
                "inner join Session S2 on Hr.session_id = S2.session_id\n" +
                "where m.rollNo = ?";
        return jdbcTemplate.query(sql, new Object[]{rollNo}, rowMapper1);
    }

    public List<AllMessCancellations> filterByDate(Date start, Date end) {
        String sql = "select m.entryNo, H.hostel_id, H.hostel_name, m.rollNo, S.name, m.date_, s2.session_id , s2.start_date\n" +
                "from MessCancellations m\n" +
                "inner join Student S on m.rollNo = S.roll\n" +
                "inner join Hostel_registration Hr on m.hostelRegistrationId = Hr.hostel_registration_id\n" +
                "inner join Hostel H on Hr.hostel_id = H.hostel_id\n" +
                "inner join Session S2 on Hr.session_id = S2.session_id " +
                "where m.date_ >= ? and m.date_ <= ?";
        return jdbcTemplate.query(sql, new Object[]{start, end}, rowMapper1);
    }
    public List<AllMessCancellations> filterByDateAndRoll(Date start, Date end,Integer Roll) {
        String sql = "select m.entryNo, H.hostel_id, H.hostel_name, m.rollNo, S.name, m.date_, s2.session_id , s2.start_date\n" +
                "from MessCancellations m\n" +
                "inner join Student S on m.rollNo = S.roll\n" +
                "inner join Hostel_registration Hr on m.hostelRegistrationId = Hr.hostel_registration_id\n" +
                "inner join Hostel H on Hr.hostel_id = H.hostel_id\n" +
                "inner join Session S2 on Hr.session_id = S2.session_id " +
                "where m.date_ >= ? and m.date_ <= ? and m.rollNo=?";
        return jdbcTemplate.query(sql, new Object[]{start, end,Roll}, rowMapper1);
    }

    public List<AllMessCancellations> filterByRollNoAndSession(Integer rollNo, Integer year) {
        String sql = "select m.entryNo, H.hostel_id, H.hostel_name, m.rollNo, S.name, m.date_, s2.session_id , s2.start_date\n" +
                "from MessCancellations m\n" +
                "inner join Student S on m.rollNo = S.roll\n" +
                "inner join Hostel_registration Hr on m.hostelRegistrationId = Hr.hostel_registration_id\n" +
                "inner join Hostel H on Hr.hostel_id = H.hostel_id\n" +
                "inner join Session S2 on Hr.session_id = S2.session_id\n" +
                "where m.rollNo = ? and year(s2.start_date) = ?";
        return jdbcTemplate.query(sql, new Object[]{rollNo, year}, rowMapper1);
    }

    public List<MessCancellations> balanceByRollNoAndSession(Integer rollNo, Integer year) {
        String sql = "select * from MessCancellations where rollNo = ? and YEAR(date_) = ?";
        return jdbcTemplate.query(sql, new Object[]{rollNo, year}, rowMapper);
    }

    public List<AllMessCancellations> filterByHostelIdAndSessionId(Integer hostelId, Integer sessionId) {
        String sql = "select m.entryNo, H.hostel_id, H.hostel_name, m.rollNo, S.name, m.date_, s2.session_id , s2.start_date\n" +
                "from MessCancellations m\n" +
                "inner join Student S on m.rollNo = S.roll\n" +
                "inner join Hostel_registration Hr on m.hostelRegistrationId = Hr.hostel_registration_id\n" +
                "inner join Hostel H on Hr.hostel_id = H.hostel_id\n" +
                "inner join Session S2 on Hr.session_id = S2.session_id\n" +
                "where H.hostel_id = ? and S2.session_id = ?";
        return jdbcTemplate.query(sql, new Object[]{hostelId, sessionId}, rowMapper1);
    }

    public List<AllMessCancellations> filterByHostel(Integer hostelId) {
        String sql = "select m.entryNo, H.hostel_id, H.hostel_name, m.rollNo, S.name, m.date_, s2.session_id , s2.start_date\n" +
                "from MessCancellations m\n" +
                "inner join Student S on m.rollNo = S.roll\n" +
                "inner join Hostel_registration Hr on m.hostelRegistrationId = Hr.hostel_registration_id\n" +
                "inner join Hostel H on Hr.hostel_id = H.hostel_id\n" +
                "inner join Session S2 on Hr.session_id = S2.session_id\n" +
                "where H.hostel_id = ?";
        return jdbcTemplate.query(sql, new Object[]{hostelId}, rowMapper1);
    }

    public Integer getEntryCount(int hrId,int roll,Date d){
        String sql="select count(*) from messcancellations where hostelRegistrationId = ? and rollNo = ? and date_<=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{hrId,roll,d},Integer.class);
    }

}
