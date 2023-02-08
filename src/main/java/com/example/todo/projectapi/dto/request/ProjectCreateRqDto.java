package com.example.todo.projectapi.dto.request;

import com.example.todo.projectapi.entity.ProjectEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private List<String> userIdList = new ArrayList<>();

    public ProjectEntity toEntity(){
        return ProjectEntity.builder()
                .title(projectTitle)
                .contents(projectContent)
                .build();
    }
}
