package ca.cafeteros.test;

//import ca.cafeteros.entities.*;
//import javax.persistence.*;

import java.sql.*;

public class jdbcConnection {
	
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/cafeteros";
	static final String USER = "myself";
	static final String PASS = "www.myself.com";

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		
		try{
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Creating statement....");
			stmt = conn.createStatement();
			
			String sql = "SELECT * FROM users";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				int id = rs.getInt("id");
				String email = rs.getString("email");
				System.out.print("id: " + id);
				System.out.println("email: " + email);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Goodbye!");
	}

}
