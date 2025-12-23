package com.menotifilho.taskmanager.controller;

import com.menotifilho.taskmanager.model.Task;
import com.menotifilho.taskmanager.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Deve retornar lista de tarefas e Status 200")
    void shouldReturnListOfTasks() throws Exception {
        //cenario
        Task task = new Task();
        task.setTitle("Estudar Testes");
        task.setDescription("Estudar Testes");
        task.setId(1L);

        Mockito.when(taskRepository.findAll()).thenReturn(List.of(task));

        //acao
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Estudar Testes"))
                .andExpect(jsonPath("$[0].status").value(false))
        ;
    }
}
