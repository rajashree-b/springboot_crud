package com.demo.crud_op.repo;


import com.demo.crud_op.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {

    boolean existsByEmail(String email);
}
