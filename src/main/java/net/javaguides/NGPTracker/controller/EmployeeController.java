package net.javaguides.NGPTracker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.NGPTracker.exceptions.ResourcesNotFoundException;
import net.javaguides.NGPTracker.model.Employee;
import net.javaguides.NGPTracker.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
//@RequestMapping(value = "/api/v1/", method = {RequestMethod.GET, RequestMethod.POST})

public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	//to find the email id exist or not
	
	//get all list of employee
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	// create employee rest api
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		String tempEmailId = employee.getEmailId();
		Employee employeeEmail = employeeRepository.findByEmailId(tempEmailId);
		if(employeeEmail !=null) {
			throw  new ResourcesNotFoundException(" The emailid :"+ tempEmailId+" is already exist");
			}
		
		return employeeRepository.save(employee);
		
		}
		
	//get employee by id rest api
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("Employee not exist with id :"+ id));
		return ResponseEntity.ok(employee);
	}
	
	//Update Rest api
	@CrossOrigin(origins="http://localhost:4200")
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("Employee not exist with id :"+ id));
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
		Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}
	//Delete employee rest API
	@DeleteMapping("/employees/{id}")
	@CrossOrigin(origins="http://localhost:4200")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourcesNotFoundException("Employee not exist with id :"+ id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/employees/login")
	@CrossOrigin(origins="http://localhost:4200")
	public Employee loginEmployee(@RequestBody Employee employee) {
		String tempEmailId = employee.getEmailId();
		Employee employeeEmail = employeeRepository.findByEmailId(tempEmailId);
		if(employeeEmail !=null) {
			return employeeRepository.findByEmailId(tempEmailId);
			
			}
		
		throw  new ResourcesNotFoundException(" The emailid :"+ tempEmailId+" is doesn't exist");
		
		}
}
