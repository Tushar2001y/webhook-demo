package com.example.webhookSrc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webhookSrc.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findByFirstName(String firstName);
	List<Employee> findByLastName(String lastName);
//	List<Employee> getAllEmployees();

}
