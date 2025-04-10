package de.mydev.app.controller;

import de.mydev.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
//api/admin/**
@RequestMapping("api/admin")
public class AdminController
{
    @Autowired
    private IUserService userService;

    // GET api/admin/all
    // reachable by just ADMIN
    @GetMapping("all")
    public ResponseEntity<?> getAllUsers(
    )
    {
        return ResponseEntity.ok(userService.findAllUsers());
    }
}
