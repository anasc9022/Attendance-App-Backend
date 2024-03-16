package com.attendenceapp.attendenceapp.controller;

import com.attendenceapp.attendenceapp.dto.AttendanceDetailDTO;
import com.attendenceapp.attendenceapp.dto.RegisterDTO;
import com.attendenceapp.attendenceapp.entity.AttendanceDetail;
import com.attendenceapp.attendenceapp.repository.UserRegisterRepository;
import com.attendenceapp.attendenceapp.repository.AttendanceRepository;
import com.attendenceapp.attendenceapp.entity.Register;
import com.attendenceapp.attendenceapp.service.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api")
public class RegisterController {
    Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserRegisterRepository userRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Register user) {
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/attendance/sign-in")
    public ResponseEntity<?> loginUser(@RequestBody Register loginUser) {
        try {
            Register user = userRepository.findByUserName(loginUser.getUserName());

            if (user == null || !user.getPassword().equals(loginUser.getPassword())) {
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }

            List<AttendanceDetail> attendanceList = user.getAttendanceDetails();
            AttendanceDetail lastAttendance = attendanceList.stream()
                    .sorted(Comparator.comparingLong(AttendanceDetail::getId).reversed())
                    .findFirst()
                    .orElse(null);
            if (lastAttendance == null ) {
                AttendanceDetail attendance = new AttendanceDetail();
                attendance.setSignInTime(LocalTime.now());
                attendance.setLoginDate(LocalDate.now());
                attendance.setRegister(user);
                AttendanceDetail userAtt = attendanceRepository.save(attendance);

                logger.info("Saved user details: {}", userAtt);
                return ResponseEntity.ok(userAtt);
            }
            else if (lastAttendance != null && !lastAttendance.getLoginDate().isEqual(LocalDate.now())) {
                long daysDifference = ChronoUnit.DAYS.between(lastAttendance.getLoginDate(), LocalDate.now());
                if (daysDifference == 1) {
                    AttendanceDetail attendance = new AttendanceDetail();
                    attendance.setSignInTime(LocalTime.now());
                    attendance.setLoginDate(LocalDate.now());
                    attendance.setRegister(user);
                    AttendanceDetail userAtt = attendanceRepository.save(attendance);

                    logger.info("Saved user details: {}", userAtt);
                    return ResponseEntity.ok(userAtt);
                } else {
                    for (int i = 1; i < daysDifference; i++) {
                        AttendanceDetail attendance = new AttendanceDetail();
                        attendance.setSignInTime(null); // Assuming you want to set null for SignInTime for missing days
                        attendance.setLoginDate(lastAttendance.getLoginDate().plusDays(i));
                        attendance.setRegister(user);
                        AttendanceDetail userAtt = attendanceRepository.save(attendance);

                        logger.info("Saved user details: {}", userAtt);
                    }
                    // Log in for today
                    AttendanceDetail attendance = new AttendanceDetail();
                    attendance.setSignInTime(LocalTime.now());
                    attendance.setLoginDate(LocalDate.now());
                    attendance.setRegister(user);
                    AttendanceDetail userAtt = attendanceRepository.save(attendance);

                    logger.info("Saved user details: {}", userAtt);
                    return ResponseEntity.ok(userAtt);
                }
            } else {
                // User has already logged in today, you can return an appropriate response
                return ResponseEntity.ok(lastAttendance);
            }

        } catch (Exception e) {
            logger.error("Error occurred while processing login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing login");
        }
    }



    @PostMapping("/attendance/sign-out")
    public ResponseEntity<?> signOut(@RequestBody Map<String, String> requestBody) {
        try {
            String attendanceId = requestBody.get("attendanceId");
            if (attendanceId == null) {
                return ResponseEntity.badRequest().body("attendanceId is required");
            }
            Long id = Long.parseLong(attendanceId);
            AttendanceDetail updatedAttendance = attendanceService.signOut(id);
            return ResponseEntity.ok(updatedAttendance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while signing out");
        }
    }
}

