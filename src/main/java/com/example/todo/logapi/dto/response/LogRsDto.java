package com.example.todo.logapi.dto.response;

import lombok.*;

import java.time.LocalDateTime;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class LogRsDto {


    private String logId;

    private String title;

    private String contents;

    private boolean done;

    private LocalDateTime createDate; // 등록 시간


    private String userName;


}
