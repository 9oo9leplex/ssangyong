package com.example.todo.projectapi.entity;


import com.example.todo.logapi.entity.LogEntity;
import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.entity.UserProjectEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString(exclude = "todos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "projectId")
@Builder

@Entity
@Table(name = "tbl_project")
public class ProjectEntity {


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String projectId;

    private String title;

    private String contents;

    private boolean done;


    @CreationTimestamp
    private LocalDateTime createDate; // 등록 시간

    @ManyToOne(fetch = FetchType.LAZY)
    // 연관관계 설정은 했지만 INSERT, UPDATE시에는 이 객체를 활용하지 않겠다.
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    // 할 일 추가, 수정시 사용할 외래키
    @Column(name = "user_id")
    private String userId;

    @OneToMany(mappedBy = "project")
    private List<UserProjectEntity> userProjects = new ArrayList<>();

    @OneToMany(mappedBy = "project") // 상대방 엔터티에 조인되는 필드명(상대방의)을 씀
    private List<TodoEntity> todos = new ArrayList<>();




}
