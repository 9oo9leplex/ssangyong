package com.example.todo.projectapi.dto.response;

import com.example.todo.todoapi.dto.response.TodoRsDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ProjectInfoRsDto {

    private String projectId;
    private String title;
    private String content;
    private boolean done;

    private LocalDateTime createDate;

//    private List<String> members;
    private UserIdNameEmailListRsDto members;
    private int memberCount;

    private List<TodoRsDto> todos;

    private String errorMsg;

}
