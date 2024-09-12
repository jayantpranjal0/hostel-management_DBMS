package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.Job;
import com.hms.HostelManagement.repository.JobRepository;
import com.hms.HostelManagement.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        super();
        this.jobRepository = jobRepository;
    }

    @Override
    public void createJob(Job j) {
        jobRepository.createJob(j);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.getAllJobs();
    }

    @Override
    public Job getJobFromType(String t) {
        return jobRepository.getJobFromType(t);
    }

    @Override
    public void updateJobFromType(Job j, String t) {
        jobRepository.updateJobFromType(j,t);
    }
}
