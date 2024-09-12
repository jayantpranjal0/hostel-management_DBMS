package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.HostelRegistration;
import com.hms.HostelManagement.repository.HostelRegistrationRepository;
import com.hms.HostelManagement.service.HostelRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HostelRegistrationServiceImpl implements HostelRegistrationService {

    @Autowired
    private HostelRegistrationRepository hostelRegistrationRepository;

    public HostelRegistrationServiceImpl(HostelRegistrationRepository hostelRegistrationRepository) {
        super();
        this.hostelRegistrationRepository = hostelRegistrationRepository;
    }

    @Override
    public List<HostelRegistration> getAllRegisteredHostel() {
        return hostelRegistrationRepository.getAllRegisteredHostels();
    }

    @Override
    public void createHostelRegistration(HostelRegistration hr) {
        hostelRegistrationRepository.createHostelRegistration(hr);
    }

    @Override
    public HostelRegistration getHostelRegFromId(int id) {
        return hostelRegistrationRepository.getHostelRegFromId(id);
    }

    @Override
    public int getHostelIDFromHostelRegistrationId(Integer id) {
        return hostelRegistrationRepository.getHostelRegFromId(id).getHostelId();
    }

    @Override
    public int getSessionFromHostelRegistrationId(Integer id) {
        return hostelRegistrationRepository.getHostelRegFromId(id).getSessionId();
    }

    @Override
    public Date getStartDateFromHrId(int hrId) {
        return hostelRegistrationRepository.getSessionFromHrId(hrId).getStartDate();
    }
}
