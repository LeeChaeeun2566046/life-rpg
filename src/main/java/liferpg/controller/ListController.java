package liferpg.controller;

import liferpg.model.Ability;
import liferpg.model.User;
import liferpg.service.ExpService;

public class ListController {

    private final User user;
    private final ExpService expService;

    public ListController(User user, ExpService expService) {
        this.user = user;
        this.expService = expService;
    }


    public int previewExp(String difficultyText) {
        return expService.getExpByDifficulty(toDifficulty(difficultyText));
    }

    public boolean completeQuest(String categoryText, String difficultyText) {
        int gained = expService.getExpByDifficulty(toDifficulty(difficultyText));
        boolean leveledUp = expService.addExp(user, gained);

        String ability = toAbility(categoryText);
        if (ability != null) {
            user.getAbilityPoints().merge(ability, 1, Integer::sum);
        }
        user.setCompletedCount(user.getCompletedCount() + 1);
        return leveledUp;
    }

    private int toDifficulty(String text) {
        switch (text) {
            case "쉬움":   return ExpService.EASY;
            case "보통":
            case "중간":   return ExpService.NORMAL;   
            case "어려움": return ExpService.HARD;
            default: throw new IllegalArgumentException("알 수 없는 난이도: " + text);
        }
    }

    private String toAbility(String category) {
        switch (category) {
            case "공부":     return Ability.INTELLIGENCE;
            case "운동":     return Ability.STAMINA;
            case "자기관리": return Ability.CLEANLINESS;
            default: return null;
        }
    }
}
