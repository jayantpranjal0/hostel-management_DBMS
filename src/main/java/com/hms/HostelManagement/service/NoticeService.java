package com.hms.HostelManagement.service;

import com.hms.HostelManagement.model.Notice;

import java.util.List;

public interface NoticeService {

    public void createNotice(Notice i);

    public List<Notice> getAllNotices();

    public List<Notice> getAllNoticesFromHostelReg(int id);

    public Notice getNoticeFromId(int id);

    public void updateNoticeDetails(Notice i,int id);
}
