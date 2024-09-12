package com.hms.HostelManagement.controller;

import com.hms.HostelManagement.model.Employee;
import com.hms.HostelManagement.model.EmployeeUserMapping;
import com.hms.HostelManagement.model.User;
import com.hms.HostelManagement.service.AuthenticationService;
import com.hms.HostelManagement.service.EmployeeUserMappingService;
import com.hms.HostelManagement.service.StudentService;
import com.hms.HostelManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

abstract class BaseController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EmployeeUserMappingService employeeUserMappingService;


    public Boolean isAuthenticated(HttpSession session) {
        return authenticationService.isAuthenticated(session);
    }

    public void addDefaultAttributes(Model model, HttpSession session) {
        String currentUser = authenticationService.getCurrentUser(session);
        model.addAttribute("username", currentUser);
        if (currentUser != null) {

            model.addAttribute("userImageUrl", "https://ui-avatars.com/api/?name=" + currentUser);

            User user= userService.getUser(currentUser);
            model.addAttribute("user", user);
//
            String currRole=user.getRole();

            if (currRole.equals("employee")) {
                EmployeeUserMapping emu=employeeUserMappingService.getEmployeeUserMappingFromUsername(user.getUsername());
                model.addAttribute("emu",emu);
            }
        }
    }



    public String getRoleInSession(HttpSession session)
    {
        String currentUser = authenticationService.getCurrentUser(session);
        if(currentUser!=null)
        {
            User user=userService.getUser(currentUser);
            return user.getRole();
        }
        else
            return null;
    }
    public User getUserInSession(HttpSession session)
    {
        String currentUser = authenticationService.getCurrentUser(session);
        if(currentUser!=null)
        {
            User user=userService.getUser(currentUser);
            return user;
        }
        else
            return null;
    }
}
