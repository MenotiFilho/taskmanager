package com.menotifilho.taskmanager.service;

import com.menotifilho.taskmanager.dto.TaskResponseDTO;
import com.menotifilho.taskmanager.model.Task;
import com.menotifilho.taskmanager.model.TaskRepository;
import com.menotifilho.taskmanager.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Deve retornar lista de DTOs")
    void findAllCase1() {
        //Arrange
        //Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("usuario_teste");
        Task t1 = new Task();
        t1.setId(1L);
        t1.setTitle("Task 1");
        t1.setUser(user);

        when(taskRepository.findAll()).thenReturn(List.of(t1));

        //Action
        List<TaskResponseDTO> result = taskService.findAll();

        //Assert
        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals("Task 1", result.get(0).title());
    }

    @Test
    @DisplayName("Deve lançar erro quando ID não existe")
    void findByIdCaseError() {
        //Setup
        Long idInexistente = 99L;
        when(taskRepository.findById(idInexistente)).thenReturn(Optional.empty());

        //Action + Assert
        assertThrows(ResponseStatusException.class, ()-> {
            taskService.findById(idInexistente);
        });
    }

    @Test
    @DisplayName("Deve retornar um DTO quando a tarefa existe")
    void findByIdSuccess() {
        //Setup
        User user = new User();
        user.setUsername("menoti");

        Task taskMock = new Task();
        taskMock.setId(10L);
        taskMock.setTitle("Tarefa Teste");
        taskMock.setUser(user);

        when(taskRepository.findById(10L)).thenReturn(Optional.of(taskMock));

        //Action
        TaskResponseDTO resultado = taskService.findById(10L);

        //Assert
        assertNotNull(resultado);
        assertEquals(10L, resultado.id());
        assertEquals("Tarefa Teste", resultado.title());
        assertEquals("menoti", resultado.username());
    }

    @Test
    @DisplayName("Deve Criar uma tarefa com sucesso vinculada ao usuário")
    void createTaskCase1() {
        //Setup
        User user = new User();
        user.setId(1L);
        user.setUsername("marcos");

        Task taskInput = new Task();
        taskInput.setTitle("Estudar Java");

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });

        //Action
        TaskResponseDTO createdTask = taskService.createTask(taskInput, user);

        //Assert
        assertNotNull(createdTask);
        assertEquals(1L,createdTask.id());
        assertEquals("Estudar Java", createdTask.title());
        assertEquals(user.getUsername(),createdTask.username());
        verify(taskRepository,times(1)).save(any());
    }
}