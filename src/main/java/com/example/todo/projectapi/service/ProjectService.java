package com.example.todo.projectapi.service;


import com.example.todo.logapi.dto.request.LogModifyRqDto;
import com.example.todo.logapi.dto.response.LogRsDto;
import com.example.todo.logapi.entity.LogEntity;
import com.example.todo.projectapi.dto.request.ProjectCreateRqDto;
import com.example.todo.projectapi.dto.request.UserIdNameEmailRqDto;
import com.example.todo.projectapi.dto.response.ProjectInfoRsDto;
import com.example.todo.projectapi.dto.response.ProjectDetailRsDto;
import com.example.todo.projectapi.dto.response.ProjectListRsDto;
import com.example.todo.projectapi.dto.response.UserIdNameEmailListRsDto;
import com.example.todo.projectapi.entity.ProjectEntity;
import com.example.todo.projectapi.exceptions.ProjectNotFoundException;
import com.example.todo.projectapi.repository.ProjectRepository;
import com.example.todo.todoapi.dto.response.TodoRsDto;
import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.entity.UserProjectEntity;
import com.example.todo.userapi.repository.UserProjectRepository;
import com.example.todo.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectService {

    private final UserProjectRepository userProjectRepository;
    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;

    // 유저 아이디로 프로젝트 리스트 조회
    // 조회된 프로젝트 아이디로 참여 유저 조회
    public List<UserProjectEntity> getCurrentUserProjectList(String userId){
        UserEntity currentUserEntity = userRepository.findById(userId).orElseThrow();
        List<UserProjectEntity> currentUserProjectList = userProjectRepository.findByUser(currentUserEntity);
        if(currentUserProjectList.isEmpty()){
            throw new ProjectNotFoundException("해당 유저로 조회되는 프로젝트가 없습니다.");
        }
        return currentUserProjectList;

    }
    public  List<ProjectEntity> getUserProjectList(List<UserProjectEntity> userProjectEntityList){
        List<ProjectEntity> userProjectList = new ArrayList<>();
        for(UserProjectEntity e : userProjectEntityList){
            userProjectList.add(e.getProject());
        }
        return userProjectList;
    }

    public ProjectListRsDto getCurrentUserProjectInfo(String userId){

        List<UserProjectEntity> userProjectEntityList = getCurrentUserProjectList(userId);

        List<ProjectEntity> userProjectList = getUserProjectList(userProjectEntityList);

        List<ProjectDetailRsDto> projectDetailList = new ArrayList<>();

        for(ProjectEntity e : userProjectList){

            List<UserIdNameEmailRqDto> list = new ArrayList<>();
            
            List<UserProjectEntity> entities = userProjectRepository.findByProject(e);
            for(UserProjectEntity entity :entities){

                list.add(
                        UserIdNameEmailRqDto.builder()
                                .userId(entity.getUser().getId())
                                .userName(entity.getUser().getUserName())
                                .email(entity.getUser().getEmail())
                                .build()
                );
            }

            UserIdNameEmailListRsDto members = UserIdNameEmailListRsDto.builder()
                    .list(list)
                    .build();

            ProjectDetailRsDto projectDetail = ProjectDetailRsDto.builder()
                .projectId(e.getProjectId())
                .projectTitle(e.getTitle())
                .done(e.isDone())
                .createDate(e.getCreateDate())
//                    .members(members)
                    .members(members)
                .memberCount(list.size())
                .build();
            projectDetailList.add(projectDetail);
        }

        String errorMsg = "";
        if(projectDetailList.isEmpty()) errorMsg = "no participating projects";

        return ProjectListRsDto.builder()
                .errorMsg(errorMsg)
                .list(projectDetailList)
                .build();

    }


    // 프로젝트 아이디를 던젔을때 모든 로그를 찍는 서비스 구축
    // 프로젝트 아이디를 던졌을때 모든 투두와 로그를 중첩리스트(맵) 형태로 리턴해주는 서비스 구축
    public ProjectInfoRsDto getProjectDetails(String projectId){
        ProjectEntity projectEntity = projectRepository.findById(projectId).orElseThrow();

//        List<String> members = new ArrayList<>();
        List<UserIdNameEmailRqDto> members = new ArrayList<>();

        List<UserProjectEntity> entities = userProjectRepository.findByProject(projectEntity);
        for(UserProjectEntity entity : entities){
//            members.add(entity.getUser().getUserName());
            members.add(UserIdNameEmailRqDto.builder()
                            .userId(entity.getId())
                            .userName(entity.getUser().getUserName())
                            .email(entity.getUser().getEmail())
                            .build());
        }

        UserIdNameEmailListRsDto userList = UserIdNameEmailListRsDto.builder()
                .list(members)
                .build();

        List<TodoRsDto> todos = new ArrayList<>();
        boolean todosStatus = true;
        if(projectEntity.getTodos().size() == 0) todosStatus = false;

        for(TodoEntity todoEntity : projectEntity.getTodos()){
            List<LogRsDto> logs = new ArrayList<>();
            boolean logsStatus= true;
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
                
                // logStatus 비교해서 false;
                logsStatus &= logEntity.isDone();
            }

            if(logs.size() == 0) logsStatus = false;

            TodoRsDto todoDto = TodoRsDto.builder()
                    .todoId(todoEntity.getTodoId())
                    .title(todoEntity.getTitle())
                    .content(todoEntity.getContents())
                    .createDate(todoEntity.getCreateDate())
//                    .done(todoEntity.isDone())
                    .done(logsStatus)
                    .userName(todoEntity.getUser().getUserName())
                    .logs(logs)
                    .build();
            todos.add(todoDto);

            todosStatus &= logsStatus;
        }

        // Todo: 프로젝트 상태 업데이트
//        if(todosStatus) update(project);
        if((projectEntity.isDone() && !todosStatus) || (!projectEntity.isDone() && todosStatus)) {
            projectEntity.setDone(!projectEntity.isDone());
            projectRepository.save(projectEntity);
        }

        ProjectInfoRsDto resultDto = ProjectInfoRsDto.builder()
                .projectId(projectEntity.getProjectId())
                .title(projectEntity.getTitle())
                .content(projectEntity.getContents())
                .done(projectEntity.isDone())
                .createDate(projectEntity.getCreateDate())
//                .members(members)
                .members(userList)
                .memberCount(members.size())
                .todos(todos)
                .build();

        log.info("********project info - {}",resultDto);

        return resultDto;
    }

    public ProjectListRsDto createProject(ProjectCreateRqDto projectCreateDTO, String userId){

        ProjectEntity newProject = projectCreateDTO.toEntity();
//        UserEntity user = userRepository.findById(projectCreateDTO.getUserId()).orElseThrow();
        UserEntity user = userRepository.findById(userId).orElseThrow();
        newProject.setUser(user);

        ProjectEntity save = projectRepository.save(newProject);

        // 조회 유저 없을시 예외처리 수행행함 // 멤버가 없을때,

        List<UserProjectEntity> userProjects = new ArrayList<>();

        for(UserIdNameEmailRqDto userIdDto : projectCreateDTO.getUserList()){

            //
            UserEntity member = userRepository.findById(userIdDto.getUserId()).orElseThrow();
            UserProjectEntity userProjectEntity = UserProjectEntity.builder()
                    .project(newProject)
                    .user(member)
                    .build();
            userProjectRepository.save(userProjectEntity);
            userProjects.add(userProjectEntity);
        }

        log.info("newProject - {}", newProject);
        log.info("user - {}", user);

        

//        return getCurrentUserProjectInfo(projectCreateDTO.getUserId());
        return getCurrentUserProjectInfo(userId);
    }

    public UserIdNameEmailListRsDto getUserNameEmailList(){

        List<UserEntity> entityList = userRepository.findAll();
        ArrayList<UserIdNameEmailRqDto> userIdNameEmailList = new ArrayList<>();
        for(UserEntity e : entityList){
            UserIdNameEmailRqDto item = UserIdNameEmailRqDto.builder()
                    .email(e.getEmail())
                    .userId(e.getId())
                    .userName(e.getUserName())
                    .build();
            userIdNameEmailList.add(item);
        }
        return UserIdNameEmailListRsDto.builder()
                .list(userIdNameEmailList)
                .build();
    }
}