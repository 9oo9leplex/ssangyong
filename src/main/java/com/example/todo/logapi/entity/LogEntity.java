package com.example.todo.logapi.entity;

import com.example.todo.todoapi.entity.TodoEntity;
import com.example.todo.userapi.entity.UserEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


// todo table의 아이디를 참조해야함

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "logId")
@Builder

@Entity
@Table(name = "tbl_log")
public class LogEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String logId;

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

    @ManyToOne(fetch = FetchType.LAZY)
    // 연관관계 설정은 했지만 INSERT, UPDATE시에는 이 객체를 활용하지 않겠다.
    @JoinColumn(name = "todo_id", insertable = false, updatable = false)
    private TodoEntity todo;

    // 할 일 추가, 수정시 사용할 외래키
    @Column(name = "todo_id")
    private String todoId;

}
