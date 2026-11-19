package liferpg.connector;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL="jdbc:mysql://127.0.0.1:3306/teamdatabase";  
    private static final String USER = "dao";
    private static final String PASS = "wkvmtlf";
    
    public DatabaseConnector() {}
    
    public static Connection getConnection() throws SQLException {
    	Connection conn = null;
    	try {
		    conn = DriverManager.getConnection(URL, USER, PASS);
			System.out.println("DB 연결 성공");
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB 연결 정보를 확인하세요");
		}
		return conn;
    }
    
}
