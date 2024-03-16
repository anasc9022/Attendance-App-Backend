package com.attendenceapp.attendenceapp.service;

import com.attendenceapp.attendenceapp.entity.AttendanceDetail;
import org.springframework.stereotype.Service;

@Service
public interface AttendanceService {

    public AttendanceDetail signOut(Long attendanceId);
}
