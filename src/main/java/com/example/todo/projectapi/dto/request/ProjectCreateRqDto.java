package com.example.todo.projectapi.dto.request;

import com.example.todo.projectapi.entity.ProjectEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ProjectCreateRqDto {

    private String projectTitle;
    private String projectContent;
    private String userId;
    private List<UserIdNameEmailRqDto> userList = new ArrayList<>();

    public ProjectEntity toEntity(){
        return ProjectEntity.builder()
                .userId(this.userId)
                .title(this.projectTitle)
                .contents(this.projectContent)
                .done(false)
                .createDate(LocalDateTime.now())
                .build();
    }
}
