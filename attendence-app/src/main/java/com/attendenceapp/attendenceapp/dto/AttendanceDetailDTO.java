package com.attendenceapp.attendenceapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class AttendanceDetailDTO {
    private Long id;
    private LocalDate loginDate;
    private LocalTime signInTime;
    private LocalTime signOutTime;
    private Long registerId;

}