package com.hms.HostelManagement.service;

import com.hms.HostelManagement.model.Hostel;

import java.util.List;

public interface HostelService {

    List<Hostel> getAllHostel();

    public void createHostel(Hostel h);

    public Hostel getHostelFromId(int id);
}
