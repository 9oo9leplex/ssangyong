package com.example.todo.todoapi.entity;

// 일정관리 프로그램

import com.example.todo.logapi.entity.LogEntity;
import com.example.todo.projectapi.entity.ProjectEntity;
import com.example.todo.userapi.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter @ToString(exclude = "logs")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "todoId")
@Builder

@Entity
@Table(name = "tbl_todo")
public class TodoEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String todoId;

    @Column(nullable = false, length = 30)
    private String title; // 제목

    @Column(nullable = false, length = 30)
    private String contents; // 내용

    private boolean done; // 일정 완료 여부

    @CreationTimestamp
    private LocalDateTime createDate; // 등록 시간

    // 회원과 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    // 연관관계 설정은 했지만 INSERT, UPDATE시에는 이 객체를 활용하지 않겠다.
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    // 할 일 추가, 수정시 사용할 외래키
    @Column(name = "user_id")
    private String userId;


    @ManyToOne(fetch = FetchType.LAZY)
    // 연관관계 설정은 했지만 INSERT, UPDATE시에는 이 객체를 활용하지 않겠다.
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private ProjectEntity project;

    // 할 일 추가, 수정시 사용할 외래키
    @Column(name = "project_id")
    private String projectId;

    @OneToMany(mappedBy = "todo") // 상대방 엔터티에 조인되는 필드명(상대방의)을 씀
    private List<LogEntity> logs = new ArrayList<>();

}
