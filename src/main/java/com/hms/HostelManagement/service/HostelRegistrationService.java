package com.hms.HostelManagement.service;

import com.hms.HostelManagement.model.HostelRegistration;

import java.util.Date;
import java.util.List;

public interface HostelRegistrationService {
    List<HostelRegistration> getAllRegisteredHostel();

    public void createHostelRegistration(HostelRegistration hr);

    public HostelRegistration getHostelRegFromId(int id);

    public int getHostelIDFromHostelRegistrationId(Integer HostelRegistrationId);

    public int getSessionFromHostelRegistrationId(Integer HostelRegistrationId);

    public Date getStartDateFromHrId(int hrId);
}
