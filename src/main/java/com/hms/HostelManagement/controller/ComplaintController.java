package com.hms.HostelManagement.controller;

import com.hms.HostelManagement.model.Complaint;
import com.hms.HostelManagement.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ComplaintController {
        @Autowired
        private ComplaintService complaintService;

        @PostMapping("/updateStatus")
        public String updateComplaintStatus(@RequestParam int complaintID) {
            complaintService.updateComplaintStatus(complaintID);
            return "redirect:/complaints";
        }
    @PostMapping("/updateStatusDel")
    public String updateComplaintStatusDel(@RequestParam int complaintID) {
        complaintService.updateComplaintStatusDel(complaintID);
        return "redirect:/complaints";
    }
        @PostMapping("/updateStatusAll")
        public String updateComplaintStatusAll() {
            complaintService.updateComplaintStatusAll();
            return "redirect:/complaints";
        }
    }
