package liferpg.connector;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class DatabaseConnector{
	public DatabaseConnector() {}

	
    public static Connection getConnection() throws FileNotFoundException, IOException {
 
    	// 1. load the properties file
    	Properties props = new Properties();
    	// 작업 디렉토리= life-rpg. 상대 경로는 작업 디렉토리를 앞에 붙이지 않음    	
    	try (InputStream is = DatabaseConnector.class.getClassLoader().getResourceAsStream("db/db.properties")) {
    	    if (is==null) {
    	    	throw new FileNotFoundException("classpath 에서 db/db.properties를 찾을 수 없습니다.");
    	    }
    	    props.load(is);
    	}
    	//2. read the props
    	String user = props.getProperty("user");
    	String password = props.getProperty("password");
    	String url = props.getProperty("url");
    	
    	Connection conn = null;
    	try {
		    conn = DriverManager.getConnection(url, user, password);
			//System.out.println("DB 연결 성공");
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DB 연결 정보를 확인하세요");
		}
		return conn;
    }
}