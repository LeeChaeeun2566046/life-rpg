package liferpg.dao;

import liferpg.model.QuestSet;
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
 
import liferpg.model.Quest;
import liferpg.connector.DatabaseConnector;

public class QuestsetDao {
	private final DatabaseConnector connector;
	
	public QuestsetDao(DatabaseConnector connector) {
        this.connector = connector;
    } // end of constructor
	
	
	
	public void insertQuestset(QuestSet questset) throws FileNotFoundException, IOException {
		String sql = "INSERT INTO questset (repeat_num, completed_num, user_id, start_date, consistent) VALUES (?, ?, ?, ?, ?)";
		// 아예 새로운 quest set을 등록할 때, id 는 사용자가 설정하지 않고 시스템에서 자동 부여합니다 (Statement.RETURN_GENERATED_KEYS)
		
		try (Connection conn = connector.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
			
			// data binding
			pstmt.setInt(1, questset.getRepeatNum());
			pstmt.setInt(2, questset.getCompletedNum());
			pstmt.setInt(3, questset.getUserId());
			
			// local date -> date
			LocalDate ld = questset.getStartDate();
			Date d = java.sql.Date.valueOf(ld);
			pstmt.setDate(4, d);   
			
			pstmt.setBoolean(5, questset.isConsistent());
 			
			// execute update
			pstmt.executeUpdate();
			
			// 시스템에서 자동 부여한 quest id를 quest의 필드로 설정
			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int questsetId = generatedKeys.getInt("questset_id");
					questset.setId(questsetId);
					System.out.println(questsetId+ " questset이 추가되었습니다");
				}
			}// end of try
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateCompletedNum(int questsetId, int completedNum) throws FileNotFoundException, IOException {
		String sql = "UPDATE questset SET completed_num =? WHERE questset_id=?";
		
		try (Connection conn = connector.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			// data binding
			pstmt.setInt(1, completedNum);
			pstmt.setInt(2, questsetId);
			
			// execute update
			int rows = pstmt.executeUpdate();
   		    
			if (rows > 0) {
   			    System.out.println("completedNum이 수정되었습니다.");
   		    } else {
   			    System.out.println("해당 id가 존재하지 않습니다.");
   		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public void updateConsistent(int questsetId, boolean consistent) throws FileNotFoundException, IOException {
		String sql = "UPDATE questset SET completed_num =? WHERE questset_id=?";
		
		try (Connection conn = connector.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			// data binding
			pstmt.setBoolean(1, consistent);
			pstmt.setInt(2, questsetId);
			
			// execute update
			int rows = pstmt.executeUpdate();
   		    
			if (rows > 0) {
   			    System.out.println("consistent 정보가 수정되었습니다.");
   		    } else {
   			    System.out.println("해당 id가 존재하지 않습니다.");
   		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public List<QuestSet> findAll() throws FileNotFoundException, IOException{
	    List<QuestSet> questsetList = new ArrayList<>();
	    String sql = "SELECT * FROM quest";
	    
	    try (Connection conn = connector.getConnection();
	    		PreparedStatement pstmt = conn.prepareStatement(sql);){
	    	
	    	try (ResultSet rs = pstmt.executeQuery()) {
	    		while (rs.next()) {
	    			QuestSet questset = new QuestSet(); 
	    			questset.setUserId(rs.getInt("user_id"));
	    			
	    			// date -> local date
	    			Date d = rs.getDate("start_date");
	    			ZoneId defaultZoneId = ZoneId.systemDefault();
	    			Instant instant = d.toInstant();
	    			LocalDate ld = instant.atZone(defaultZoneId).toLocalDate();  
	    			questset.setStartDate(ld);  
	    			
	    			questset.setRepeatNum(rs.getInt("repeat_num"));
	    			questset.setQuestsetId(rs.getInt("questset_id"));
	    			questset.setConsistent(rs.getBoolean("consistent"));
	    			questset.setCompletedNum(rs.getInt("completed_num"));
	    		}
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    
	    return questsetList;
		
	}
	
	// 완료되지 않은 questset을 찾고 싶을 수도 있고, 완료된 questset를 찾고 싶을 수도 있어서 파라미터로 전달할 수 있도록 함
	public List<QuestSet> findCompleted(boolean boo) throws FileNotFoundException, IOException {
	    List<QuestSet> questsetList = new ArrayList<>();
	    String sql = "SELECT * FROM quest WHERE completed=?";
	    
	    try (Connection conn = connector.getConnection();
	    		PreparedStatement pstmt = conn.prepareStatement(sql);){
	    	
	    	// data binding
	    	pstmt.setBoolean(1, boo);
	    	
	    	// execute query
	    	try (ResultSet rs = pstmt.executeQuery()) {
	    		while (rs.next()) {
	    			QuestSet questset = new QuestSet(); 
	    			questset.setUserId(rs.getInt("user_id"));
	    			 
	    			// date -> local date
	    			Date d = rs.getDate("start_date");
	    			ZoneId defaultZoneId = ZoneId.systemDefault();
	    			Instant instant = d.toInstant();
	    			LocalDate ld = instant.atZone(defaultZoneId).toLocalDate();  
	    			questset.setStartDate(ld);  
	    			
	    			questset.setRepeatNum(rs.getInt("repeat_num"));
	    			questset.setQuestsetId(rs.getInt("questset_id"));
	    			questset.setConsistent(rs.getBoolean("consistent"));
	    			questset.setCompletedNum(rs.getInt("completed_num"));
	    			questsetList.add(questset);
	    		}
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    return questsetList;
		
	}
	
	public QuestSet findById(int questsetId) throws FileNotFoundException, IOException{
		QuestSet questset = new QuestSet();
		String sql = "SELECT * FROM quest WHERE quest_id = ?";
		
		try (Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			
			// data binding
			pstmt.setInt(1, questsetId);
			
			// execute query
			try (ResultSet rs = pstmt.executeQuery()) {
    			questset.setUserId(rs.getInt("user_id"));
    			
    			// date -> local date
    			Date d = rs.getDate("start_date");
    			ZoneId defaultZoneId = ZoneId.systemDefault();
    			Instant instant = d.toInstant();
    			LocalDate ld = instant.atZone(defaultZoneId).toLocalDate();  
    			questset.setStartDate(ld);  
     			
    			questset.setRepeatNum(rs.getInt("repeat_num"));
    			questset.setQuestsetId(rs.getInt("questset_id"));
    			questset.setConsistent(rs.getBoolean("consistent"));
    			questset.setCompletedNum(rs.getInt("completed_num"));
 			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questset;
		
	}

	// 새로 추가함!!
	public List<Integer> findAllByUserId(int userId) throws FileNotFoundException, IOException { 
		/* 사용 예시
		 * 
		 * userId = 101
		 * List<Integer> questsetIdList = findAllByUser(userId)
		 * questsetIdList ---> {1, 3, 4, 9}
		 * 이 리스트의 값들을 for문으로 순회하면서 findById(questsetId)에 파라미터로 전달하면
		 * award 객체들을 돌려받을 수 있다.
		 */
		
 		List<Integer> questsetIdList = new ArrayList<>();  // return value
	    String sql = "SELECT questset_id FROM questset WHERE user_id = ?";
    	
	    try (Connection conn = connector.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql);) {
    		
	    	// data binding
	    	pstmt.setInt(1, userId);
	    	
	    	// execute query
	    	try (ResultSet rs = pstmt.executeQuery()) {  
	    		// user_id = 101 이라고 치자
	    		// rs 에는 user_id = 101 인 questset의 id가 쫘라라락 들어 있음
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
	
	

}