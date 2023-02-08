package com.example.todo.projectapi.dto.response;

import com.example.todo.projectapi.dto.request.UserIdNameRqDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UserIdNameListRsDto {

    private String errorMsg;
    private List<UserIdNameRqDto> list = new ArrayList<>();
}
