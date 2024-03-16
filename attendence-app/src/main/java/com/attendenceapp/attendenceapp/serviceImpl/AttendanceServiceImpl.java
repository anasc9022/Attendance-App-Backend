package com.attendenceapp.attendenceapp.serviceImpl;

import com.attendenceapp.attendenceapp.entity.AttendanceDetail;
import com.attendenceapp.attendenceapp.repository.AttendanceRepository;
import com.attendenceapp.attendenceapp.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public AttendanceDetail signOut(Long attendanceId) {
        AttendanceDetail attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new IllegalStateException("Attendance not found with id: " + attendanceId));

        if (attendance.getSignOutTime() != null) {
            throw new IllegalStateException("Attendance already signed out");
        }

        attendance.setSignOutTime(LocalTime.now());

        AttendanceDetail savedAttendance = attendanceRepository.save(attendance);
        return savedAttendance;
    }


}
