package com.attendenceapp.attendenceapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "attendance_detail")
@Getter
@Setter
public class AttendanceDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login_date")
    @Temporal(TemporalType.DATE)
    private LocalDate loginDate;

    @Column(name = "sign_in_time")
    private LocalTime signInTime;

    @Column(name = "sign_out_time")
    private LocalTime signOutTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_id", nullable = false)
    @JsonBackReference
    private Register register;

    public Long getUserId() {
        return register.getId();
    }

    public String getUserName() {
        return register.getUserName();
    }

}