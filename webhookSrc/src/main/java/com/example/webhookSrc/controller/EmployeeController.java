package com.example.webhookSrc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.webhookSrc.models.Employee;
import com.example.webhookSrc.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;
	
	private String externalUrl = "https://webhook.site/7a4e3cbf-8ab0-4a7f-9256-5f25e8155336/api/webhook";
	
	@GetMapping("/employee")
	public ResponseEntity<List<Employee>> getAllEmployee(@RequestParam(required = false) String firstName){
		List<Employee> employeeList = employeeRepository.findAll();
		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}
	
	@GetMapping("/byFirstName/{firstName}")
	public ResponseEntity<List<Employee>> getEmployeeByFirstName(@RequestParam(required = false) String firstName){
		List<Employee> employeeList = new ArrayList<>();
		if(firstName == null) {
			employeeRepository.findAll().forEach(employeeList::add);
		} else {
			employeeRepository.findByFirstName(firstName).forEach(employeeList::add);
		}
		if(employeeList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(employeeList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/byLastName/{lastName}")
	public ResponseEntity<List<Employee>> getEmployeeByLatName(@RequestParam(required = false) String lastName){
		List<Employee> employeeList = new ArrayList<>();
		if(lastName == null) {
			employeeRepository.findAll().forEach(employeeList::add);
		} else {
			employeeRepository.findByFirstName(lastName).forEach(employeeList::add);
		}
		if(employeeList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(employeeList, HttpStatus.OK);
		}
	}
	
	@PostMapping("/employee")
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
		Employee addEmployee = employeeRepository.save(new Employee(employee.getFirstName(), employee.getLastName()));
		externalCaller(employee);
		return new ResponseEntity<>(addEmployee, HttpStatus.CREATED);
	}
	
	public void externalCaller(Employee employee) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.postForObject(externalUrl, employee, Employee.class);
	}
}
