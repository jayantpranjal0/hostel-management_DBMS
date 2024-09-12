package com.hms.HostelManagement.repository;

import com.hms.HostelManagement.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeRepository {
    @Autowired
    private JdbcTemplate template;


    public void createNotice(Notice notice){
        String sql = "insert into Notice(hostel_registration_id,description,date) values (?, ?,?)";
        template.update(sql,notice.getHostelRegistrationId(),notice.getDescription(),notice.getDate() );
    }

    public List<Notice>getAllNotices()
    {
        String sql = "select * from notice";
        return template.query(sql, new BeanPropertyRowMapper<>(Notice.class));
    }
    public List<Notice>getAllNoticesFromHostelReg(int id)
    {
        String sql = "select * from notice where hostel_registration_id=?";
        return template.query(sql,new Object[]{id},new BeanPropertyRowMapper<>(Notice.class));
    }

    public Notice getNoticeFromId(int id){
        String sql= "select * from notice where notice_id=?";
        return template.queryForObject(sql,new Object[]{id},new BeanPropertyRowMapper<>(Notice.class));
    }
    public void updateNotice(Notice i,int id){
        String sql="UPDATE notice SET description=?,date=? where notice_id=?";
        template.update(sql,i.getDescription(),i.getDate(),id);
    }
}
