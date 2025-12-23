package com.menotifilho.taskmanager.controller;

import com.menotifilho.taskmanager.dto.TaskResponseDTO;
import com.menotifilho.taskmanager.model.Task;
import com.menotifilho.taskmanager.model.TaskRepository;
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
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> taskList = taskService.findAll();
        return ResponseEntity.ok(taskList);
    }

    @PostMapping
    public ResponseEntity<Task> newTask(@RequestBody Task task){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            throw new IllegalStateException();
        }
        var user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(taskService.createTask(task,user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.findById(id));
    }

}
