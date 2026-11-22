package liferpg.model;

import java.util.List;
import java.util.Map;

public class User {
    private int userId;
    private String nickname;
    private int exp;
    private int level;
    private int completedCount;
    
    private List<Integer> questsetIdList;
    private List<Integer> questIdList;
    private Map<String, Integer> abilityPoints;

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    
    public int getExp() { return exp; }
    public void setExp(int exp) { this.exp = exp; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getCompletedCount() { return completedCount; }
    public void setCompletedCount(int completedCount) { this.completedCount = completedCount; }

    public List<Integer> getQuestsetIdList() { return questsetIdList; }
    public void setAward_id_list(List<Integer> questSet_id_list) { this.questsetIdList = questsetIdList; }

    public List<Integer> getQuestIdList() { return questIdList; }
    public void setQuest_id_list(List<Integer> questIdList) { this.questIdList = questIdList; }

    public Map<String, Integer> getAbilityPoints() { return abilityPoints; }
    public void setAbilityPoints(Map<String, Integer> abilityPoints) { this.abilityPoints = abilityPoints; }
}