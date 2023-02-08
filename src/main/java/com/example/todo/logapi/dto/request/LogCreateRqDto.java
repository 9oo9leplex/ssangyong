package com.example.todo.logapi.dto.request;


import com.example.todo.logapi.entity.LogEntity;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class LogCreateRqDto {

    private String logTitle;
    private String logContent;
    private String todoId;

    public LogEntity toEntity(){
        return LogEntity.builder()
                .title(logTitle)
                .contents(logContent)
                .todoId(todoId)
                .build();
    }
}
