package com.hms.HostelManagement.controller;

import com.hms.HostelManagement.model.*;
import com.hms.HostelManagement.model.Complaint;
import com.hms.HostelManagement.model.Hostel;
import com.hms.HostelManagement.model.User;
import com.hms.HostelManagement.repository.HostelRepository;
import com.hms.HostelManagement.repository.StudentUserMappingRepository;
import com.hms.HostelManagement.service.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
@Controller
public class DashboardController extends BaseController {
    @Autowired
    private ToastService toastService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private HostelService hostelservice;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentUserMappingService studentUserMappingService;

    @Autowired
    private SessionService sessionService;
    @Autowired
    private HostelRegistrationService hostelRegistrationService;
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private JobService jobService;

    @Autowired
    private EmployeeUserMappingService employeeUserMappingService;
    // Needed to automatically convert String date in form to Date object.

    @Autowired
    private Environment env;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private MessCancellationsService messCancellationsService;
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @GetMapping("")
    public String baseHome(Model model,HttpSession session){
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/login";
        }


        addDefaultAttributes(model, session);

        if(getRoleInSession(session).equals("student"))
        {
            model.addAttribute("thisStudent",studentUserMappingService.getStudentFromUsername(getUserInSession(session).getUsername()));
        }
        if(getRoleInSession(session).equals("employee"))
            return "dashboard/employeeIndex";

        return "dashboard/index";
    }

    @GetMapping("/complaints")
    public String allcomplaint(Model model, HttpSession session, Integer keyword) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, session);

        User u=getUserInSession(session);

        if(u.getRole().equals("admin"))
        {
            List<Complaint>cl=new ArrayList<Complaint>();
            if (keyword != null) {
                cl=complaintService.getAllComplaintByRoll(keyword);
            }
            else
            {
                cl=complaintService.getAllComplaint();
            }
            List<String>hostelNames=new ArrayList<String >();
            List<String>sessionNames=new ArrayList<String>();
            for(Complaint c:cl)
            {
                HostelRegistration hr=hostelRegistrationService.getHostelRegFromId(c.getHostelRegistrationId());
                hostelNames.add(hostelservice.getHostelFromId(hr.getHostelId()).getHostelName());
                sessionNames.add(sessionService.getSessionFromId(hr.getSessionId()).getSessionName());
            }

            model.addAttribute("complaints",cl);
            model.addAttribute("hostelNames",hostelNames);
            model.addAttribute("sessionNames",sessionNames);
        }
        else if(u.getRole().equals("student")){

            int hrId=studentUserMappingService.getHostelRegistrationIdFromUsername(u.getUsername());
            int roll=studentUserMappingService.getRollNoFromUsername(u.getUsername()).getRoll();
            model.addAttribute("complaints",complaintService.getStudentComplaint(roll,hrId));
        }
        else if(u.getRole().equals("employee"))
        {
            int hrId=employeeUserMappingService.getEmployeeUserMappingFromUsername(u.getUsername()).getHostelRegistrationId();
            System.out.println(hrId);
            model.addAttribute("complaints",complaintService.getEmployeeComplaint(hrId));
            model.addAttribute("job",employeeUserMappingService.getJobFromEmployeeUsername(u.getUsername()));
        }
        return "dashboard/allComplaint";
    }



//    @GetMapping("/mycomplaints")
//    public String mycomplaint(Model model, HttpSession session) {
//        if (!isAuthenticated(session))
//            return "redirect:/";
//        String username = authenticationService.getCurrentUser(session);
//        User user = userService.getUser(username);
//        String currRole = user.getRole();
//        if (!currRole.equals("student")) {
//            return "redirect:/dashboard";
//        }
//        addDefaultAttributes(model, session);
//        int rollNo = studentUserMappingService.getRollNofromUsername(username);
//        model.addAttribute("complaints", complaintService.getParticularComplaint(rollNo));
//        return "dashboard/myComplaints";
//    }

    @PostMapping("/complaints")
    public String PostaddComplaint(@ModelAttribute("complaint") Complaint complaint, Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, session);

        User u=getUserInSession(session);
        int hrId=studentUserMappingService.getHostelRegistrationIdFromUsername(u.getUsername());
        int roll=studentUserMappingService.getRollNoFromUsername(u.getUsername()).getRoll();
        complaint.setRollNo(roll);
        complaint.setHostelRegistrationId(hrId);
        complaintService.createComplaint(complaint);
        return "redirect:/complaints";
    }

    @GetMapping("/complaints/add")
    public String addComplaint(Model model, HttpSession session) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, session);
        Complaint complaint = new Complaint();
        model.addAttribute("complaint", complaint);
        return "dashboard/addComplaint";
    }

    @GetMapping("/hostels")
    public String allHostel(Model model,HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        addDefaultAttributes(model,session);

        model.addAttribute("hostels",hostelservice.getAllHostel());

        return "dashboard/allHostel";

    }

    // getmapping add hostel , temporaily pass new empty hostel object to form template

    @GetMapping("/hostels/add")
    public String addHostel(Model model,HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, session);

        Hostel hostel=new Hostel();

        model.addAttribute("hostel",hostel);

        return "dashboard/addHostel";

    }

    @PostMapping("/hostels")
    public String PostaddHostel(@ModelAttribute("hostel") Hostel h, Model model,HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        addDefaultAttributes(model,session);
        hostelservice.createHostel(h);
        return "redirect:/hostels";
    }

    @GetMapping("/sessions")
    public String allSession(Model model,HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        addDefaultAttributes(model,session);


        List<Session> st=sessionService.getAllSession();
//        for(Session s:st)
//            System.out.println(s.getSessionName()+" "+s.getStartDate());

        model.addAttribute("sessions",sessionService.getAllSession());

        return "dashboard/allSessions";
    }

    @GetMapping("/sessions/add")
    public String addSession(Model model,HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        addDefaultAttributes(model,session);

        Session newSession = new Session();
        model.addAttribute("newSession",newSession);

        return "dashboard/addSession";
    }

    @PostMapping("/sessions")
    public String postaddSession(@ModelAttribute("newsession") Session s,Model model,HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        sessionService.createSession(s);
        return "redirect:/sessions";
    }




    @GetMapping("/registeredHostels")
    public String RegisteredHostels(Model model,HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        List<HostelRegistration>hr=hostelRegistrationService.getAllRegisteredHostel();

        List<HelperHostelSessionName> registeredHostels = new ArrayList<HelperHostelSessionName>();

        for(HostelRegistration s:hr)
        {
            HelperHostelSessionName curr=new HelperHostelSessionName();
            curr.setHostelName(hostelservice.getHostelFromId(s.getHostelId()).getHostelName());
            curr.setSessionName(sessionService.getSessionFromId(s.getSessionId()).getSessionName());
            curr.setUint(s.getRoomCount());
            registeredHostels.add(curr);
        }


        model.addAttribute("registeredhostels",registeredHostels);
        return "dashboard/registeredHostels";
    }
    @GetMapping("/registerHostelSession")
    public String RegisterHostelWithSession(Model model,HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        HostelRegistration hostelRegistration=new HostelRegistration();
        model.addAttribute("hostelRegistration",hostelRegistration);

        model.addAttribute("hostels",hostelservice.getAllHostel());
        model.addAttribute("sessions",sessionService.getAllSession());
        return "dashboard/registerHostelSession";
    }


    @PostMapping("/registeredHostels")
    public String PostRegisteredHostels(@ModelAttribute("hostelRegistration") HostelRegistration hr,Model model,HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }

        hostelRegistrationService.createHostelRegistration(hr);

        return "redirect:/registeredHostels";
    }


    @GetMapping("/notices")
    public String ShowAllNotice(Model model,HttpSession session) {
        if(!isAuthenticated(session))
            return "redirect:/";
        String role=getRoleInSession(session);
        if(role.equals("admin"))
        {
            List<Notice>notices=noticeService.getAllNotices();
            model.addAttribute("notices",notices);

            List<String>hostels=new ArrayList<String >();
            List<String>sessions=new ArrayList<String>();
            for(Notice i:notices)
            {
                HostelRegistration hr=hostelRegistrationService.getHostelRegFromId(i.getHostelRegistrationId());
                hostels.add(hostelservice.getHostelFromId(hr.getHostelId()).getHostelName());
                sessions.add(sessionService.getSessionFromId(hr.getSessionId()).getSessionName());
            }
//            System.out.println("debug");
//            System.out.println(hostels);
//            System.out.println(sessions);

            model.addAttribute("hostels",hostels);
            model.addAttribute("sessions",sessions);
        }
        else if(role.equals("student")){

            User u=getUserInSession(session);
            int hid= studentUserMappingService.getHostelRegIdFromUsername(u.getUsername());
            model.addAttribute("notices",noticeService.getAllNoticesFromHostelReg(hid));
        }
        addDefaultAttributes(model,session);
        return "dashboard/notices";
    }


    @GetMapping("notice/add")
    public String addNotice(Model model,HttpSession session){

        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";

        Notice notice=new Notice();

        model.addAttribute("notice",notice);

        List<HostelRegistration>hr=hostelRegistrationService.getAllRegisteredHostel();

        List<HelperHostelSessionName> registeredHostels = new ArrayList<HelperHostelSessionName>();

        for(HostelRegistration s:hr)
        {
            HelperHostelSessionName curr=new HelperHostelSessionName();
            curr.setHostelName(hostelservice.getHostelFromId(s.getHostelId()).getHostelName());
            curr.setSessionName(sessionService.getSessionFromId(s.getSessionId()).getSessionName());
            curr.setUint(s.getHostelRegistrationId());
            registeredHostels.add(curr);
        }

        model.addAttribute("registeredhostels",registeredHostels);


        return "dashboard/addNotice";
    }

    @PostMapping("/notices")
    public String PostaddNotice(@ModelAttribute("notice") Notice i, Model model,HttpSession session) {
        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";

        noticeService.createNotice(i);


        return "redirect:/notices";
    }


    @GetMapping("notices/{id}")
    public String updateNotice(@PathVariable int id,Model model,HttpSession session){

        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";

        Notice currNotice=noticeService.getNoticeFromId(id);

        model.addAttribute("notice",currNotice);

        return "dashboard/updateNotice";
    }
    @PostMapping("notices/{id}")
    public String postupdateNotice(@PathVariable int id,@ModelAttribute ("notice") Notice i,Model model,HttpSession session){

        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";

        noticeService.updateNoticeDetails(i,id);

        return "redirect:/notices";
    }

    @GetMapping("/students")
    public String showAllStudents(Model model,HttpSession session){
        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";

        List<StudentUserMapping>stu=studentUserMappingService.getAllStudentUserMapping();
        List<String>hostelNames=new ArrayList<String >();
        List<String>sessionNames=new ArrayList<String>();
        for(StudentUserMapping s:stu)
        {
            HostelRegistration hr=hostelRegistrationService.getHostelRegFromId(s.getHostelRegistrationId());
            hostelNames.add(hostelservice.getHostelFromId(hr.getHostelId()).getHostelName());
            sessionNames.add(sessionService.getSessionFromId(hr.getSessionId()).getSessionName());
        }

        model.addAttribute("hostelNames",hostelNames);
        model.addAttribute("sessionNames",sessionNames);
        model.addAttribute("studentUsers",stu);
        return "dashboard/allStudents";
    }
    @GetMapping("/students/{id}")
    public String viewStudentDetails(@PathVariable int id, Model model,HttpSession session){
        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";
        addDefaultAttributes(model,session);
        Student s=studentService.getStudentFromRoll(id);
        model.addAttribute("thisStudent",s);
        return "dashboard/viewStudentFromAdmin";
    }

    @GetMapping("/jobs")
    public String viewAllJobs(Model model,HttpSession session){
        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";


        addDefaultAttributes(model, session);
        model.addAttribute("jobs",jobService.getAllJobs());

        return "dashboard/allJobs";
    }
    @GetMapping("/jobs/add")
    public String addJob(Model model,HttpSession session){
        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";


        addDefaultAttributes(model, session);

        Job job = new Job();

        model.addAttribute("job",job);

        return "dashboard/addJob";
    }

    @PostMapping("/jobs")
    public String postaddJob(@ModelAttribute("job") Job j,Model model,HttpSession session){
         if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";

        jobService.createJob(j);

        return "redirect:/jobs";
    }

    @GetMapping("/jobs/{id}")
    public String updateJob(@PathVariable String id,Model model,HttpSession session){
         if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";

        addDefaultAttributes(model,session);
        Job j=jobService.getJobFromType(id);
        model.addAttribute("job",j);
        return "dashboard/updateJob";
    }
    @PostMapping("/jobs/{id}")
    public String postupdateJob(@PathVariable String id,@ModelAttribute("job") Job j, Model model,HttpSession session){
         if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("admin"))
        {

        }
        else
            return "redirect:/";

//        System.out.println(j.getJobType());

        jobService.updateJobFromType(j,id);

        addDefaultAttributes(model,session);
        return "redirect:/jobs";
    }


    @GetMapping("/aboutUs")
    public String aboutUsPage(Model model,HttpSession session){

        return "dashboard/aboutUs";
    }
    @GetMapping("/contactUs")
    public String contactUsPage(Model model,HttpSession session){

        return "dashboard/contactUs";
    }

    @GetMapping("/myBalance")
    public  String myBalancePage(Model model,HttpSession session){
          if(!isAuthenticated(session))
            return "redirect:/";
          User u=getUserInSession(session);
          if(u.getRole().equals("student"))
          {

          }
          else
            return "redirect:/";

        Student s=studentUserMappingService.getStudentFromUsername(u.getUsername());

        int hrId=studentUserMappingService.getHostelRegIdFromUsername(u.getUsername());
        int roll=s.getRoll();
        Date d=new Date();
        int entry= messCancellationsService.getEntryCount(hrId,roll,d);

        int oneday=100;

        Student ns=new Student();

        Date sd=hostelRegistrationService.getStartDateFromHrId(hrId);
        Date date1 = new Date(); // Replace with your first Date
        date1=sd;
        // Convert Date objects to LocalDate
        LocalDate localDate1 = date1.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = LocalDate.now();
        System.out.println(localDate1);
        System.out.println(localDate2);
        // Calculate the difference in days
        int daysBetween = (int)ChronoUnit.DAYS.between(localDate1, localDate2);
        int addbal=-daysBetween*oneday+entry*oneday;
        System.out.println(daysBetween);
        System.out.println(entry);
        ns.setBalance(addbal);
        System.out.println(addbal);
        model.addAttribute("nstudent",ns);
        model.addAttribute("student",s);
        return "dashboard/myBalance";
    }


//    @GetMapping("/setRechargeValue")
//    public  String setRechargePage(Model model,HttpSession session){
//        if(!isAuthenticated(session))
//            return "redirect:/";
//        User u=getUserInSession(session);
//        if(u.getRole().equals("student"))
//        {
//
//        }
//        else
//            return "redirect:/";
//
//        Transaction transaction = new Transaction();
//
//        model.addAttribute("transaction",transaction);
//
//        return "dashboard/setRechargeValue";
//    }



//    @RequestMapping(value = {"/payment"}, method = RequestMethod.GET)
    @GetMapping("/payment")
    public String payment(  Model model,HttpSession session){
        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("student"))
        {

        }
        else
            return "redirect:/";
//        if(t==null)
//            return "redirect:/";

        model.addAttribute("rzp_key_id", env.getProperty("rzp_key_id"));
        model.addAttribute("rzp_currency", env.getProperty("rzp_currency"));
        model.addAttribute("rzp_company_name", env.getProperty("rzp_company_name"));
//        Transaction tnew= new Transaction();
//        tnew.setAmount(t.getAmount());
//        tnew.setDescription(t.getDescription());
//        model.addAttribute("tnew",tnew);
        return "dashboard/payment";
    }
    @GetMapping("/payment/createOrderId/{amount}")
    @ResponseBody
    public String createPaymentOrderId(@PathVariable String amount,HttpSession session) {
        String orderId=null;
        try {
            RazorpayClient razorpay = new RazorpayClient(env.getProperty("rzp_key_id"), env.getProperty("rzp_key_secret"));
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount); // amount in the smallest currency unit
            orderRequest.put("currency", env.getProperty("rzp_currency"));
            orderRequest.put("receipt", "order_rcptid_11");

            Order order = razorpay.orders.create(orderRequest);
            orderId = order.get("id");
        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }
        return orderId;
    }
    @RequestMapping(value = {"/payment/success/{amount}/{contactCount}/{companyName}/{currency}/{description}"}, method = RequestMethod.POST)
    public String paymentSuccess(Model model,HttpSession session,
                                 Authentication authentication,
                                 @RequestParam("razorpay_payment_id") String razorpayPaymentId,
                                 @RequestParam("razorpay_order_id") String razorpayOrderId,
                                 @RequestParam("razorpay_signature") String razorpaySignature,
                                 @PathVariable Float amount,
                                 @PathVariable Integer contactCount,
                                 @PathVariable String companyName,
                                 @PathVariable String currency,
                                 @PathVariable String description,
                                 RedirectAttributes redirectAttributes
    ){
        if(!isAuthenticated(session))
            return "redirect:/";
        User u=getUserInSession(session);
        if(u.getRole().equals("student"))
        {

        }
        else
            return "redirect:/";

        Transaction t= new Transaction();
        t.setRoll(studentUserMappingService.getRollNoFromUsername(u.getUsername()).getRoll());
        t.setHostelRegistrationId(studentUserMappingService.getHostelRegistrationIdFromUsername(u.getUsername()));

        t.setPaymentId(razorpayPaymentId);
        t.setSignature(razorpaySignature);
        t.setDescription(description);
        t.setCurrency(currency);
        int val=Math.round(amount);
        t.setAmount(val);

        Student oldStudent = studentUserMappingService.getStudentFromUsername(u.getUsername());
        val+=oldStudent.getBalance();
        studentService.updateStudentBalanceByRoll(val,oldStudent.getRoll());
        transactionService.createTransaction(t);
//        System.out.println(razorpayPaymentId+" "+razorpayOrderId+" "+razorpaySignature+" "+amount+" "+currency+" "+description);
//        System.out.println("Save all data, which on success we get!");
        return "redirect:/myBalance";
    }

}
