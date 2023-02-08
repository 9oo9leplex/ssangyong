package com.example.todo.projectapi.exceptions;

public class ProjectNotFoundException  extends RuntimeException {
    public ProjectNotFoundException(String msg) {
        super(msg);
    }
}
