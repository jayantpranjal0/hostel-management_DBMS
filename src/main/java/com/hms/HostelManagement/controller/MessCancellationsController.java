package com.hms.HostelManagement.controller;

import com.hms.HostelManagement.model.*;
import com.hms.HostelManagement.repository.StudentRepository;
import com.hms.HostelManagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class MessCancellationsController extends BaseController {
    @Autowired
    private MessCancellationsService messCancellationsService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;
    @Autowired
    private StudentUserMappingService studentUserMappingService;
    @Autowired
    private HostelRegistrationService hostelRegistrationService;
    @Autowired
    private HostelService hostelService;
    @Autowired
    SessionService sessionService;
    @Autowired
    StudentService studentService;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @GetMapping("/mess")
    public String allCancellations(Model model, HttpSession httpSession, String keyword) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        String username = authenticationService.getCurrentUser(httpSession);
        User user = userService.getUser(username);
        String currRole = user.getRole();
        if (Objects.equals(currRole, "student")) {
            return "redirect:/dashboard";
        }
        if (keyword == null) {
            model.addAttribute("cancellation", messCancellationsService.getAll());
        } else {
            model.addAttribute("cancellation", messCancellationsService.findByKeyword(keyword));
        }
        return "messCancellations/allMessCancellations";
    }

    @GetMapping("/mymess")
    public String myCancellations(Model model, HttpSession httpSession, String keyword) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        String username = authenticationService.getCurrentUser(httpSession);
        User user = userService.getUser(username);
        String currRole = user.getRole();
        System.out.println(currRole);
        if (!Objects.equals(currRole, "student")) {
            return "redirect:/dashboard";
        }
        addDefaultAttributes(model, httpSession);
        System.out.println(username);
        int rollNo = studentUserMappingService.getRollNoFromUsername(username).getRoll();
        System.out.println(rollNo);
        if (keyword == null) {
            model.addAttribute("cancellation", messCancellationsService.filterByRollNo(rollNo));
        } else {
            model.addAttribute("cancellation", messCancellationsService.findByKeywordAndRollNo(keyword, rollNo));
        }
        return "messCancellations/myMessCancellations";
    }

    @PostMapping("/mymess")
    public String postMyCancellations(@ModelAttribute("hostel") HostelRegistration h, @ModelAttribute("mess") MessCancellations m, @ModelAttribute("user") User u, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        Date utildate = m.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
        m.setDate(sqlDate);
        System.out.println(h.getHostelId());
        System.out.println(m.getRollNo());
        System.out.println("reached");
//        StudentUserMapping sum=new StudentUserMapping();
        messCancellationsService.createMessCancellation(m, h, u);
        System.out.println("reached");
        model.addAttribute("cancellation", messCancellationsService.filterByRollNo(m.getRollNo()));
        return "redirect:/mymess";
    }

    @GetMapping("/mess/add")
    public String addMess(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        System.out.println(model);
        addDefaultAttributes(model, session);
        MessCancellations mess = new MessCancellations();
        HostelRegistration hostelRegistration = new HostelRegistration();
        model.addAttribute("mess", mess);
        model.addAttribute("hostel", hostelRegistration);
        return "messCancellations/addMessCancellations";
    }

    @GetMapping("/mymess/add")
    public String addMessStudent(Model model, HttpSession session) throws ParseException {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
//        System.out.println(model);
        String username = authenticationService.getCurrentUser(session);
        System.out.println(username);
        User user = userService.getUser(username);
        addDefaultAttributes(model, session);
        String currRole = user.getRole();
        if (!Objects.equals(currRole, "student")) {
            return "redirect:/dashboard";
        }
        int rollNo = studentUserMappingService.getRollNoFromUsername(username).getRoll();
//        System.out.println(rollNo);
        int hostelRegistrationId = studentUserMappingService.getHostelRegistrationIdFromUsername(username);
        int hostelId = hostelRegistrationService.getHostelIDFromHostelRegistrationId(hostelRegistrationId);
        int sessionId = hostelRegistrationService.getSessionFromHostelRegistrationId(hostelRegistrationId);
        Date startdate = sessionService.getStartDateFromSession(sessionId);
        MessCancellations mess = new MessCancellations();
        HostelRegistration hostelRegistration = new HostelRegistration();
        mess.setRollNo(rollNo);
        mess.setHostelRegistrationId(hostelRegistrationId);
        hostelRegistration.setHostelId(hostelId);
        User u = new User();
        Session ss = new Session();
        ss.setStartDate(startdate);
        u.setUsername(username);
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTime();
        calendar.setTime(startdate);
        calendar.add(Calendar.YEAR, 1);
        model.addAttribute("mess", mess);
        model.addAttribute("hostel", hostelRegistration);
        model.addAttribute("user", u);
        model.addAttribute("sessions", ss);
        model.addAttribute("maxdate", calendar);
        model.addAttribute("mindate", calendar1);
        return "messCancellations/addMyMessCancellations";
    }

    @GetMapping("/mess/checkBalance")
    public String checkBalance(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        System.out.println(model);
        addDefaultAttributes(model, session);

        MessCancellations mess = new MessCancellations();
        model.addAttribute("mess", mess);
        return "messCancellations/checkMessBalance";
    }

    @GetMapping("/mymess/checkBalance")
    public String studentCheckBalance(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        System.out.println(model);
        addDefaultAttributes(model, session);
        String username = authenticationService.getCurrentUser(session);
        User user = userService.getUser(username);
        String currRole = user.getRole();
        if (!Objects.equals(currRole, "student")) {
            return "redirect:/dashboard";
        }
        Integer rollNo = studentUserMappingService.getRollNoFromUsername(username).getRoll();
        int hostelRegistrationId = studentUserMappingService.getHostelRegistrationIdFromUsername(username);
        int sess = hostelRegistrationService.getSessionFromHostelRegistrationId(hostelRegistrationId);
        model.addAttribute("mess", messCancellationsService.filterByRollNoAndSession(rollNo, sess));
        System.out.println(messCancellationsService.filterByRollNoAndSession(rollNo, sess).size());
        return "messCancellations/myBalance";
    }

    @PostMapping("/myBalance")
    public String myBalance(@ModelAttribute("mess") MessCancellations m, @RequestParam("year") Integer year, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        List<MessCancellations> mi = messCancellationsService.balanceByRollNoAndSession(m.getRollNo(), year);
        System.out.println(model);
        model.addAttribute("mess", mi);
        return "messCancellations/myBalance";
    }

    @PostMapping("/mess")
    public String PostAddMess(MessCancellations m, HostelRegistration h, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, session);
        Date utildate = m.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
        m.setDate(sqlDate);
//        messCancellationsService.createMessCancellation(m, h);
        return "redirect:/mess";
    }

    @GetMapping("/mess/update/{id}")
    public String updateMess(@PathVariable(value = "id") Integer id, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        MessCancellations messCancellations = messCancellationsService.getById(id);
        model.addAttribute("cancellation", messCancellations);
        return "messCancellations/updateMessCancellation";
    }

    @GetMapping("/mymess/update/{id}")
    public String studentUpdateMess(@PathVariable(value = "id") Integer id, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        MessCancellations messCancellations = messCancellationsService.getById(id);
        model.addAttribute("cancellation", messCancellations);
        return "messCancellations/updateMessCancellation";
    }
    @GetMapping("/mess/filterByHostelAndSession")
    public String filterMess(Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }

        addDefaultAttributes(model, httpSession);
        model.addAttribute("filter", new AllMessCancellations());
        model.addAttribute("hostels", hostelService.getAllHostel());
        model.addAttribute("sessions", sessionService.getAll());
        return "messCancellations/HostelAndSessionMessCancellation";
    }

    @GetMapping("/mess/filterBySession")
    public String filterSession(Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        model.addAttribute("sId", new Session());
        model.addAttribute("sessions", sessionService.getAll());
        return "messCancellations/SessionMessCancellation";
    }

    @GetMapping("/mess/filterByHostel")
    public String filterByHostel(Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        List<Hostel> allHostels = hostelService.getAllHostel();
        model.addAttribute("cancellation", new Hostel());
        model.addAttribute("hostels", allHostels);
        return "messCancellations/HostelMessCancellation";
    }

    @GetMapping("/mess/filterByRollNo")
    public String filterByRollNo(Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        model.addAttribute("cancellation", new AllMessCancellations());
        model.addAttribute("student", new Student());
        model.addAttribute("students", studentService.getAll());
        return "messCancellations/RollNoMessCancellation";
    }

    @GetMapping("/mymess/filterByRollNo")
    public String filterStudentByRollNo(Model model, HttpSession httpSession, String keyword) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        String username = authenticationService.getCurrentUser(httpSession);
        User user = userService.getUser(username);
        String currRole = user.getRole();
        if (!Objects.equals(currRole, "student")) {
            return "redirect:/dashboard";
        }
        addDefaultAttributes(model, httpSession);
        int rollNo = studentUserMappingService.getRollNoFromUsername(username).getRoll();
        addDefaultAttributes(model, httpSession);
        if (keyword == null) {
            model.addAttribute("cancellation", messCancellationsService.filterByRollNo(rollNo));
        } else {
            model.addAttribute("cancellation", messCancellationsService.findByKeywordAndRollNo(keyword, rollNo));
        }
        return "messCancellations/myMessCancellations";

    }

    @GetMapping("/mess/filterByDate")
    public String filterByDate(Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
//        MessCancellations mi=new MessCancellations();
        model.addAttribute("filter", new RangeDateModel());
        model.addAttribute("mess", new MessCancellations());
        return "messCancellations/DateMessCancellation";
    }

    @GetMapping("/mymess/filterByDate")
    public String studentFilterByDate(Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        String username = authenticationService.getCurrentUser(httpSession);
        User user = userService.getUser(username);
        String currRole = user.getRole();
        if (!Objects.equals(currRole, "student")) {
            return "redirect:/dashboard";
        }
        int rollNo = studentUserMappingService.getRollNoFromUsername(username).getRoll();
        int hostelRegistrationId = studentUserMappingService.getHostelRegistrationIdFromUsername(username);
        int session = hostelRegistrationService.getSessionFromHostelRegistrationId(hostelRegistrationId);
        MessCancellations mi = new MessCancellations();
        mi.setRollNo(rollNo);
        SessionAndRollNo s=new SessionAndRollNo();
        s.setSession(session);
        System.out.println(session);
        model.addAttribute("filter", new RangeDateModel());
        model.addAttribute("mess", mi);
        model.addAttribute("sessions", s);
        return "messCancellations/myMessDateCancellations";
    }

    @GetMapping("/mess/filterByRollNoAndSession")
    public String filterByRollNoAndSession(Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        List<Student>allStudents=studentService.getAll();
        List<Session>allSessions=sessionService.getAll();
        System.out.println(allStudents);
        System.out.println(allSessions);
        addDefaultAttributes(model, httpSession);
        model.addAttribute("filter", new AllMessCancellations());
        model.addAttribute("studstudents",allStudents);
        model.addAttribute("sessions",allSessions);
        return "messCancellations/RollNoAndSessionMessCancellation";
    }

    @GetMapping("/mymess/filterByRollNoAndSession")
    public String StudentFilterByRollNoAndSession(Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        String username = authenticationService.getCurrentUser(httpSession);
        User user = userService.getUser(username);
        String currRole = user.getRole();
        if (!Objects.equals(currRole, "student")) {
            return "redirect:/dashboard";
        }
        Integer rollNo = studentUserMappingService.getRollNoFromUsername(username).getRoll();
        int hostelRegistrationId = studentUserMappingService.getHostelRegistrationIdFromUsername(username);
        int session = hostelRegistrationService.getSessionFromHostelRegistrationId(hostelRegistrationId);
        model.addAttribute("cancellation", messCancellationsService.filterByRollNoAndSession(rollNo, session));
        return "messCancellations/myMessCancellations";
    }

    @PostMapping("/update")
    public String PostUpdateMess(@ModelAttribute("cancellation") MessCancellations m, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        Date utildate = m.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
        m.setDate(sqlDate);
        messCancellationsService.updateMessCancellation(m);
        return "redirect:/mess";
    }

    @PostMapping("/myMessUpdate")
    public String studentPostUpdateMess(@ModelAttribute("cancellation") MessCancellations m, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        Date utildate = m.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
        m.setDate(sqlDate);
        messCancellationsService.updateMessCancellation(m);
        return "redirect:/mymess";
    }

    @PostMapping("/filterByHostelAndSession")
    public String PostFilterMess(@ModelAttribute("filter") AllMessCancellations m, @ModelAttribute("hostels") Hostel hostel, @ModelAttribute("sessions") Session session, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        List<AllMessCancellations> mi = messCancellationsService.filterByHostelIdAndSessionId(hostel.getHostelId(), session.getSessionId());
        for (AllMessCancellations mo : mi) {
            Date utildate = mo.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
            mo.setDate(sqlDate);
        }
        model.addAttribute("cancellation", mi);
        return "messCancellations/allMessCancellations";
    }


    @PostMapping("/mess/postFilterByRollNo")
    public String PostFilterByRollNo(@ModelAttribute("cancellation") AllMessCancellations allMessCancellations,@ModelAttribute("student") Student student, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        List<AllMessCancellations> mi = messCancellationsService.filterByRollNo(student.getRoll());
//        System.out.println(allMessCancellations.getStudentRollNo());
        for (AllMessCancellations mo : mi) {
            Date utildate = mo.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
            mo.setDate(sqlDate);
        }
        model.addAttribute("cancellation", mi);
        return "messCancellations/allMessCancellations";
    }

    @PostMapping("/mess/postFilterByDate")
    public String PostFilterByDate(@ModelAttribute("filter") RangeDateModel rangeDateModel, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        List<AllMessCancellations> mi = messCancellationsService.filterByDate(rangeDateModel.getStart(), rangeDateModel.getEnd());
        for (AllMessCancellations mo : mi) {
            Date utildate = mo.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
            mo.setDate(sqlDate);
        }
        model.addAttribute("cancellation", mi);
        return "messCancellations/allMessCancellations";
    }

    @PostMapping("/mymess/postFilterByDate")
    public String studentPostFilterByDate(@ModelAttribute("filter") RangeDateModel rangeDateModel, @ModelAttribute("mess") MessCancellations m, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        System.out.println(m.getRollNo());
        List<AllMessCancellations> mi = messCancellationsService.filterByDateAndRoll(rangeDateModel.getStart(), rangeDateModel.getEnd(), m.getRollNo());
        for (AllMessCancellations mo : mi) {
            Date utildate = mo.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
            mo.setDate(sqlDate);
        }
        model.addAttribute("cancellation", mi);
        return "messCancellations/allMessCancellations";
    }

    @PostMapping("/mess/postFilterByRollNoAndSession")
    public String PostFilterByRollNoAndSession(@ModelAttribute("filter") AllMessCancellations allMessCancellations, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        List<AllMessCancellations> mi = messCancellationsService.filterByRollNoAndSession(allMessCancellations.getStudentRollNo(), allMessCancellations.getSessionId());
        for (AllMessCancellations mo : mi) {
            Date utildate = mo.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
            mo.setDate(sqlDate);
        }
        model.addAttribute("cancellation", mi);
        return "messCancellations/allMessCancellations";
    }
    @PostMapping("/filterBySession")
    public String PostFilterMessSession(@ModelAttribute("sId") Session s, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        List<AllMessCancellations> mi = messCancellationsService.filterBySession(s.getSessionId());
        model.addAttribute("cancellation", mi);
        return "messCancellations/allMessCancellations";
    }

    @PostMapping("/filterByHostel")
    public String PostFilterMessSessionAndHostel(@ModelAttribute("cancellation") Hostel hostel, Model model, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
//        System.out.println(m.getHostelId());
        List<AllMessCancellations> mi = messCancellationsService.filterByHostel(hostel.getHostelId());
        model.addAttribute("cancellation", mi);
        return "messCancellations/allMessCancellations";
    }

    @GetMapping("mess/delete/{id}")
    public String deleteEntry(@PathVariable("id") Integer id, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        messCancellationsService.deleteMessCancellations(id);
        return "redirect:/mess";
    }

    @GetMapping("mymess/delete/{id}")
    public String studentDeleteEntry(@PathVariable("id") Integer id, HttpSession httpSession) {
        if (!isAuthenticated(httpSession)) {
            return "redirect:/";
        }
        messCancellationsService.deleteMessCancellations(id);
        return "redirect:/mymess";
    }
}
