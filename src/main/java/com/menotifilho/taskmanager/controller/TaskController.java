package com.menotifilho.taskmanager.controller;

import com.menotifilho.taskmanager.dto.TaskResponseDTO;
import com.menotifilho.taskmanager.model.Task;
import com.menotifilho.taskmanager.model.TaskRepository;
import com.menotifilho.taskmanager.model.User;
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

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> taskList = taskRepository.findAll().stream().map(task -> new TaskResponseDTO(task)).toList();
        return ResponseEntity.ok(taskList);
    }

    @PostMapping
    public ResponseEntity<Task> newTask(@RequestBody Task task){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (User) authentication.getPrincipal();

        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return taskRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

}
