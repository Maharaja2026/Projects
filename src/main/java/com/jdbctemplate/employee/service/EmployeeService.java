package com.jdbctemplate.employee.service;

import com.jdbctemplate.employee.config.AES256TextEncrypter;
import com.jdbctemplate.employee.dao.EmployeeDao;
import com.jdbctemplate.employee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService
{
    @Autowired
    EmployeeDao employeeDao;

//    private final AES256TextEncrypter encrypter;
//
//    public EmployeeService(@Value("${myapp.encryption.key}") String encryptionKey) {
//        this.encrypter = new AES256TextEncrypter(encryptionKey);
//    }

//    -------------------------------------------------------------------------
    public void save(Employee employee) {
        employeeDao.save(employee);
    }
//
//    public Employee findEmployeeById(int employeeId)
//    {
//        return employeeDao.findEmployeeById(employeeId);
//    }
//    public List<Employee> findAll()
//    {
//        return employeeDao.findAll();
//    }
//
    public void update(Employee employee,int employeeId)
    {
        employeeDao.updateEmployee(employee,employeeId);
    }
//
//    public void deleteEmployee(int employeeId)
//    {
//        employeeDao.deleteEmployee(employeeId);
//    }

    //    -----------------------------------------------------------------------


    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    public void saveEmployee(String employeeName, long employeeContact, String employeeEmail, double employeeSalary) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeName);
        employee.setEmployeeContact(employeeContact);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedEmail = encoder.encode(employeeEmail);
        employee.setEmployeeEmail(encodedEmail);
        employee.setEmployeeSalary(employeeSalary);
        employeeDao.save(employee);
    }

    public Employee findEmployeeById(int employeeId) {
        return employeeDao.findEmployeeById(employeeId);
    }

    public void updateEmployee(int employeeId, String employeeName, long employeeContact, String employeeEmail, double employeeSalary) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeName(employeeName);
        employee.setEmployeeContact(employeeContact);
        employee.setEmployeeEmail(employeeEmail);
        employee.setEmployeeSalary(employeeSalary);

        employeeDao.updateEmployee(employee, employeeId);
    }

    public void deleteEmployee(int employeeId) {
        employeeDao.deleteEmployee(employeeId);
    }

    public Employee AuthenticateEmployee(Employee employee)
    {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        Employee emp = employeeDao.findEmployeeById(employee.getEmployeeId());
        if(emp != null) {
            if (bCrypt.matches(employee.getEmployeeEmail(), emp.getEmployeeEmail())) {
                return emp;
            }
        }
        return null;
    }


//    public String encryptText(String text) throws Exception {
//        return encrypter.encrypt(text);
//    }
//
//    public String decryptText(String encryptedText) throws Exception {
//        return encrypter.decrypt(encryptedText);
//    }

}
