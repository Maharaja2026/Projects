package com.jdbctemplate.employee.dao;

import com.jdbctemplate.employee.entity.Employee;
import com.jdbctemplate.employee.util.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDao
{
    @Autowired
   JdbcTemplate jdbcTemplate;

    public void save(Employee employee) {
        String sql = "insert into employee (employee_name, employee_contact, employee_email, employee_salary) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, employee.getEmployeeName(), employee.getEmployeeContact(), employee.getEmployeeEmail(),employee.getEmployeeSalary());
    }

    public Employee findEmployeeById(int employeeId)
    {
        String query = "select * from employee where employee_id=?";
        return jdbcTemplate.queryForObject(query,new Object[]{employeeId},new EmployeeRowMapper());
    }

    public List<Employee> findAll() {
        String sql = "select * from employee";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    public void updateEmployee(Employee employee,int employeeId)
    {
        String query = "update employee set employee_name=?, employee_contact=?, employee_email=?, employee_salary=? where employee_id=?";
        System.out.println("Executing SQL: " + query);
        System.out.println("Parameters: " + employee.getEmployeeName() + ", " + employee.getEmployeeContact() + ", " + employee.getEmployeeEmail() + ", " + employee.getEmployeeSalary() + ", " + employeeId);
        jdbcTemplate.update(query, employee.getEmployeeName(), employee.getEmployeeContact(), employee.getEmployeeEmail(), employee.getEmployeeSalary(), employeeId);
    }

    public void deleteEmployee(int employeeId)
    {
        String query = "delete from employee where employee_id=?";
        jdbcTemplate.update(query,employeeId);
    }
}

