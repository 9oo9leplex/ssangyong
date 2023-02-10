package com.example.todo.projectapi.repository;

import com.example.todo.logapi.dto.response.LogRsDto;
import com.example.todo.logapi.entity.LogEntity;
import com.example.todo.projectapi.dto.response.ProjectInfoRsDto;
import com.example.todo.projectapi.entity.ProjectEntity;
import com.example.todo.todoapi.dto.response.TodoRsDto;
import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.todoapi.repository.TodoRepository;
import com.example.todo.userapi.entity.UserProjectEntity;
import com.example.todo.userapi.repository.UserProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TodoRepository todoRepository;


    @Autowired
    UserProjectRepository userProjectRepository;

    @Test
    @DisplayName("프로젝트 아이디로 하위의 모든 로그 조회")
    @Transactional
    void selectAllLogs(){
//
//        ProjectEntity dept
//                = projectRepository.findById("id1")
//                .orElseThrow();
//
//        List<TodoEntity> todos = dept.getTodos();
//
//
//        todos.forEach(System.out::println);



        TodoEntity todo
                = todoRepository.findById("todoid1")
                .orElseThrow();

        List<LogEntity> logs = todo.getLogs();


        logs.forEach(System.out::println);


    }



    @Test
    @DisplayName("프로젝트 아이디로 하위의 모든 로그 조회")
    @Transactional
    void test2(){
        ProjectEntity projectEntity = projectRepository.findById("id1").orElseThrow();

        List<String> members = new ArrayList<>();

        List<UserProjectEntity> entities = userProjectRepository.findByProject(projectEntity);
        for(UserProjectEntity entity : entities){
            members.add(entity.getUser().getUserName());
        }

        List<TodoRsDto> todos = new ArrayList<>();
        for(TodoEntity todoEntity : projectEntity.getTodos()){
            List<LogRsDto> logs = new ArrayList<>();
            for(LogEntity logEntity : todoEntity.getLogs()){
                LogRsDto logDto = LogRsDto.builder()
                        .logId(logEntity.getLogId())
                        .title(logEntity.getTitle())
                        .contents(logEntity.getContents())
                        .done(logEntity.isDone())
                        .createDate(logEntity.getCreateDate())
                        .userName(logEntity.getUser().getUserName())
                        .build();
                logs.add(logDto);
            }

            TodoRsDto todoDto = TodoRsDto.builder()
                    .todoId(todoEntity.getTodoId())
                    .title(todoEntity.getTitle())
                    .createDate(todoEntity.getCreateDate())
//                    .done(todoEntity.isDone())
                    .userName(todoEntity.getUser().getUserName())
                    .logs(logs)
                    .build();
            todos.add(todoDto);
        }

        ProjectInfoRsDto resultDto = ProjectInfoRsDto.builder()
                .projectId(projectEntity.getProjectId())
                .title(projectEntity.getTitle())
                .content(projectEntity.getContents())
                .done(projectEntity.isDone())
                .createDate(projectEntity.getCreateDate())
                .members(members)
                .memberCount(members.size())
                .todos(todos)
                .build();
        int a=1;


    }


    @Test
    @DisplayName("유저 아이디로 프로젝트 조회")
    @Transactional
    void test1(){
//

        List<ProjectEntity> projects = projectRepository.findByUserId("402880af86068cce0186068cd7640002");

        int a=1;

    }

}