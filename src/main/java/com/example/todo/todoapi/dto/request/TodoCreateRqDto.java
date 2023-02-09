package com.example.todo.todoapi.dto.request;

import com.example.todo.todoapi.entity.TodoEntity;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class TodoCreateRqDto {

    @NotBlank
    @Size(min = 2, max = 10)
    private String title;
    private String content;
    private String projectId;
    private String userId;


    // 이 dto를 엔터티로 변환
    public TodoEntity toEntity() {
        return TodoEntity.builder()
                .userId(this.userId)
                .title(this.title)
                .contents(this.content)
                .projectId(this.projectId)
                .logs(new ArrayList<>())
                .build();
    }

}
