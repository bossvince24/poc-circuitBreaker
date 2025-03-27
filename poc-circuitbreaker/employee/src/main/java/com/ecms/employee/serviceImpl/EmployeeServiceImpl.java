package com.ecms.employee.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecms.employee.entity.Employee;
import com.ecms.employee.exception.EmployeeNotFoundException;
import com.ecms.employee.repository.EmployeeRepository;
import com.ecms.employee.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository repo;


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
	public Employee getEmployeeById(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee Not Found"));
	}
}
