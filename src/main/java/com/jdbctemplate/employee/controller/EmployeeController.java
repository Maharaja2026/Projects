package com.jdbctemplate.employee.controller;

import com.jdbctemplate.employee.entity.Employee;
import com.jdbctemplate.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Controller
@RequestMapping("/employee")
public class EmployeeController
{

    @Autowired
    EmployeeService employeeService;

//    @PostMapping
//    public void addEmployee(@RequestBody Employee employee) {
//        employeeService.save(employee);
//    }
//
//    @GetMapping
//    public Employee findEmployeeById(@RequestParam int employeeId)
//    {
//        return employeeService.findEmployeeById(employeeId);
//    }
//
//    @GetMapping("/findall")
//    public List<Employee> findAll()
//    {
//        return employeeService.findAll();
//    }
//
//    @PutMapping
//    public void updateEmployee(@RequestBody Employee employee,@RequestParam int employeeId)
//    {
//        employeeService.updateEmployee(employee,employeeId);
//    }
//
//    @DeleteMapping
//    public void deleteEmployee(@RequestParam int employeeId)
//    {
//        employeeService.deleteEmployee(employeeId);
//    }

    @GetMapping("/home")
    public String home(Model model)
    {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees",employees);
        return "home";
    }

    @GetMapping("/add")
    public String addEmployeeForm() {
        return "add";
    }

    @PostMapping("/save")
    public String saveEmployee(@RequestParam String employeeName,@RequestParam long employeeContact,@RequestParam String employeeEmail,@RequestParam double employeeSalary)
    {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeName);
        employee.setEmployeeContact(employeeContact);
        employee.setEmployeeEmail(employeeEmail);
        employee.setEmployeeSalary(employeeSalary);

        employeeService.save(employee);
        return "redirect:/employee/home";
    }

    @GetMapping("/edit")
    public String editEmployeeForm(Model model,@RequestParam int employeeId)
    {
        Employee employee = employeeService.findEmployeeById(employeeId);
        model.addAttribute("employee",employee);
        return "edit";
    }

    @PostMapping("/update")
    public String editEmployee(@RequestParam int employeeId,@RequestParam String employeeName,@RequestParam long employeeContact,@RequestParam String employeeEmail,@RequestParam double employeeSalary)
    {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeName(employeeName);
        employee.setEmployeeContact(employeeContact);
        employee.setEmployeeEmail(employeeEmail);
        employee.setEmployeeSalary(employeeSalary);

        employeeService.updateEmployee(employee,employee.getEmployeeId());
        return "redirect:/employee/home";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int employeeId)
    {
        employeeService.deleteEmployee(employeeId);
        return "redirect:/employee/home";
    }


}
