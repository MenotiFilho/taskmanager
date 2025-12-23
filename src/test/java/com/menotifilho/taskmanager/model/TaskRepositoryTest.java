package com.menotifilho.taskmanager.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve salvar uma tarefa vinculada a um usuário")
    void shouldSaveTaskWithUser(){
        //cenario
        User user = new User();
        user.setUsername("menoti");
        user.setPassword("123456");
        User savedUser = userRepository.save(user);

        Task task = new Task();
        task.setTitle("teste");
        task.setDescription("teste description");
        task.setUser(savedUser);

        //acao
        Task savedTask = taskRepository.save(task);

        //verificacao
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getUser()).isNotNull();
        assertThat(savedTask.getUser().getId()).isEqualTo(savedUser.getId());
    }



}
