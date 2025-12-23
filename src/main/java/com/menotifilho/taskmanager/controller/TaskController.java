package com.menotifilho.taskmanager.controller;

import com.menotifilho.taskmanager.dto.TaskResponseDTO;
import com.menotifilho.taskmanager.model.Task;
import com.menotifilho.taskmanager.model.User;
import com.menotifilho.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> taskList = taskService.findAll();
        return ResponseEntity.ok(taskList);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> newTask(@RequestBody Task task){
        var user =  getLoggedUser();

        return ResponseEntity.ok(taskService.createTask(task,user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody Task task){
        var user =  getLoggedUser();

        return ResponseEntity.ok(taskService.updateTask(id,user,task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id, getLoggedUser());
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.findById(id));
    }

    private User getLoggedUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            throw new IllegalStateException();
        }
        return (User) authentication.getPrincipal();
    }
}
