package liferpg.model;

import java.time.LocalDate;

public class Quest {
    private int id;
    private int userId;
    private String title;
    private String category;
    private int difficulty;
    private boolean completed;
    private LocalDate dueDate;
    private Integer awardId;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Integer getAwardId() { return awardId; }
    public void setAwardId(Integer awardId) { this.awardId = awardId; }
}