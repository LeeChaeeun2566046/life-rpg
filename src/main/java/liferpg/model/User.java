package liferpg.model;

import java.util.List;
import java.util.Map;

public class User {
    private int userId;
    private String nickname;
    private int exp;
    private int level;
    private int completedCount;
    
    private List<Integer> award_id_list;
    private List<Integer> quest_id_list;
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

    public List<Integer> getAward_id_list() { return award_id_list; }
    public void setAward_id_list(List<Integer> award_id_list) { this.award_id_list = award_id_list; }

    public List<Integer> getQuest_id_list() { return quest_id_list; }
    public void setQuest_id_list(List<Integer> quest_id_list) { this.quest_id_list = quest_id_list; }

    public Map<String, Integer> getAbilityPoints() { return abilityPoints; }
    public void setAbilityPoints(Map<String, Integer> abilityPoints) { this.abilityPoints = abilityPoints; }
}