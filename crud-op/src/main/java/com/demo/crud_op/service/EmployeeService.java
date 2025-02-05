package com.demo.crud_op.service;


import com.demo.crud_op.dto.EmployeeDTO;
import com.demo.crud_op.model.Employee;
import com.demo.crud_op.repo.CustomRepo;
import com.demo.crud_op.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo repo;

    @Autowired
    private CustomRepo customRepo;

    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    public List<EmployeeDTO> getEmpDetails() {
        Page<EmployeeDTO>employeeDTOPage=customRepo.findEmployeeDetails(PageRequest.of(0,5,Sort.by("firstName")));
        return employeeDTOPage.getContent();
    }

 public Page<EmployeeDTO> getEmployeePage(Pageable pageable) {
        return customRepo.findEmployeeDetails(pageable);
    }
}
