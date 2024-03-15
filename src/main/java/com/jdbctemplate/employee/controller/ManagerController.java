package com.jdbctemplate.employee.controller;


import com.jdbctemplate.employee.entity.Manager;
import com.jdbctemplate.employee.service.ManagerService;
import com.jdbctemplate.employee.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//@RestController
@Controller
@RequestMapping("/manager")
public class ManagerController
{
    @Autowired
    ManagerService service;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/index")
    public String indexPage()
    {
        return "index";
    }

    @GetMapping("/signupform")
    public String managerSignupForm() {
        return "signup";
    }

    @GetMapping("/loginform")
    public String managerLoginForm() {
        return "login";
    }

    @PostMapping("/signup")
    public String signUp(String managerName,String managerEmail,String managerPassword)
    {
        service.signupManager(managerName,managerEmail,managerPassword);
        return "redirect:/manager/index";
    }


//    @PostMapping("/login")
//    public String login(@RequestParam String managerEmail, @RequestParam String managerPassword, Model model, HttpServletResponse response) {
//        ResponseEntity<Map<String, Object>> loginResponse = service.loginManager(managerEmail, managerPassword);
//
//        if (loginResponse.getStatusCode().is2xxSuccessful()) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Authorization", "Bearer " + loginResponse.getBody().get("accessToken"));
//
//            Cookie jwtCookie = new Cookie("jwtToken", (String) loginResponse.getBody().get("accessToken"));
//            jwtCookie.setMaxAge(60);
//            jwtCookie.setHttpOnly(true);
//            jwtCookie.setSecure(true);
//            response.addCookie(jwtCookie);
//
//            return "redirect:/employee/home";
//        } else {
//            model.addAttribute("error", loginResponse.getBody().get("error"));
//            return "redirect:/manager/loginform";
//        }
//    }

//    @PostMapping("/login")
//    public String login(@RequestParam String managerEmail,@RequestParam String managerPassword, Model model) {
//        ResponseEntity<Map<String, Object>> response = service.loginManager(managerEmail, managerPassword);
//        if (response.getStatusCode().is2xxSuccessful()) {
//            String accessToken = response.getBody().get("accessToken").toString();
//            try {
//                jwtUtils.verify(accessToken);
//                return "redirect:/employee/home";
//            } catch (SignatureException e) {
//                return "redirect:/manager/loginform";
//            } catch (ExpiredJwtException e) {
//                return "redirect:/manager/loginform";
//            } catch (Exception e) {
//                return "redirect:/manager/loginform";
//            }
//        }
//        return "redirect:/manager/loginform";
//    }
       // --------------------------------------------------
//        ResponseEntity<String> response = service.loginManager(managerEmail,managerPassword);
//        if(response != null)
//        {
//         //   return "redirect:/employee/home";
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Authorization", "Bearer " + response.getBody()); // Set the token in the "Authorization" header
//            return new ResponseEntity<>(headers, HttpStatus.OK);
//        }
//        return "redirect:/manager/loginform";

        //-------------------------------------------------------

//    @PostMapping("/login")
//    public ResponseEntity<Map<String,Object>> login(@RequestParam String managerEmail,@RequestParam String managerPassword, Model model)
//    {
//        ResponseEntity<Map<String,Object>> response = service.loginManager(managerEmail, managerPassword);
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Authorization", "Bearer " + response.getBody());
//            return new ResponseEntity<>(response.getBody() , headers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(response.getBody(), response.getStatusCode());
//        }
//    }

    @GetMapping("/privateApi")
    public ResponseEntity privateApi(@RequestHeader(value = "authorization",defaultValue = "123") String auth) throws Exception {
     //   String authorization = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhYmNAZ21haWwuY29tIiwiaWF0IjoxNzEwMjYyMzU3LCJuYW1lIjoiYWJjIiwiZW1haWwiOiJhYmNAZ21haWwuY29tIn0.zVmbbx2FDxfSwAQuRFktcxy8fIuYKCKpl9IHvQK6Q33aRH7bEsKpjk3hYJS4ElOLftB5d-0si9N1YO8JDHFC1A";
        try {
            jwtUtils.verify(auth);
            return ResponseEntity.status(200).body("This is valid Token");
        } catch (SignatureException e) {
            return ResponseEntity.status(401).body("Invalid token signature");
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(401).body("Token has expired");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
    @GetMapping("/logout")
    public String logout()
    {
        return "redirect:/manager/loginform";
    }

    @PostMapping
    public ResponseEntity<Manager> saveManager(@RequestBody Manager manager)
    {
        Manager savedManager = service.saveManager(manager);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedManager);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String managerEmail,@RequestParam String managerPassword)
    {
        Boolean isAuthenticate = service.AuthenticateManagerPassword(managerPassword);
        System.out.println(isAuthenticate);

        if(isAuthenticate == true)
        {
            return ResponseEntity.status(HttpStatus.OK).body("Login Sucess..!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Login Failed..!");
    }

}
