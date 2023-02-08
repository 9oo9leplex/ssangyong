package com.example.todo.logapi.dto.request;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class LogModifyRqDto {

    private String logId;
    private boolean logStatus;
    private String projectId;
}
