package com.demo.crud_op.controller;


import com.demo.crud_op.dto.EmployeeDTO;
import com.demo.crud_op.exception.DuplicateEmployeeException;
import com.demo.crud_op.exception.EmployeeNotFoundException;
import com.demo.crud_op.model.Employee;
import com.demo.crud_op.repo.CustomRepo;
import com.demo.crud_op.repo.EmployeeRepo;
import com.demo.crud_op.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {
@Autowired
private EmployeeService service;
@Autowired
EmployeeRepo repo;

@Autowired
CustomRepo customRepo;
@GetMapping("/employees")
public ResponseEntity<List<Employee>>getAllEmployees()
{
return new ResponseEntity<>(service.getAllEmployees(), HttpStatus.OK);
}

@GetMapping("employees/details")
public ResponseEntity<List<EmployeeDTO>>getEmp()
{
    return new ResponseEntity<>(service.getEmpDetails(),HttpStatus.OK);
}


@GetMapping("/employee-page")
public ResponseEntity<Page<EmployeeDTO>> getEmployeePage(Pageable pageable ){
        Page<EmployeeDTO> employeeDTOPage = service.getEmployeePage(pageable);
        return ResponseEntity.ok(employeeDTOPage);
    }


@GetMapping("/employee/{id}")
    public ResponseEntity<Employee> fetchById(@PathVariable int id){
    Employee emp = repo.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));

    return new ResponseEntity<>(emp, HttpStatus.OK);

}

@PostMapping("/addEmployee")
    public ResponseEntity<Employee>addEmployee(@Valid @RequestBody Employee emp){
    if(repo.existsByEmail(emp.getEmail()))
    {
        throw new DuplicateEmployeeException("employee with the email "+emp.getEmail()+" alreadyExists" );
    }
 Employee saveEmp=repo.save(emp);
return new ResponseEntity<>(saveEmp,HttpStatus.CREATED);
}

@PutMapping("/updateEmp/{id}")
    public ResponseEntity<Employee> updateEmp(@PathVariable int id,@Valid @RequestBody Employee empDetails)
{
     Optional<Employee>empData=repo.findById(id);
      if(empData.isPresent()) {
          Employee emp = empData.get();
          emp.setFirstName(empDetails.getFirstName());
          emp.setLastName(empDetails.getLastName());
          emp.setJobTitle(empDetails.getJobTitle());
          emp.setMobile(empDetails.getMobile());
          emp.setEmail(empDetails.getEmail());
          Employee updatedEmp = repo.save(emp);
          return new ResponseEntity<>(updatedEmp, HttpStatus.OK);
      }
else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
}

@DeleteMapping("/removeEmp/{id}")
public ResponseEntity<String>deleteEmpById(@PathVariable int id)
{
    if(repo.existsById(id)){
 repo.deleteById(id);
 return new ResponseEntity<>("Employee Deleted",HttpStatus.OK);
    }
    else {
    throw new EmployeeNotFoundException("employee with id "+ id+ " not found");
    }

}



}
