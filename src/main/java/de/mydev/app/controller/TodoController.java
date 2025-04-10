package de.mydev.app.controller;


import de.mydev.app.model.*;
import de.mydev.app.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
//Request will be start with api/todo/**
@RequestMapping("api/todo")
public class TodoController
{
    @Autowired
    private ITodoService todoService;

    // POST api/todo -data {todo object...}
    @PostMapping
    public ResponseEntity<?> createTodo(Principal principal, @RequestBody TodoItem todoItem)
    {
        return ResponseEntity.ok(todoService.save(todoItem, principal.getName()));
    }

    // PUT api/todo/1...
    @PutMapping("{todoId}")
    public ResponseEntity<?> updateTodo(@PathVariable Long todoId)
    {
        return ResponseEntity.ok(todoService.completeTask(todoId));
    }

    // GET api/todo/1...
    @GetMapping("{userId}")
    public ResponseEntity<?> getWaitingTasks(@PathVariable Long userId)
    {
        return ResponseEntity.ok(todoService.findWaitingList(userId));
    }
}
