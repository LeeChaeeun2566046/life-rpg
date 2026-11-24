package liferpg.connector;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DatabaseConnector{
	public DatabaseConnector() {}

    public static Connection getConnection() throws FileNotFoundException, IOException {
    	
    	// 1. load the properties file
    	Properties props = new Properties();
    	props.load(new FileInputStream("db/db.properties")); // 작업 디렉토리= life-rpg. 상대 경로는 작업 디렉토리를 앞에 붙이지 않음    	
    	//2. read the props
    	String user = props.getProperty("user");
    	String password = props.getProperty("password");
    	String url = props.getProperty("url");
    	
    	Connection conn = null;
    	try {
		    conn = DriverManager.getConnection(url, user, password);
			System.out.println("DB 연결 성공");
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB 연결 정보를 확인하세요");
		}
		return conn;
    }
    
    
    
    
}