package liferpg.dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import liferpg.model.QuestSet;
import liferpg.model.User;
import liferpg.connector.*;


public class UserDao {
	private final DatabaseConnector connector;
    
	public UserDao(DatabaseConnector connector) {
    		this.connector = connector;
    }
    
    
    public void insertUser(User user) throws FileNotFoundException, IOException{
    	String sql = "INSERT INTO user (exp, level, completed_count, nickname) VALUES (?, ?, ?, ?)";
    	
    	 
    	try (Connection conn = connector.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    		pstmt.setInt(1,  user.getExp());
    		pstmt.setInt(2, user.getLevel());
    		pstmt.setInt(3,  user.getCompletedCount());
    		pstmt.setString(4, user.getNickname());
    		
    		try {
    		pstmt.executeUpdate();
    		} catch (SQLIntegrityConstraintViolationException e) {
    			System.out.println("해당 nickname의 학생이 이미 존재합니다.");
    		}
    		
    		try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
    			if (generatedKeys.next()) {
    				int userId = generatedKeys.getInt(1); // userId는 사용자가 입력하지 않고 시스템에서 자동 생성 및 부여해줍니다
    				user.setUserId(userId);
    				System.out.println(userId + " 학생 정보가 추가되었습니다."); 
    			} 
    		}  
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public void updateLevel(int userId, int level) throws FileNotFoundException, IOException{
    	String sql = "UPDATE user SET level=? WHERE user_id = ?";
    	
    	try (Connection conn = connector.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    		 pstmt.setInt(1, level);
    		 pstmt.setInt(2, userId);
    		 
    		 int rows = pstmt.executeUpdate();
    		 if (rows > 0) {
    			 System.out.println("학생의 level이 수정되었습니다.");
    		 } else {
    			 System.out.println("해당 id의 학생이 존재하지 않습니다.");
    		 }
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}  
    }
    
    public void updateExp(int userId, int exp) throws FileNotFoundException, IOException {
    	String sql = "UPDATE user SET exp=? WHERE user_id = ?";
    	
    	try (Connection conn = connector.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql)) {
    		 pstmt.setInt(1, exp);
    		 pstmt.setInt(2, userId);
    		 
    		 int rows = pstmt.executeUpdate();
    		 if (rows > 0) {
    			 System.out.println("학생의 exp가 수정되었습니다.");
    		 } else {
    			 System.out.println("해당 id의 학생이 존재하지 않습니다.");
    		 }
    	} catch (SQLException e) {
    		e.printStackTrace();
    	} // end of catch
    }    
    
     
	public List<User> findAll() throws FileNotFoundException, IOException{
	    List<User> userList = new ArrayList<>();
	    String sql = "SELECT * FROM user";
	    
	    try (Connection conn = connector.getConnection();
	    		PreparedStatement pstmt = conn.prepareStatement(sql);){
	    	
	    	try (ResultSet rs = pstmt.executeQuery()) {
	    		while (rs.next()) {
	    			User user = new User(); 
	    			user.setUserId(rs.getInt("user_id"));
	    			user.setNickname(rs.getString("nickname"));
	    			user.setLevel(rs.getInt("level"));
	    			user.setCompletedCount(rs.getInt("completed_count"));
	    			user.setExp(rs.getInt("exp"));
	    			
	    			userList.add(user);
 	    		}
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	    
	    return userList;
		
	}
	

    
    
    
}