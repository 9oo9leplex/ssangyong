package com.example.todo.logapi.dto.request;


import com.example.todo.logapi.entity.LogEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class LogCreateRqDto {

    private String projectId;
    private String logTitle;
    private String logContent;
    private String todoId;

    public LogEntity toEntity(){
        return LogEntity.builder()
                .title(this.logTitle)
                .contents(this.logContent)
                .todoId(this.todoId)
                .createDate(LocalDateTime.now())
                .done(false)
                .build();
    }
}
