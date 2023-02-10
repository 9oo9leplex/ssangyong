package com.example.todo.logapi.service;

import com.example.todo.logapi.controller.LogController;
import com.example.todo.logapi.dto.request.LogCreateRqDto;
import com.example.todo.logapi.repositroy.LogRepository;
import com.example.todo.projectapi.controller.ProjectApiController;
import com.example.todo.projectapi.dto.request.ProjectCreateRqDto;
import com.example.todo.projectapi.dto.response.ProjectInfoRsDto;
import com.example.todo.projectapi.dto.response.ProjectListRsDto;
import com.example.todo.projectapi.entity.ProjectEntity;
import com.example.todo.projectapi.repository.ProjectRepository;
import com.example.todo.projectapi.service.ProjectService;
import com.example.todo.todoapi.controller.TodoApiController;
import com.example.todo.todoapi.dto.request.TodoCreateRqDto;
import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.todoapi.repository.TodoRepository;
import com.example.todo.todoapi.service.TodoService;
import com.example.todo.userapi.controller.UserApiController;
import com.example.todo.userapi.dto.request.LoginRqDto;
import com.example.todo.userapi.dto.request.UserSignUpRqDto;
import com.example.todo.userapi.dto.response.LoginRsDto;
import com.example.todo.userapi.dto.response.UserSignUpRsDto;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.repository.UserProjectRepository;
import com.example.todo.userapi.repository.UserRepository;
import com.example.todo.userapi.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
//import org.springframework.test.annotation.Rollback;

import java.beans.PropertyEditor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@SpringBootTest
@Commit
class LogServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    LogRepository logRepository;

//    @Autowired
//    ProjectService projectService;
//    @Autowired
//    TodoService todoService;
//    @Autowired
//    LogService logService;

    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TodoService todoService;
    @Autowired
    LogService logService;

    @Autowired
    LogController logController;

    @Test
    @DisplayName("새로운 로그를 등록하면 등록된다.")
//    @Rollback
    @Transactional
    void createTest(){

        UserSignUpRqDto userSignUpRqDto = UserSignUpRqDto.builder()
                .email("abc@naver.com")
                .userName("연창모")
                .password("123qwe!@#")
                .build();

        UserSignUpRsDto userSignUpRsDto = userService.create(userSignUpRqDto);

        LoginRqDto loginRqDto = LoginRqDto.builder()
                .email("abc@naver.com")
                .password("123qwe!@#")
                .build();

        LoginRsDto loginRsDto = userService.getByCredentials("abc@naver.com","123qwe!@#");

        UserEntity userEntity = userRepository.findByEmail("abc@naver.com");


        ProjectCreateRqDto projectCreateRqDto = ProjectCreateRqDto.builder()
                .projectTitle("pjt001")
                .projectContent("pjtContent")
//                .userId(userEntity.getId())
                .userList(new ArrayList<>())
                .build();

        ProjectListRsDto projectListRsDto = projectService.createProject(projectCreateRqDto, userEntity.getId());

        Optional<ProjectEntity> projectEntity = projectRepository.findById(projectListRsDto.getList().get(0).getProjectId());

        TodoCreateRqDto todoCreateRqDto = TodoCreateRqDto.builder()
                .title("todoTitle001")
                .content("todoContent001")
                .projectId(projectEntity.get().getProjectId())
                .build();

        todoService.create(todoCreateRqDto, userEntity.getId());

        TodoCreateRqDto todoCreateRqDto2 = TodoCreateRqDto.builder()
                .title("todoTitle002")
                .content("todoContent002")
                .projectId(projectEntity.get().getProjectId())
                .build();

        todoService.create(todoCreateRqDto2, userEntity.getId());

        List<TodoEntity> todoEntity = projectEntity.get().getTodos();

        LogCreateRqDto logCreateRqDto = LogCreateRqDto.builder()
                .logTitle("logTitle001")
                .logContent("logContent001")
                .todoId(todoEntity.get(0).getTodoId())
                .projectId(projectEntity.get().getProjectId())
                .build();

        logService.create(logCreateRqDto, userEntity.getId());

        LogCreateRqDto logCreateRqDto2 = LogCreateRqDto.builder()
                .logTitle("logTitle002")
                .logContent("logContent002")
                .todoId(todoEntity.get(0).getTodoId())
                .projectId(projectEntity.get().getProjectId())
                .build();

        logService.create(logCreateRqDto2, userEntity.getId());



        ProjectInfoRsDto projectInfoRsDto = projectService.getProjectDetails(projectEntity.get().getProjectId());
    }
}