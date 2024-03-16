package com.attendenceapp.attendenceapp.repository;

import com.attendenceapp.attendenceapp.entity.AttendanceDetail;
import com.attendenceapp.attendenceapp.entity.Register;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@Repository
@CrossOrigin("http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "users", path = "all-users")
public interface UserRegisterRepository extends JpaRepository<Register, Long> {
    Register findByUserName(String username);
    
}
