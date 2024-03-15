package com.jdbctemplate.employee;

import com.jdbctemplate.employee.dao.EmployeeDao;
import com.jdbctemplate.employee.entity.Employee;
import com.jdbctemplate.employee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeApplicationTests {

	@Autowired
	EmployeeService service;
	@MockBean
	EmployeeDao dao;

	@BeforeEach
	void setup()
	{
		Employee employee = new Employee(4,"maharaja",8758643346L,"maha@gmail.com",55000.0);
		Mockito.when(dao.findEmployeeById(4)).thenReturn(employee);
	}

	@Test
	public void  testFindEmployeeById()
	{
		String employeeName = "maharaja";
		Employee employee = service.findEmployeeById(4);
		assertEquals(employeeName,employee.getEmployeeName());
	}

	@Test
	public void testSaveEmployee() {
		Employee employee = new Employee(5, "John", 1234567890L, "john@example.com", 60000.0);
		service.save(employee);
		verify(dao, times(1)).save(employee);
	}

	@Test
	public void testUpdateEmployee() {
		int employeeId = 4;
		Employee updatedEmployee = new Employee(employeeId, "Updated maharaja", 5763238963L, "updated@gmail.com", 65000.0);
		service.update(updatedEmployee, employeeId);
		verify(dao, times(1)).updateEmployee(updatedEmployee, employeeId);
	}

	@Test
	public void testDeleteEmployee() {
		int employeeId = 4;
		service.deleteEmployee(employeeId);
		verify(dao, times(1)).deleteEmployee(employeeId);
	}

	@Test
	public void testFindAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, "John", 1234567890L, "john@example.com", 60000.0));
		employees.add(new Employee(2, "Alice", 9876543210L, "alice@example.com", 70000.0));
		Mockito.when(dao.findAll()).thenReturn(employees);

		List<Employee> retrievedEmployees = service.findAll();
		assertEquals(2, retrievedEmployees.size());
	}

}
