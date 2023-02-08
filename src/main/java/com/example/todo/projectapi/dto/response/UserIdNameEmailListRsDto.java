package com.example.todo.projectapi.dto.response;

import com.example.todo.projectapi.dto.request.UserIdNameEmailRqDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserIdNameEmailListRsDto {

    private String errorMsg;
    private List<UserIdNameEmailRqDto> list = new ArrayList<>();
}
