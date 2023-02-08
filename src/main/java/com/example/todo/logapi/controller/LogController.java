package com.example.todo.logapi.controller;

import com.example.todo.logapi.dto.request.LogCreateRqDto;
import com.example.todo.projectapi.dto.response.ProjectInfoRsDto;
import com.example.todo.projectapi.dto.response.ProjectListDTO;
import com.example.todo.projectapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createLog(
            @AuthenticationPrincipal String userId,
            @Validated @RequestBody LogCreateRqDto rqDto,
            BindingResult result
    ){

        if(result.hasErrors()){
            return ResponseEntity
                    .badRequest()
                    .body(result.hasErrors());
        }

        try {
            // 설계 상 todo, log 생성 시 project 정보를 다시 호출하게끔 했는데
            // 다른 서비스 메서드를 호출하는 것이 맞는지
            // 서비스 페이지를 통합해서 쓰는 것이 맞는지
            // 컨트롤러에서 각각 호출해서 사용하는 것이 맞는지

            ProjectInfoRsDto rsDto =

            boolean flag = logService.create(rqDto, userId);
            if(flag)

            return ResponseEntity
                    .ok()
                    .body(projectList);
        } catch (RuntimeException e){
            log.warn(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(ProjectListDTO.builder().error(e.getMessage()));
        }


        return null;
    }

}
