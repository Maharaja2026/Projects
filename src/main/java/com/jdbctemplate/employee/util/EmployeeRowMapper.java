package com.jdbctemplate.employee.util;

import com.jdbctemplate.employee.entity.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee>
{
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("employee_id"));
        employee.setEmployeeName(rs.getString("employee_name"));
        employee.setEmployeeContact(rs.getLong("employee_contact"));
        employee.setEmployeeEmail(rs.getString("employee_email"));
        employee.setEmployeeSalary(rs.getDouble("employee_salary"));
        return employee;
    }
}
