package com.example.todo.projectapi.dto.response;

import lombok.*;

import java.util.List;



@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ProjectListRsDto {

    private String errorMsg;
    private List<ProjectDetailRsDto> list;
}
