package com.hms.HostelManagement.service;

import com.hms.HostelManagement.model.Job;

import java.util.List;

public interface JobService {
    public void createJob(Job j);

    public List<Job> getAllJobs();

    public Job getJobFromType(String t);

    public void updateJobFromType(Job j,String t);
}
