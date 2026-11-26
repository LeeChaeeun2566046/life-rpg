package liferpg.dao;

import liferpg.model.Quest;
import liferpg.connector.DatabaseConnector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import liferpg.connector.*;

public class QuestDao {
	
	private final DatabaseConnector connector;
	
	public QuestDao(DatabaseConnector connector) {
        this.connector = connector;
    } // end of constructor
	
	// 사용자가 새로운 Quest를 등록할 때 사용
	public void insertQuest(Quest quest) throws FileNotFoundException, IOException {
		String sql = "INSERT INTO quest (user_id, title, category, difficulty, completed, dueDate, questset_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
		// 아예 새로운 quest를 등록할 때, quest id 는 사용자가 설정하지 않고 시스템에서 자동 부여합니다 (Statement.RETURN_GENERATED_KEYS)
		
		try (Connection conn = connector.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
			
			// data binding
			pstmt.setInt(1, quest.getUserId());
			pstmt.setString(2, quest.getTitle());
			pstmt.setString(3, quest.getCategory());
			pstmt.setInt(4, quest.getDifficulty());
			pstmt.setBoolean(5, false);
			
            // local date -> date
			LocalDate ld = quest.getDueDate();
			Date d = java.sql.Date.valueOf(ld);
			pstmt.setDate(6, d);   			
			
			pstmt.setInt(7, quest.getQuestsetId());
			
			// execute update
			pstmt.executeUpdate();
			
			// 시스템에서 자동 부여한 quest id를 quest의 필드로 설정
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int questId = generatedKeys.getInt("quest_id");
					quest.setQuestId(questId);
					System.out.println(questId+ " quest가 추가되었습니다");
				}
			}// end of try
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public void questComplete(int questId) throws FileNotFoundException, IOException {
		String sql = "UPDATE quest SET completed=? WHERE quest_id=?";
		
		try (Connection conn = connector.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			// data binding
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, questId);
			
			// execute update
			int rows = pstmt.executeUpdate();
   		    
			if (rows > 0) {
   			    System.out.println("quest가 complete으로 수정되었습니다.");
   		    } else {
   			    System.out.println("해당 id의 quest가 존재하지 않습니다.");
   		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 새로 추가함
	public void updateQuestsetId(int questId, int questsetId) throws FileNotFoundException, IOException {
		String sql = "UPDATE quest SET questset_id=? WHERE quest_id=?";
		
		try (Connection conn = connector.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			// data binding
			pstmt.setInt(1, questsetId);
			pstmt.setInt(2, questId);
			
			// execute update
			int rows = pstmt.executeUpdate();
   		    
			if (rows > 0) {
   			    System.out.println("quest의 questset id가 수정되었습니다.");
   		    } else {
   			    System.out.println("해당 id의 quest가 존재하지 않습니다.");
   		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// 새로 추가함!! 이 함수의 리턴값을 User 객체가 필드로 소유한다.
	public List<Integer> findAllByUserId(int userId) throws FileNotFoundException, IOException { 
		/* 사용 예시
		 * 
		 * userId = 101
		 * List<Integer> questId = findAllByUser(userId)
		 * questId ---> {1, 3, 4, 9}
		 * 이 리스트의 값들을 for문으로 순회하면서 findById(questId)에 파라미터로 전달하면
		 * quest 객체들을 돌려받을 수 있다.
		 */
		
		/*
		 * List<Integer> 대신에 List<Quest> 를 돌려주는게 나을까? 
		 * 
		 */
		
 		List<Integer> questIdList = new ArrayList<>();  // return value
	    String sql = "SELECT quest_id FROM quest WHERE user_id = ?";
    	
	    try (Connection conn = connector.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql);) {
    		
	    	// data binding
	    	pstmt.setInt(1, userId);
	    	
	    	// execute query
	    	try (ResultSet rs = pstmt.executeQuery()) {  
	    		// user_id = 101 이라고 치자
	    		// rs 에는 user_id = 101 인 quest의 id가 쫘라라락 들어 있음
		    	while (rs.next()) {
	    			questIdList.add(rs.getInt("quest_id")); 
	    			// 사실상 column 이 한개라 getInt(1)이라 해도 무방하지만 직관적으로 알아볼수있게 column name 명시함
	    		}
	    	}
     	} catch (SQLException e) {
     		e.printStackTrace();
     	}    
	    return questIdList;
	}

	// 새로 추가함!!
	public List<Integer> findAllByQuestsetId(int questsetId) throws FileNotFoundException, IOException { 
		/* 사용 예시
		 * 
		 * questsetId = 101
		 * List<Integer> questsetId = findAllByAward(questsetId)
		 * questId ---> {1, 3, 4, 9}
		 * 이 리스트의 값들을 for문으로 순회하면서 findById(questId)에 파라미터로 전달하면
		 * quest 객체들을 돌려받을 수 있다.
		 */
		 
 		List<Integer> questsetIdList = new ArrayList<>();  // return value
	    String sql = "SELECT quest_id FROM quest WHERE questset_id = ?";
    	
	    try (Connection conn = connector.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql);) {
    		
	    	// data binding
	    	pstmt.setInt(1, questsetId);
	    	
	    	// execute query
	    	try (ResultSet rs = pstmt.executeQuery()) {  
	    		// questset_id = 101 이라고 치자
	    		// rs 에는 questset_id = 101 인 quest의 id가 쫘라라락 들어 있음
		    	while (rs.next()) {
	    			questsetIdList.add(rs.getInt("questset_id")); 
	    			// 사실상 column 이 한개라 getInt(1)이라 해도 무방하지만 직관적으로 알아볼수있게 column name 명시함
	    		}
	    	}
     	} catch (SQLException e) {
     		e.printStackTrace();
     	}    
	    return questsetIdList;
	}

	// Quest 목록 보여줄때 사용
	public List<Quest> findAll() throws FileNotFoundException, IOException {
	    List<Quest> questList = new ArrayList<>();
	    String sql = "SELECT * FROM quest";
	    
	    try (Connection conn = connector.getConnection();
	    		PreparedStatement pstmt = conn.prepareStatement(sql);){
	    	try (ResultSet rs = pstmt.executeQuery()) {
	    		while (rs.next()) {
	    			Quest q = new Quest();
	    			q.setUserId(rs.getInt("user_id"));
	    			q.setTitle(rs.getString("title"));
	    			q.setQuestId(rs.getInt("quest_id"));

	    			Date date = rs.getDate("due_date");
	    			q.setDueDate(LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault()));   
	    			
	    			q.setDifficulty(rs.getInt("difficulty"));
	    			q.setCompleted(rs.getBoolean("completed"));
	    			q.setCategory(rs.getString("category"));
	    			q.setQuestsetId(rs.getInt("questset_id"));
	    			questList.add(q);
	    		}
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    
	    return questList;
	}

	// 완료되지 않은 quest를 찾고 싶을 수도 있고, 완료된 quest를 찾고 싶을 수도 있어서 파라미터로 전달할 수 있도록 함	
	public List<Quest> findCompleted(boolean boo) throws FileNotFoundException, IOException {
	    List<Quest> questList = new ArrayList<>();
	    String sql = "SELECT * FROM quest WHERE completed = ?";
	    
	    try (Connection conn = connector.getConnection();
	    		PreparedStatement pstmt = conn.prepareStatement(sql);){
	    	
	    	// data binding
	    	pstmt.setBoolean(1, boo);
	    	
	    	// execute query
	    	try (ResultSet rs = pstmt.executeQuery()) {
	    		while (rs.next()) {
	    			Quest quest = new Quest();
	    			quest.setUserId(rs.getInt("user_id"));
	    			quest.setTitle(rs.getString("title"));
	    			quest.setQuestId(rs.getInt("quest_id"));
	    			
	    			// date -> local date
 	    			quest.setDueDate(rs.getDate("due_date").toLocalDate());   
	    			
	    			quest.setDifficulty(rs.getInt("difficulty"));
	    			quest.setCompleted(rs.getBoolean("completed"));
	    			quest.setCategory(rs.getString("category"));
	    			quest.setQuestsetId(rs.getInt("questset_id"));
	    			questList.add(quest);
	    		}
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    
	    return questList;
		
	}
	
	public Quest findById(int questId) throws FileNotFoundException, IOException {
		Quest quest = new Quest();
		String sql = "SELECT * FROM quest WHERE quest_id = ?";
		
		try (Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			
			// data binding
			pstmt.setInt(1, questId);
			
			// execute query
			try (ResultSet rs = pstmt.executeQuery()) {
				quest.setUserId(rs.getInt("user_id"));
    			quest.setTitle(rs.getString("title"));
    			quest.setQuestId(rs.getInt("quest_id"));
    			
    			// date -> local date
     			quest.setDueDate(rs.getDate("due_date").toLocalDate());    			
    			
    			quest.setDifficulty(rs.getInt("difficulty"));
    			quest.setCompleted(rs.getBoolean("completed"));
    			quest.setCategory(rs.getString("category"));
    			quest.setQuestsetId(rs.getInt("questset_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quest;
	}
	
	
	// 이거 모델에 getCategory()가 있으니까 없어도 될 것 같음
	// ...다시 생각해보니 필요할수도
	public String findCategory(int questId) throws FileNotFoundException, IOException {
	    String sql = "SELECT category FROM quest WHERE quest_id = ?";
	    String category = null;
	    try (Connection conn = connector.getConnection();
	    		PreparedStatement pstmt = conn.prepareStatement(sql)) {
	    	
	    	// data binding
	    	pstmt.setInt(1, questId);
	    	
	    	// execute query
	    	try (ResultSet rs = pstmt.executeQuery()) {
	    		// rs는 row 단위로 cursor를 옮긴다. 
	    		// quest_id는 중복될 수 없기 때문에 지금 rs에는 row가 딱 하나만 있다.
	    		category = rs.getString("category");
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return category;
	}
	 
	
	public int findDifficulty(int questId) throws FileNotFoundException, IOException {
	    String sql = "SELECT difficulty FROM quest WHERE quest_id = ?";
	    int difficulty = 0;
	    try (Connection conn = connector.getConnection();
	    		PreparedStatement pstmt = conn.prepareStatement(sql)) {
	    	
	    	// data binding
	    	pstmt.setInt(1, questId);
	    	
	    	// execute query
	    	try (ResultSet rs = pstmt.executeQuery()) {
	    		// rs는 row 단위로 cursor를 옮긴다. 
	    		// quest_id는 중복될 수 없기 때문에 지금 rs에는 row가 딱 하나만 있다.
	    		difficulty = rs.getInt("category");
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return difficulty;
	}
	
	public int findAwardId(int questId) throws FileNotFoundException, IOException {
	    String sql = "SELECT questset_id FROM quest WHERE quest_id = ?";
	    int questsetId = 0;
	    try (Connection conn = connector.getConnection();
	    		PreparedStatement pstmt = conn.prepareStatement(sql)) {
	    	
	    	// data binding
	    	pstmt.setInt(1, questId);
	    	
	    	// execute query
	    	try (ResultSet rs = pstmt.executeQuery()) {
	    		// rs는 row 단위로 cursor를 옮긴다. 
	    		// quest_id는 중복될 수 없기 때문에 지금 rs에는 row가 딱 하나만 있다.
	    		questsetId = rs.getInt("questset_id");
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return questsetId;		
	}
}