package com.ecms.employee.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecms.employee.entity.Employee;
import com.ecms.employee.exception.EmployeeNotFoundException;
import com.ecms.employee.repository.EmployeeRepository;
import com.ecms.employee.service.EmployeeService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository repo;
	
	private static final String EMPLOYEE_SERVICEIMPL = "employeeServiceImpl";


	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}
	
	@Override
	public Employee saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return repo.save(employee);
	}
	

	@Override
	@Retry(name = "getEmployee", fallbackMethod = "getEmployeeFallback")
	@CircuitBreaker(name = EMPLOYEE_SERVICEIMPL, fallbackMethod = "getEmployeeFallback")
	public Employee getEmployeeById(Long id) {
		// TODO Auto-generated method stub
		log.info("Trying to fetch employee...");
		return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found"));
	}
	
	public Employee getEmployeeFallback(Long id, EmployeeNotFoundException exception) {
//		System.out.println("Fallback triggered due to: " + exception.getMessage());
		log.info("Fallback trigger due to: " + exception.getMessage());
		return new Employee();
	}
}
