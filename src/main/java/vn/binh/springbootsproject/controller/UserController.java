package vn.binh.springbootsproject.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.binh.springbootsproject.entity.UserInfo;
import vn.binh.springbootsproject.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


}
