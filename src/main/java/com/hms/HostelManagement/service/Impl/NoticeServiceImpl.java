package com.hms.HostelManagement.service.Impl;

import com.hms.HostelManagement.model.Notice;
import com.hms.HostelManagement.repository.NoticeRepository;
import com.hms.HostelManagement.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    NoticeRepository noticeRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        super();
        this.noticeRepository = noticeRepository;
    }

    @Override
    public void createNotice(Notice i) {
        noticeRepository.createNotice(i);
    }

    @Override
    public List<Notice> getAllNotices() {
        return noticeRepository.getAllNotices();
    }

    @Override
    public List<Notice> getAllNoticesFromHostelReg(int id) {
        return noticeRepository.getAllNoticesFromHostelReg(id);
    }

    @Override
    public Notice getNoticeFromId(int id) {
        return noticeRepository.getNoticeFromId(id);
    }

    @Override
    public void updateNoticeDetails(Notice i, int id) {
        noticeRepository.updateNotice(i,id);
    }
}
