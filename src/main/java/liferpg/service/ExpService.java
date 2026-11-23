package liferpg.service;

import liferpg.model.User;

public class ExpService {

	public static final int EASY = 1;
	public static final int NORMAL = 2;
	public static final int HARD = 3;
	
	public static final int MAX_LEVEL = 100;
	public static final int NEED_EXP = 100;

    // 난이도별 EXP (쉬움 10 / 보통 20 / 어려움 40)
	public int getExpByDifficulty(int difficulty) {
	    switch (difficulty) {
	        case EASY:   return 10;
	        case NORMAL: return 20;
	        case HARD:   return 40;
	        default: throw new IllegalArgumentException("알 수 없는 난이도: " + difficulty);
	    }
	}

    // 현재 레벨에서 다음 레벨까지 필요한 EXP (100x현재 레벨) 반환
    public int getRequiredExp(int level) {
        return NEED_EXP * level;
    }

    // EXP를 더한 뒤 레벨업일시 true 반환
    public boolean addExp(User user, int gained) {
    	// 만렙 달성 시 레벨업 없음
    	if(user.getLevel() == MAX_LEVEL) {
    		return false;
        }
    	
        user.setExp(user.getExp() + gained);
        boolean leveledUp = false;

        while (user.getExp() >= getRequiredExp(user.getLevel())) {
            user.setExp(user.getExp() - getRequiredExp(user.getLevel()));
            user.setLevel(user.getLevel() + 1);
            leveledUp = true;

            // 레벨이 만렙을 넘었을 경우 만렙으로 보정, EXP 최대치로 고정 후 레벨업 종료
            if(user.getLevel() >= MAX_LEVEL) {
            	user.setLevel(MAX_LEVEL);
            	break;
            }
        }
         
        return leveledUp;
    }
}