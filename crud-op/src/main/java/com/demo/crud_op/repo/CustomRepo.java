package com.demo.crud_op.repo;

import com.demo.crud_op.dto.EmployeeDTO;
import com.demo.crud_op.model.Employee;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomRepo extends JpaRepository<Employee,Integer> {

    @Query("SELECT new com.demo.crud_op.dto.EmployeeDTO(e.id, e.firstName, e.lastName) FROM Employee e ")
    Page<EmployeeDTO> findEmployeeDetails(Pageable pageable);

}