package com.attendenceapp.attendenceapp.repository;

import com.attendenceapp.attendenceapp.entity.AttendanceDetail;
import com.attendenceapp.attendenceapp.entity.Register;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@CrossOrigin("http://localhost:4200")
@Repository
@RepositoryRestResource(collectionResourceRel = "reports", path = "attendance")
public interface AttendanceRepository extends JpaRepository<AttendanceDetail, Long> {

    Page<AttendanceDetail> findByRegisterId(@Param("id") Long id, Pageable pageable);

}
