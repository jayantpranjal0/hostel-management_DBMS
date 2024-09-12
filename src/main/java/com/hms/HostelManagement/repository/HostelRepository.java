package com.hms.HostelManagement.repository;

import com.hms.HostelManagement.model.Hostel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HostelRepository {
    @Autowired
    JdbcTemplate template;
//    RowMapper = (rs, rownum){
//
//    };
    public void createHostel(Hostel hostel){
        String sql = "insert into Hostel(hostel_name, hostel_address) values (?, ?)";
        template.update(sql, hostel.getHostelName(), hostel.getHostelAddress());
    }
    public List<Hostel> getAllHostels(){
        String sql = "select * from Hostel";
        return template.query(sql, new BeanPropertyRowMapper<>(Hostel.class));
    }
    public void deleteHostel(int HostelId){
            String sql = "delete from Hostel where HostelId=?";
            template.update(sql,HostelId);
    }
    public void updateHostel( Hostel hostel){
        String sql = "UPDATE Hostel SET HostelName=?,HostelAddress =? WHERE HostelId = ?";
        template.update(
                sql,hostel.getHostelName(),hostel.getHostelAddress(),hostel.getHostelId());
    }

    public Hostel getHostelFromName(String hostelName){
        String sql="select * from Hostel where HostelName=?";
        return template.queryForObject(sql,new Object[]{hostelName},new BeanPropertyRowMapper<>(Hostel.class));
    }

    public Hostel getHostelFromId(int id){
        String sql="select * from Hostel where hostel_id=?";
        return template.queryForObject(sql,new Object[]{id},new BeanPropertyRowMapper<>(Hostel.class));
    }

}







