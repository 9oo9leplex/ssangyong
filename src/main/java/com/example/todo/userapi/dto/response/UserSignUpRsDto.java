package com.example.todo.userapi.dto.response;

import com.example.todo.userapi.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

// 회원가입 완료후 클라이언트게 응답할 데이터를 담는 객체
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "email")
public class UserSignUpRsDto {

    private String email;
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDate;

    // 엔터티를 dto로 변경하는 생성자
    public UserSignUpRsDto(UserEntity entity) {
        this.email = entity.getEmail();
        this.userName = entity.getUserName();
        this.joinDate = entity.getJoinDate();
    }
}
