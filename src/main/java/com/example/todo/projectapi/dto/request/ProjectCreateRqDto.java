package com.example.todo.projectapi.dto.request;

import com.example.todo.projectapi.entity.ProjectEntity;
import lombok.*;

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
    private List<UserIdNameRqDto> userList = new ArrayList<>();

    public ProjectEntity toEntity(){
        return ProjectEntity.builder()
                .userId(this.userId)
                .title(this.projectTitle)
                .contents(this.projectContent)
                .build();
    }
}
