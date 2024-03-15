package com.jdbctemplate.employee.controller;

import com.jdbctemplate.employee.entity.Employee;
import com.jdbctemplate.employee.service.EmployeeService;
import com.jdbctemplate.employee.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;
import java.util.List;

//@RestController
@Controller
@RequestMapping("/employee")
public class EmployeeController
{

    @Autowired
    EmployeeService employeeService;

    @Autowired
    JwtUtils jwtUtils;

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
public String home(Model model) {
    List<Employee> employees = employeeService.findAll();
    model.addAttribute("employees", employees);
    return "home";
}

//    @GetMapping("/home")
//    public String home(@CookieValue(name = "jwtToken", required = false) String jwtToken, Model model) {
//        if (jwtToken != null) {
//            try {
//                jwtUtils.verify(jwtToken);
//                List<Employee> employees = employeeService.findAll();
//                model.addAttribute("employees", employees);
//                // Proceed with processing the home page
//                return "home";
//            } catch (ExpiredJwtException e) {
//                // Token verification failed, redirect to login page
//                return "redirect:/manager/loginform";
//            } catch (Exception e) {
//                // Other exceptions, handle appropriately
//                return "redirect:/manager/loginform";
//            }
//        } else {
//            // Token not present, redirect to login page
//            return "redirect:/manager/loginform";
//        }
//    }
    @GetMapping("/add")
    public String addEmployeeForm() {
        return "add";
    }

    @PostMapping("/save")
    public String saveEmployee(@RequestParam String employeeName, @RequestParam long employeeContact, @RequestParam String employeeEmail, @RequestParam double employeeSalary) {
        employeeService.saveEmployee(employeeName, employeeContact, employeeEmail, employeeSalary);
        return "redirect:/employee/home";
    }

    @GetMapping("/edit")
    public String editEmployeeForm(Model model, @RequestParam int employeeId) throws Exception {
        Employee employee = employeeService.findEmployeeById(employeeId);
      //  employee.setEmployeeEmail(employeeService.decryptText(employee.getEmployeeEmail()));
        if(employee != null)
        {
            model.addAttribute("employee", employee);
            return "edit";
        }
        return "redirect:/employee/home";
    }

    @PostMapping("/update")
    public String editEmployee(@RequestParam int employeeId, @RequestParam String employeeName, @RequestParam long employeeContact, @RequestParam String employeeEmail, @RequestParam double employeeSalary) {
        employeeService.updateEmployee(employeeId, employeeName, employeeContact, employeeEmail, employeeSalary);
        return "redirect:/employee/home";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int employeeId) {
        employeeService.deleteEmployee(employeeId);
        return "redirect:/employee/home";
    }
}

