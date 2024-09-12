package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.User;
import com.hms.HostelManagement.repository.UserRepository;
import com.hms.HostelManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }


    @Override
    public void createUser(User u) {
        userRepository.createUser(u);
    }

    @Override
    public User getUser(String username) {
        return userRepository.getUser(username);
    }

    @Override
    public void changePassword(String username, User user) {
        user.setUsername(username);
        userRepository.update(user);
    }

    @Override
    public boolean checkUserNameExists(String username) {

        User user=userRepository.getUser(username);
        if(user==null)
            return false;
        else
            return true;
    }
}
