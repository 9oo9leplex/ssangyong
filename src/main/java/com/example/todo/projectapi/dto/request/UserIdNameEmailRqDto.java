package com.example.todo.projectapi.dto.request;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserIdNameEmailRqDto {
    private String userId;
    private String userName;
    private String email;


}
