package com.hms.HostelManagement.repository;

import com.hms.HostelManagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate template;

    public void createUser(User u) {
        String sql = "INSERT INTO user (username, password, Role) VALUES (?, ?, ?)";
        template.update(sql, u.getUsername(), u.getPassword(),u.getRole());
    }


    public User getUser(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try {
            return template.queryForObject(sql, new Object[]{username},new BeanPropertyRowMapper<>(User.class));
        }
        catch (EmptyResultDataAccessException e) {
        return null;
        }
    }

    public void update(User user) {
        String sql = "UPDATE user SET password = ? WHERE username = ?";
        template.update(sql, user.getPassword(), user.getUsername(),user.getRole());
    }



}
