package com.jdbctemplate.employee.service;

import com.jdbctemplate.employee.dao.EmployeeDao;
import com.jdbctemplate.employee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService
{
    @Autowired
    EmployeeDao employeeDao;

    public void save(Employee employee) {
        employeeDao.save(employee);
    }

    public Employee findEmployeeById(int employeeId)
    {
        return employeeDao.findEmployeeById(employeeId);
    }
    public List<Employee> findAll()
    {
        return employeeDao.findAll();
    }

    public void updateEmployee(Employee employee,int employeeId)
    {
        employeeDao.updateEmployee(employee,employeeId);
    }

    public void deleteEmployee(int employeeId)
    {
        employeeDao.deleteEmployee(employeeId);
    }


}
