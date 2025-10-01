package vn.binh.springbootsproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import vn.binh.springbootsproject.entity.UserInfo;
import vn.binh.springbootsproject.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@EnableMethodSecurity
public class CustomerController {

    private final UserService userService;

    public CustomerController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    public String addUser(@RequestBody UserInfo user) {
        return userService.addUser(user);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello is guest");
    }
}
