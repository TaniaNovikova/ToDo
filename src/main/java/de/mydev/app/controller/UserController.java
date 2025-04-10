package de.mydev.app.controller;


import de.mydev.app.model.*;
import de.mydev.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;


@RestController
//So to reach it endpoints can be api/user/**
@RequestMapping("api/user")
public class UserController
{
    @Autowired
    private IUserService userService;

    //Example: POST http://localhost:8080/api/user -data {user form...}
    @PostMapping
    public ResponseEntity<?> register(@RequestBody User user)
    {
        if (userService.findByUsername(user.getUsername()) != null)
        {
            //Username should be unique.
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // GET /api/user/login -> It should be same with security login path like described before.
    // Why logout -> After logout, Spring will redirect it to login path.
    @GetMapping("login")
    public ResponseEntity<?> loginAndLogout(HttpServletRequest request)
    {
        //Authentication info will be stored on request by Spring Security.
        Principal principal = request.getUserPrincipal();
        //principal.getName() = username
        if (principal == null || principal.getName() == null)
        {
            //Here is will be logout redirection also so consider it as 20. httpStatus -> No error
            return new ResponseEntity<>(HttpStatus.OK);
        }
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // We can reach Principal directly as parameter like HttpServletRequest.
    // POST /api/user/change/ROLE_USER
    @PostMapping("change/{role}")
    public ResponseEntity<?> changeRole(Principal principal, @PathVariable Role role)
    {
        User user = userService.changeRole(role, principal.getName());
        return ResponseEntity.ok(user);
    }
}
