package liferpg.controller;

import liferpg.model.Ability;
import liferpg.model.User;
import liferpg.service.ExpService;

public class MainController {
	private final User user;
	private final ExpService expService;

	public MainController(User user, ExpService expService) {
		this.user = user;
		this.expService = expService;
	}
	
	public String getNickname() {
		return user.getNickname();
	}
	
	public int getLevel() {
		return user.getLevel();
	}
	
	public int getExp() {
		return user.getExp();
	}
	
	public int getRequiredExp() {
		return expService.getRequiredExp(user.getLevel());
	}
	
	public int getAbilityPoints(String abilityName) {
	return user.getAbilityPoints().getOrDefault(abilityName, 0);
	}
	
	public int getIntelligence() {
		return getAbilityPoints(Ability.INTELLIGENCE); 
	}
	public int getStamina( ) { 
		return getAbilityPoints(Ability.STAMINA); 	
	}
	public int getCleanliness() {
		return getAbilityPoints(Ability.CLEANLINESS);
	}
}
