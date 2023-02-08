package com.example.todo.logapi.repositroy;

import com.example.todo.logapi.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity, String> {
}
