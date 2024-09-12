package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.Hostel;
import com.hms.HostelManagement.repository.HostelRepository;
import com.hms.HostelManagement.service.HostelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostelServiceImpl implements HostelService {
    @Autowired
    HostelRepository hostelRepository;

    public HostelServiceImpl(HostelRepository hostelRepository) {
        super();
        this.hostelRepository = hostelRepository;
    }

    @Override
    public List<Hostel> getAllHostel() {
        return hostelRepository.getAllHostels();
    }

    @Override
    public void createHostel(Hostel h) {
        hostelRepository.createHostel(h);
    }

    @Override
    public Hostel getHostelFromId(int id) {
        return hostelRepository.getHostelFromId(id);
    }


}
