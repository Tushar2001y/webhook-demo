package com.example.webhook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webhook.entities.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findByFirstName(String firstName);
	List<Employee> findByLastName(String lastName);
//	List<Employee> getAllEmployees();
@Query("FROM Employee u WHERE u.firstName=:firstName AND u.lastName=:lastName")
Employee getEmployeeByEmployee_name(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
