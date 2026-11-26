package liferpg.model;

import java.time.LocalDate;

public class Quest {
    private int questId;
    private int userId;
    private Integer questsetId;
    private String title;
    private String category;
    private int difficulty;
    private boolean completed;
    private LocalDate dueDate; 

    // Getters and Setters
    public int getQuestId() { return questId; }
    public void setQuestId(int questId) { this.questId = questId; }

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

    public Integer getQuestsetId() { return questsetId; }
    public void setQuestsetId(Integer questsetId) { this.questsetId = questsetId; }
}