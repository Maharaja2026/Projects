package com.jdbctemplate.employee.service;

import com.jdbctemplate.employee.dao.ManagerDao;
import com.jdbctemplate.employee.entity.Employee;
import com.jdbctemplate.employee.entity.Manager;
import com.jdbctemplate.employee.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ManagerService
{
    @Autowired
    ManagerDao dao;

    @Autowired
    JwtUtils jwtUtils;

    public void signupManager(String managerName,String managerEmail,String managerPassword)
    {
        Manager manager = new Manager();
        manager.setManagerName(managerName);
        manager.setManagerEmail(managerEmail);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPwd = encoder.encode(managerPassword);
        manager.setManagerPassword(encodedPwd);
        dao.saveManager(manager);
    }

    public Manager saveManager(Manager manager)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPwd = encoder.encode(manager.getManagerPassword());
        manager.setManagerPassword(encodedPwd);
        dao.saveManager(manager);
        return manager;
    }

    public ResponseEntity<Map<String, Object>> loginManager(String managerEmail, String managerPassword)
    {
        Manager exManager = dao.findManagerByEmail(managerEmail);

        if (exManager != null)
        {
            if(exManager.getManagerPassword().equals(managerPassword))
            {
                String token = jwtUtils.generateJwt(exManager);

                Map<String, Object> data = new HashMap<>();
                data.put("accessToken", token);

                return ResponseEntity.ok(data);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid credentials"));
    }

    public ResponseEntity<Manager> findManagerById(int managerId) {
        Manager manager = dao.findManagerById(managerId);

        if (manager != null) {
            return ResponseEntity.ok(manager);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public Manager AuthenticateManager(Manager manager)
    {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        Manager exManager = dao.findManagerById(manager.getManagerId());
        if(exManager != null) {
            if (bCrypt.matches(manager.getManagerPassword(),exManager.getManagerPassword()))
            {
                return exManager;
            }
        }
        return null;
    }
    private String encryptPassword(String password)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPwd = encoder.encode(password);
        return encodedPwd;
    }
    public Boolean AuthenticateManagerPassword(String managerPassword)
    {
        List<Manager> managers = dao.findAll();
        for(Manager manager : managers)
        {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            Boolean x = encoder.matches(managerPassword,manager.getManagerPassword());
            if(x == true)
            {
                return x;
            }
        }
        return false;
    }

    public Manager findManagerByEmail(String managerEmail)
    {
        return dao.findManagerByEmail(managerEmail);
    }
}
