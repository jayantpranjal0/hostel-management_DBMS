package com.hms.HostelManagement.controller;

import com.hms.HostelManagement.model.*;
import com.hms.HostelManagement.repository.HostelRepository;
import com.hms.HostelManagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class StudentController extends BaseController{
    @Autowired
    private ToastService toastService;

    @Autowired
    private StudentService studentService;


    @Autowired
    private UserService userService;

    @Autowired
    private HostelService hostelservice;

    @Autowired
    private HostelRegistrationService hostelRegistrationService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private StudentUserMappingService studentUserMappingService;

    @Autowired
    private JobService jobService;

    @Autowired EmployeeService employeeService;

    @Autowired EmployeeUserMappingService employeeUserMappingService;
    @GetMapping("/registerStudent")
    public String registerStudent(Model model,HttpSession session)
    {
        if(!isAuthenticated(session))
            return "redirect:/";
        addDefaultAttributes(model,session);

        String role=getRoleInSession(session);

        if(role.equals("admin"))
        {

        }
        else
            return "redirect:/";


         // studentusermapping , username password roll hostelregistrationid


        StudentUserMapping studentUserMapping=new StudentUserMapping();
        model.addAttribute("studentUserMapping",studentUserMapping);

        User newUser= new User();
        newUser.setRole("student");
        model.addAttribute("newUser",newUser);

        List<HelperHostelSessionName> registeredHostels = new ArrayList<HelperHostelSessionName>();

        List<HostelRegistration>hr=hostelRegistrationService.getAllRegisteredHostel();
        for(HostelRegistration s:hr)
        {
            HelperHostelSessionName curr=new HelperHostelSessionName();
            curr.setHostelName(hostelservice.getHostelFromId(s.getHostelId()).getHostelName());
            curr.setSessionName(sessionService.getSessionFromId(s.getSessionId()).getSessionName());
            curr.setUint(s.getHostelRegistrationId());
            registeredHostels.add(curr);
//            System.out.println(curr.getUint()+" "+ curr.getHostelName()+" "+curr.getSessionName());
        }


        model.addAttribute("registeredhostels",registeredHostels);


        return "dashboard/registerStudent";
    }
    @PostMapping("/registerStudent")
    public String PostregisterStudent(@ModelAttribute("newUser") User newUser,@ModelAttribute("studentUserMapping") StudentUserMapping s, Model model,HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        String role=getRoleInSession(session);

        if(role.equals("admin"))
        {

        }
        else
            return "redirect:/";

        if(userService.checkUserNameExists(newUser.getUsername()))
        {
            // username already exists
        }
        else
        {
            // also have to check roll no present in hostel registration id of same session
            // check same roll  present in multiple hostels , check roll

            newUser.setRole("student");
            userService.createUser(newUser);

             if(studentService.checkRollNoExists(s.getRoll()))
            {
                // student already added in students table

            }
            else
            {

                Student newStudent = new Student();
                newStudent.setRoll(s.getRoll());
                newStudent.setBalance(0); // set default balance
                studentService.createStudent(newStudent);
            }
            s.setUsername(newUser.getUsername());
            studentUserMappingService.createStudentUserMapping(s);
        }

        return "redirect:/students";
    }


    @GetMapping("/myProfile")
    public String myProfile(Model model,HttpSession session){

        String role=getRoleInSession(session);

        if(role.equals("student"))
        {
            model.addAttribute("thisStudent",studentUserMappingService.getStudentFromUsername(getUserInSession(session).getUsername()));
        }
        else if(role.equals("employee"))
        {
            User u=getUserInSession(session);
            model.addAttribute("thisEmployee",employeeUserMappingService.getEmployeeFromUsername(u.getUsername()));
            model.addAttribute("emu",employeeUserMappingService.getEmployeeUserMappingFromUsername(u.getUsername()));
        }

        addDefaultAttributes(model,session);
        return "dashboard/myProfile";
    }

    @GetMapping("/myProfile/{id}")
    public  String updateMyProfile(@PathVariable int id, Model model,HttpSession session){

        User u=getUserInSession(session);
        if(u.getRole().equals("student"))
        {
            model.addAttribute("thisStudent",studentService.getStudentFromRoll(id));
        }
        else if(u.getRole().equals("employee"))
        {
            model.addAttribute("thisEmployee",employeeService.getEmployeeFromId(id));
            model.addAttribute("emu",employeeUserMappingService.getEmployeeUserMappingFromUsername(u.getUsername()));
        }
        addDefaultAttributes(model,session);
        return "dashboard/updatemyProfile";
    }

    @PostMapping("/myProfile/{id}")
    public  String postupdateMyProfile(@PathVariable int id,@ModelAttribute("thisStudent") Student s,@ModelAttribute("thisEmployee") Employee e, Model model,HttpSession session){

//        System.out.println(s.getName()+" "+s.getEmail());
        User u=getUserInSession(session);
        if(u.getRole().equals("student")) {
            studentService.updateStudentByRoll(s, id);
        }
        else if(u.getRole().equals("employee"))
        {
            employeeService.updateEmployeeFromId(e,id);
        }
        return "redirect:/myProfile";
    }



    @GetMapping("/registerEmployee")
    public String registerEmployee(Model model,HttpSession session){

         if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";

        User newUser = new User();

        model.addAttribute("newUser",newUser);



        EmployeeUserMapping employeeUserMapping =  new EmployeeUserMapping();

        model.addAttribute("employeeUserMapping",employeeUserMapping);


        model.addAttribute("jobs",jobService.getAllJobs());


         List<HelperHostelSessionName> registeredHostels = new ArrayList<HelperHostelSessionName>();

        List<HostelRegistration>hr=hostelRegistrationService.getAllRegisteredHostel();
        for(HostelRegistration s:hr)
        {
            HelperHostelSessionName curr=new HelperHostelSessionName();
            curr.setHostelName(hostelservice.getHostelFromId(s.getHostelId()).getHostelName());
            curr.setSessionName(sessionService.getSessionFromId(s.getSessionId()).getSessionName());
            curr.setUint(s.getHostelRegistrationId());
            registeredHostels.add(curr);
//            System.out.println(curr.getUint()+" "+ curr.getHostelName()+" "+curr.getSessionName());
        }


        model.addAttribute("registeredhostels",registeredHostels);


        return "dashboard/registerEmployee";
    }

    @GetMapping("/employees")
    public String showAllEmployees(Model model,HttpSession session){
         if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        String role=getRoleInSession(session);

        if(role.equals("admin"))
        {

        }
        else
            return "redirect:/";

        List<EmployeeUserMapping>emu=employeeUserMappingService.getAllEmployeeUserMapping();
        List<String>hostelNames=new ArrayList<String >();
        List<String>sessionNames=new ArrayList<String>();
        for(EmployeeUserMapping e:emu)
        {
            HostelRegistration hr=hostelRegistrationService.getHostelRegFromId(e.getHostelRegistrationId());
            hostelNames.add(hostelservice.getHostelFromId(hr.getHostelId()).getHostelName());
            sessionNames.add(sessionService.getSessionFromId(hr.getSessionId()).getSessionName());
        }


        model.addAttribute("hostelNames",hostelNames);
        model.addAttribute("sessionNames",sessionNames);
        model.addAttribute("employeeUsers",emu);
        return "dashboard/allEmployees";
    }



    @GetMapping("/employees/{id}")
    public String viewEmployeeDetails(@PathVariable int id, Model model,HttpSession session){
        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";
        addDefaultAttributes(model,session);
        Employee e=employeeService.getEmployeeFromId(id);
        model.addAttribute("thisEmployee",e);


        return "dashboard/viewEmployeeFromAdmin";
    }
    @PostMapping("/registerEmployee")
    public String PostregisterEmployee(@ModelAttribute("newUser") User newUser,@ModelAttribute("employeeUserMapping") EmployeeUserMapping e, Model model,HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        String role=getRoleInSession(session);

        if(role.equals("admin"))
        {

        }
        else
            return "redirect:/";

        System.out.println(newUser.getUsername() + " "+ newUser.getPassword());
        System.out.println(e.getJobType() + " "+ e.getEmployeeId());

        if(userService.checkUserNameExists(newUser.getUsername()))
        {
            // username already exists
        }
        else
        {
            // also have to check employee Id present in hostel registration id of same session
            // check same roll  present in multiple hostels , check roll

            newUser.setRole("employee");
            userService.createUser(newUser);

             if(employeeService.checkIdExists(e.getEmployeeId()))
            {
                // employee already added in employee table

            }
            else
            {

                Employee newEmployee= new Employee();
                newEmployee.setEmployeeId(e.getEmployeeId());
                employeeService.createEmployee(newEmployee);
            }
            e.setUsername(newUser.getUsername());
            employeeUserMappingService.createEmployeeUserMapping(e);
        }

        return "redirect:/employees";
    }
     @GetMapping("/myJob")
     public String getMyJobDetails(Model model,HttpSession session)
    {
        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("employee"))
        {

        }
        else
            return "redirect:/";

        Job j=employeeUserMappingService.getJobFromEmployeeUsername(u.getUsername());
        model.addAttribute("job",j);
        return "dashboard/jobDetails";
    }



}
