package com.example.todo.userapi.repository;

import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.entity.UserProjectEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserProjectRepositoryTest {

    @Autowired
    UserProjectRepository userProjectRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 엔터티로 프로젝트 조회")
    @Transactional
    @Rollback
    void test() {

        UserEntity user = userRepository.findById("402880af86068cce0186068cd7640002").orElseThrow();

        List<UserProjectEntity> byUser = userProjectRepository.findByUser(user);

        int a=1;


    }


}