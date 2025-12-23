package com.menotifilho.taskmanager.service;

import com.menotifilho.taskmanager.dto.TaskResponseDTO;
import com.menotifilho.taskmanager.model.Task;
import com.menotifilho.taskmanager.model.TaskRepository;
import com.menotifilho.taskmanager.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskResponseDTO> findAll() {
        List<TaskResponseDTO> taskList = taskRepository.findAll().stream().map(task -> new TaskResponseDTO(task)).toList();
        return taskList;
    }

    public TaskResponseDTO findById(Long id){
        TaskResponseDTO task = new TaskResponseDTO(taskRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada!")));
        return task;
    }

    @Transactional
    public TaskResponseDTO createTask(Task task, User user) {

        task.setUser(user);

        return new TaskResponseDTO(taskRepository.save(task));
    }

    @Transactional
    public TaskResponseDTO updateTask(Long id, User user, Task task){

        Task taskToUpdate = taskRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"));

        if (!(user.getId().equals(taskToUpdate.getUser().getId()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para alterar esta tarefa!");
        }

        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());
        taskToUpdate.setStatus(task.getStatus());

        return new TaskResponseDTO(taskRepository.save(taskToUpdate));
    }

    @Transactional
    public void deleteTask(Long id, User user) {
        Task taskToDelete = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"));

        if (!(user.getId().equals(taskToDelete.getUser().getId()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para alterar esta tarefa!");
        }

        taskRepository.delete(taskToDelete);
    }

}
