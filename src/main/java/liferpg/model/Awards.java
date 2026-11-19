package liferpg.model;

import java.time.LocalDate;

public class Awards {
    private int id;
    private int userId;
    private int repeatNum;
    private int completedNum;
    private LocalDate startDate;
    private boolean consistent;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRepeatNum() { return repeatNum; }
    public void setRepeatNum(int repeatNum) { this.repeatNum = repeatNum; }

    public int getCompletedNum() { return completedNum; }
    public void setCompletedNum(int completedNum) { this.completedNum = completedNum; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public boolean isConsistent() { return consistent; }
    public void setConsistent(boolean consistent) { this.consistent = consistent; }
}