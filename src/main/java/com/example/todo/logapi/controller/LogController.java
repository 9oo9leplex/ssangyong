package com.example.todo.logapi.controller;

import com.example.todo.logapi.dto.request.LogCreateRqDto;
import com.example.todo.projectapi.dto.response.ProjectInfoRsDto;
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

//    private final LogService logService;
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

        ProjectInfoRsDto rsDto;
        try {
            // TODO: 2023.2.8. Log Controller create 작업
            // log create 시 return void
            // insert 예외 발생 시 catch로 빠짐
//            logService.create(rqDto, userId);
            rsDto = projectService.getProjectDetails(rqDto.getProjectId());

            return ResponseEntity
                    .ok()
                    .body(rsDto);
        } catch (RuntimeException e){
            log.warn(e.getMessage());

            rsDto = projectService.getProjectDetails(rqDto.getProjectId());
            rsDto.setErrorMsg(e.getMessage());

            return ResponseEntity
                    .internalServerError()
                    .body(rsDto);
        }
    }

}
