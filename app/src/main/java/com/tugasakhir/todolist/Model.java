package com.tugasakhir.todolist;

public class Model {
    int todoId;
    String title, description;
    Model (int todoId, String title, String description) {
        this.todoId = todoId;
        this.title = title;
        this.description = description;
    }

    public int getTodoId() {;
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
