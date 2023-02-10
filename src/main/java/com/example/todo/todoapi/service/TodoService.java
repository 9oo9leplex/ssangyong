package com.example.todo.todoapi.service;

import com.example.todo.projectapi.entity.ProjectEntity;
import com.example.todo.projectapi.repository.ProjectRepository;
import com.example.todo.todoapi.dto.request.TodoCreateRqDto;
import com.example.todo.todoapi.dto.request.TodoModifyRqDto;
import com.example.todo.todoapi.dto.response.TodoDetailRsDto;
import com.example.todo.todoapi.dto.response.TodoListRsDto;
import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.todoapi.repository.TodoRepository;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    private final UserRepository userRepository;

    private final ProjectRepository projectRepository;


    // 할 일 목록 조회
    @Transactional
    public TodoListRsDto retrieve(String userId) {
        List<TodoEntity> entityList = todoRepository.findByUserId(userId);

        List<TodoDetailRsDto> dtoList = entityList.stream()
                .map(TodoDetailRsDto::new)
                .collect(Collectors.toList());

        return TodoListRsDto.builder()
                .todos(dtoList)
                .build();
    }

    // 할 일 등록
    public void create(
            final TodoCreateRqDto createRequestDTO ,String userId
    )
        throws RuntimeException
    {
        TodoEntity todo = createRequestDTO.toEntity();

        UserEntity userEntity = userRepository.findById(userId).orElseThrow();
        todo.setUser(userEntity);
        todo.setUserId(userEntity.getId());
        todoRepository.save(todo);

        ProjectEntity projectEntity = projectRepository.findById(createRequestDTO.getProjectId()).orElseThrow();
        List<TodoEntity> todos = projectEntity.getTodos();
        if(todos== null){
            todos = new ArrayList<>();
        }
        todos.add(todo);
        projectEntity.setTodos(todos);
        projectRepository.save(projectEntity);


        log.info("할 일이 저장되었습니다. 제목 : {}", createRequestDTO.getTitle());
    }

    // 할 일 수정 (제목, 할일 완료여부)
    public TodoListRsDto update(
            final String id,
            final TodoModifyRqDto modifyRequestDTO,
            final String userId
    ) {
        Optional<TodoEntity> targetEntity = todoRepository.findById(id);

        targetEntity.ifPresent(entity -> {
            entity.setTitle(modifyRequestDTO.getTitle());
//            entity.setDone(modifyRequestDTO.isDone());

            todoRepository.save(entity);
        });

        return retrieve(userId);
    }

    // 할 일 삭제
    public TodoListRsDto delete(final String id, final String userId) {


        try {
            todoRepository.deleteById(id);
        } catch (Exception e) {
            log.error("id가 존재하지 않아 삭제에 실패했습니다. - ID: {}, err: {}"
                                    , id, e.getMessage());
            throw new RuntimeException("id가 존재하지 않아 삭제에 실패했습니다.");
        }
        return retrieve(userId);
    }



}











