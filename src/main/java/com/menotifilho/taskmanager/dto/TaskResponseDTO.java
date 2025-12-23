package com.menotifilho.taskmanager.dto;

import com.menotifilho.taskmanager.model.Task;

public record TaskResponseDTO(Long id, String title, String description, Boolean status, String username) {
    public TaskResponseDTO(Task task){
        this(task.getId(), task.getTitle(), task.getDescription(), task.getStatus(),task.getUser().getUsername());
    }
}
