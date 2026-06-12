package com.example.os_project;

import java.util.Date;

public class Quest {
    private int id;
    private String title;
    private Date dueDate;
    private int difficulty;
    private String status;

    public Quest(int id, String title, Date dueDate, int difficulty, String status) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.difficulty = difficulty;
        this.status = status;
    }

    public String getDetails() {
        String diffStr = (difficulty == 3) ? "상" : (difficulty == 2) ? "중" : "하";
        return "과제명: " + title + " [난이도: " + diffStr + "] (마감: " + dueDate.toString() + ")";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}