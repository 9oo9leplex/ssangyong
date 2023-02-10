package com.example.todo.projectapi.service;

import com.example.todo.projectapi.dto.request.ProjectCreateRqDto;
import com.example.todo.projectapi.dto.request.UserIdNameEmailRqDto;
import com.example.todo.projectapi.dto.response.ProjectInfoRsDto;
import com.example.todo.projectapi.dto.response.ProjectListRsDto;
import com.example.todo.projectapi.entity.ProjectEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {


    @Autowired
    ProjectService projectService;


    @Test
    @DisplayName("유저 엔터티로 프로젝트 조회")
    @Transactional
    @Rollback
    void test() {

        ProjectListRsDto resDto = projectService.getCurrentUserProjectInfo("402880af86068cce0186068cd7640002");

        assertEquals("project_title",resDto.getList().get(0).getProjectTitle());


    }


    @Test
    @DisplayName("프로젝트 아이디로 모든 세부사항 조회, DTO 생성, 반환")
//    @Transactional
//    @Rollback
    void test2() {

        List<UserIdNameEmailRqDto> members = new ArrayList<>();
        UserIdNameEmailRqDto user1 = UserIdNameEmailRqDto.builder()
                .userId("402880af85f0876c0185f095d1280002")
                .userName("userName1")
                .email("test@naver.com")
                .build();
        UserIdNameEmailRqDto user2 = UserIdNameEmailRqDto.builder()
                .userId("402880af86068cce0186068cd7640002")
                .userName("userName2")
                .email("test2@naver.com")
                .build();
        members.add(user1);
        members.add(user2);



        ProjectCreateRqDto projectCreateDTO = ProjectCreateRqDto.builder()
                .projectTitle("t1")
//                .userId("402880af85f0876c0185f095d1280002")
                .projectContent("c1")
                .userList(members)
                .build();

        ProjectEntity newProject = projectCreateDTO.toEntity();

//        ProjectListRsDto project = projectService.createProject(projectCreateDTO);


        int a=1;


    }

}