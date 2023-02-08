package com.example.todo.todoapi.dto.response;

import com.example.todo.logapi.dto.response.LogRsDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class TodoRsDto {


    private String todoId;

    private String title;

    private boolean done;

    private LocalDateTime createDate;
    private String userName;

    private List<LogRsDto> logs;
}
