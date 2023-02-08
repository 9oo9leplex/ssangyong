package com.example.todo.projectapi.repository;

import com.example.todo.projectapi.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, String> {

    List<ProjectEntity> findByUserId(String id);

//    @Override
//    @Query("select")
//    List<ProjectEntity> findById(String projectId);
}
