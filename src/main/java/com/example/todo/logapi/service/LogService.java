package com.example.todo.logapi.service;

import com.example.todo.logapi.dto.request.LogCreateRqDto;
import com.example.todo.logapi.dto.request.LogModifyRqDto;
import com.example.todo.logapi.entity.LogEntity;
import com.example.todo.logapi.repositroy.LogRepository;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final UserRepository userRepository;

    public void create(
            final LogCreateRqDto rqDto,
            final String userId
    ) throws  RuntimeException
    {

        LogEntity entity = rqDto.toEntity();
//        entity.setUserId(userId);
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        entity.setUser(userEntity);
        entity.setUserId(userEntity.getId());


        logRepository.save(entity);
        log.info("새로운 로그가 저장되었습니다. {}", entity);
    }

    public void update(
            final LogModifyRqDto rqDto
    ) throws RuntimeException
    {

        Optional<LogEntity> entity = logRepository.findById(rqDto.getLogId());

        entity.ifPresent(e -> {
            e.setDone(!e.isDone());

            logRepository.save(e);
        });
    }
}


// project, todo, log -> 파라미터
// parent_id
// id