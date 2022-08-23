package com.example.webhook.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webhook.entities.Employee;
import com.example.webhook.repository.EmployeeRepository;


@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

	@Autowired
	EmployeeRepository employeeRepository;

	@PostMapping
	//at http://localhost:8181/api/webhook equivalent given by ngrok as <some-ngrok-link>/api/webhook
	public ResponseEntity<Employee> print(@RequestBody Employee requestBodyEmployee) {

		Optional<Employee> empl = Optional.ofNullable(employeeRepository.getEmployeeByEmployee_name(requestBodyEmployee.getFirstName(), requestBodyEmployee.getLastName()));
		assert empl != null;
		if (empl.isEmpty()) {

			Employee addEmployee = employeeRepository.save(new Employee(requestBodyEmployee.getFirstName(), requestBodyEmployee.getLastName()));
			System.out.println("--- from webhook ---\n" + addEmployee.toString());
			return new ResponseEntity<>(addEmployee, HttpStatus.OK);
		}
		else{
			System.out.println("the record already exists");
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
}
	//for testing
//	@GetMapping("/employee")
//	public ResponseEntity<List<Employee>> getAllEmployee(@RequestParam(required = false) String firstName){
//		List<Employee> employeeList = employeeRepository.findAll();
//		return new ResponseEntity<>(employeeList, HttpStatus.OK);
//	}
	
	//for testing
//		@RequestMapping("/")
//		public String getAllEmployee(Model model){
//			model.addAttribute("employees", employeeRepository.findAll());
//			return "employees";
//		}
	
	
	
	


