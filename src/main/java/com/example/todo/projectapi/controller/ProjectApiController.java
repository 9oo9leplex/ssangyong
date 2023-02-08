package com.example.todo.projectapi.controller;

import com.example.todo.projectapi.dto.request.ProjectCreateRqDto;
import com.example.todo.projectapi.dto.response.ProjectListRsDto;
import com.example.todo.projectapi.dto.response.UserIdNameListRsDto;
import com.example.todo.projectapi.service.ProjectService;
import com.example.todo.userapi.entity.UserProjectEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectApiController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<?> retrieveProjectList(
            // 인증완료처리시 등록했던 값을 넣어줌
            @AuthenticationPrincipal String userId
    ) {
        log.info("/api/projects Get retrive() method!! - {}", userId);
        List<UserProjectEntity> userList = projectService.getCurrentUserProjectList(userId);
        ProjectListRsDto rsDto = projectService.getCurrentUserProjectInfo(userId);

        // 예외 처리
        // 참여 중인 프로젝트가 없다고 에러는 아님
        // httpstatus ok
        // errorMsg 가 있다면 그것을 출력, 없다면 리스트 출력
        return ResponseEntity.ok().body(rsDto);

    }

    @PostMapping
    public ResponseEntity<?> createProject(
            @AuthenticationPrincipal String userId,
            @Validated @RequestBody ProjectCreateRqDto rqDto,
            BindingResult result
    ){

        if(result.hasErrors()){
            log.warn("createProject - createDto 검증 에러 발생: {}", result.getFieldError());
            return ResponseEntity
                    .badRequest()
                    .body(result.getFieldError());
        }

        try {
        // TODO: 2023.2.8. Project Controller create 작업
        // create 메서드 내에서 return 하도록
        // rqDto userIdList 필드는 service 단에서 1개씩 db 넣기
//            ProjectListRsDto rsDto = projectService.create(rqDto);
            ProjectListRsDto rsDto = null;
            return ResponseEntity
                    .ok()
                    .body(rsDto);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(ProjectListRsDto.builder().errorMsg(e.getMessage()));
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<?> retrieveProject(
            @AuthenticationPrincipal String userId,
            @PathVariable String projectId
    ) {

        try {

//            ProjectListRsDto rsDto = projectService.findById(projectId);
            ProjectListRsDto rsDto = null;
            return ResponseEntity
                    .ok()
                    .body(rsDto);

        } catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/userlist")
    public ResponseEntity<?> retrieveAllUser(
        @AuthenticationPrincipal String userId
    ){
        log.info("project controller retrieve all user method");
        try {
            // TODO: 전체 유저 조회 메서드 구현
//            List<UserIdNameListRsDto> list = projectService.retrieveAllUser();
            List<UserIdNameListRsDto> list = null;
            return ResponseEntity
                    .ok()
                    .body(list);

        } catch(RuntimeException e){
            log.error(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(UserIdNameListRsDto.builder().errorMsg(e.getMessage()).build());
        }


    }

}



























