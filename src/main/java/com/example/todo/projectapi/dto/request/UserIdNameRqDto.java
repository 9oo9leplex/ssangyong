package com.example.todo.projectapi.dto.request;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserIdNameRqDto {
    private String userId;
    private String userName;
}
