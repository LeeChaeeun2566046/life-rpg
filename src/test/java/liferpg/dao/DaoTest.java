package liferpg.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import liferpg.connector.DatabaseConnector;
import liferpg.model.Quest;
import liferpg.model.QuestSet;
import liferpg.model.User;

public class DaoTest {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        DatabaseConnector dc = new DatabaseConnector();
        QuestDao questDao = new QuestDao(dc);
        QuestsetDao questsetDao = new QuestsetDao(dc);
        UserDao userDao = new UserDao(dc);
        
        System.out.println(questDao.findDifficulty(1));
        System.out.println(questDao.findCategory(1));
        System.out.println(questDao.findQuestsetId(1));
        List<Quest> allQuests = questDao.findAll();
        
        System.out.println("=====전체 quest 목록=========");
        allQuests.forEach((Quest q) -> System.out.println(q.getTitle()));
        System.out.println("========================");
        
        List<Integer> questByQuestset = questDao.findAllByQuestsetId(1);
        questByQuestset.forEach((Integer questId) -> {
			try {
				System.out.println(questDao.findByQuestId(questId).getTitle());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        System.out.println("===========userId=1 사용자의 quest 목록============");
        List<Integer> usersQuest = questDao.findAllByUserId(1); 
        for (Integer questId : usersQuest) {
        	Quest quest = questDao.findByQuestId(questId);
        	System.out.println(quest.getTitle()+ " "+ quest.getCategory());
        }
        System.out.println("=======================");
        
        System.out.println("id=2인 quest의 title: "+questDao.findByQuestId(2).getTitle());
        
        
        List<QuestSet> allQuestsets = questsetDao.findAll();
        System.out.println("==============all questset start dates==============");
        for (QuestSet qs : allQuestsets) {
        	System.out.println(qs.getStartDate());
        }
         
        // questset에 title 필드를 추가해야 할 것 같아. 
        
        List<QuestSet> allCompleted = questsetDao.findCompleted(true);
        System.out.println("===========all questsets that are completed=============");
        for(QuestSet qs: allCompleted) {
        	System.out.println(qs.getRepeatNum()+" = "+qs.getCompletedNum());
        }
        
        List<Integer> usersQuestsets = questsetDao.findAllByUserId(1);
        System.out.println("==========all questsets of userId=1==============");
        System.out.println("questsetId | userId | startDate");
        
        for(Integer qsId: usersQuestsets) {
        	QuestSet qs = questsetDao.findById(qsId);	 
        	System.out.println(qs.getQuestsetId()+" |  "+qs.getUserId()+" | "+qs.getStartDate());
        }
        
        QuestSet qs = questsetDao.findById(2);
        System.out.println("=========questset whose id=2 ==========");
        System.out.println(qs.getStartDate()+" "+ qs.getQuestsetId());
        
        List<User> allUsers = userDao.findAll(); 
        System.out.println("==============all users================");
        for (User user: allUsers) {
        	System.out.println(user.getNickname());
        }
        
        User user1 = new User();
        user1.setNickname("samantha");
        user1.setExp(60);
        user1.setLevel(20);
        user1.setCompletedCount(4);
        userDao.insertUser(user1);
        
        userDao.updateExp(1, 1000);
        userDao.updateLevel(2, 400);

    }
}
