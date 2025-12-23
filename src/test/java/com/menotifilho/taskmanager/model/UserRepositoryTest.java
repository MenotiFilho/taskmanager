package com.menotifilho.taskmanager.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve salvar e encontrar um usuario pelo username")
    void shouldSaveAndFindUser(){
        //cenario
        User user = new User();
        user.setUsername("menoti");
        user.setPassword("123456");

        //acao
        User savedUser = userRepository.save(user);

        //verificacao
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("menoti");

        User foundUser = userRepository.findByUsername("menoti");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo(savedUser.getId());

    }
}
