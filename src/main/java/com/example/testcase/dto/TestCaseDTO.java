package com.example.testcase.dto;

import lombok.Data;
import com.example.testcase.model.Priority;
import com.example.testcase.model.Status;

@Data
public class TestCaseDTO {
    private String title;
    private String description;
    private Status status;
    private Priority priority;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
