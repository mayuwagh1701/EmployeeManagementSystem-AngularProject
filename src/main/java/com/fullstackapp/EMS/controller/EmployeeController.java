package com.fullstackapp.EMS.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.fullstackapp.EMS.entity.Employee;
import com.fullstackapp.EMS.exception.ResourceNotFoundException;
import com.fullstackapp.EMS.repository.EmployeeRepository;

@RestController // this annotations tell to the spring container this class is request handler
				// class
@CrossOrigin("http://localhost:4200")
public class EmployeeController {

	@Autowired
	private EmployeeRepository erepo;

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {

		return erepo.findAll();

	}

	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {

		return erepo.save(employee);

	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {

		Employee employee = erepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id" + id));

		return ResponseEntity.ok(employee);

	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employeeDetails) {

		Employee employee = erepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id" + id));

		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastname(employeeDetails.getLastname());
		employee.setSalery(employeeDetails.getSalery());
		erepo.save(employee);

		return ResponseEntity.ok(employee);

	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable int id,
			@RequestBody Employee employeeDetails) {

		Employee employee = erepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id" + id));

		erepo.delete(employee);
		Map<String, Boolean> response = new HashMap<String,Boolean>();
		response.put("delete", Boolean.TRUE);
		return ResponseEntity.ok(response);

	}
}
