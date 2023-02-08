package com.example.todo.userapi.repository;

import com.example.todo.projectapi.entity.ProjectEntity;
import com.example.todo.userapi.entity.UserEntity;
import com.example.todo.userapi.entity.UserProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProjectRepository extends JpaRepository<UserProjectEntity, String> {
    List<UserProjectEntity> findByUser(UserEntity user);

    List<UserProjectEntity> findByProject(ProjectEntity project);

}
