package com.hms.HostelManagement.service;

import com.hms.HostelManagement.model.User;
import com.hms.HostelManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {

    public void createUser(User u);

    public User getUser(String username);

    public void changePassword(String username, User user);

    public boolean checkUserNameExists(String username);


}



