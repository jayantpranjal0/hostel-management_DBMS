package com.hms.HostelManagement.controller;

import com.hms.HostelManagement.model.User;
import com.hms.HostelManagement.service.AuthenticationService;
import com.hms.HostelManagement.service.ToastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ToastService toastService;

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if (authenticationService.isAuthenticated(session)) {
            return "redirect:/dashboard";
        }

        model.addAttribute("credentials", new User());
        return "dashboard/login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute User credentials, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (authenticationService.isAuthenticated(session)) {
            return "redirect:/dashboard";
        }

        String username = credentials.getUsername();
        String password = credentials.getPassword();
        String role = credentials.getRole();
        String errorMessage = null;
//        System.out.println(role);
        try {
            if (authenticationService.checkCredentials(username, password,role)) {
                authenticationService.loginUser(session, username);

                toastService.redirectWithSuccessToast(redirectAttributes, "Successfully logged in.");
                return "redirect:/dashboard";
            }
            errorMessage = "Incorrect password.";
        } catch (Exception e) {
            errorMessage = "No user with this username found.";
        }

        model.addAttribute("credentials", credentials);
        toastService.displayErrorToast(model, errorMessage);
        return "dashboard/login";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session) {
        authenticationService.logoutUser(session);
        return "redirect:/login";
    }
}
