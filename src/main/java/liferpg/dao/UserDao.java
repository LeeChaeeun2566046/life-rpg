package liferpg.dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liferpg.model.User;

public class UserDao {
    public UserDao() {
    	private final DatabaseConnector connector;
    	
    	public UserDao(DatabaseConnector connector) {
    		this.connector = connector;
    	}
    }
    
    public void insertUser(User user) {
    	String sql = "INSERT INTO user (exp, level, completed_count) VALUES (?, ?, ?)";
    	
    	try (Connection conn = connector.getConnection();
    			PreparedStatement pstmt = conn.preparedStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    		pstmt.setInt(1,  user.getExp());
    		pstmt.setInt(2, user.getLevel());
    		pstmt.setInt(3,  user.getCompletedCount());
    		
    		pstmt.executeUpdate();
    		
    		try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
    			if (generatedKeys.next()) {
    				int userId = generatedKeys.getInt("user_id"); // userId는 사용자가 입력하지 않고 시스템에서 자동 생성 및 부여해줍니다
    				user.setUserId(userId);
    				System.out.println(userId + " 학생 정보가 추가되었습니다."); 
    			} 
    		} // end of try
    		
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public void updateLevel(int userId, int level) {
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
    
    public void updateExp(int userId, int exp) {
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
    
    
    
}
