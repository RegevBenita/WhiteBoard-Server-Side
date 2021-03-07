package org.regev.benita.whiteboard.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.regev.benita.whiteboard.model.User;
import org.regev.benita.whiteboard.resources.GroupResource;

public class UserService {
	private final String USER_NAME = "root";
	private final String PASSWORD = "Blacksta7";
	private final String URL = "jdbc:mysql://localhost:3306/whiteboard_db";
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private List<User> users = new ArrayList<>();
	
	//method that return a list of all the users from the database
	public List<User> getAllUsers(int groupId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");									//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);			//get the connection instance
			st = con.createStatement();												//create a statement
			rs = st.executeQuery("select * from user where group_id=" + groupId);	//execute query
			while(rs.next()) {
				users.add(new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("color")));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(st!=null)
				st.close();
				if(con!=null)
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return users;
	}
	
	//method that add user to the database
	public User addUser(int groupId, User user) {
		try {
			Class.forName("com.mysql.jdbc.Driver");									//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);			//get the connection instance
			st = con.createStatement();												//create a statement
			rs = st.executeQuery("select * from user where group_id=" + groupId);	//execute query
			while(rs.next()) {
				users.add(new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("color")));
			}
			if(!users.isEmpty())
				user.setId(users.get(users.size()-1).getId() + 1);
			else user.setId(1);
			String quary = "insert into user values("+groupId+","+user.getId()+",'"+user.getName()+"','"+user.getTxtColor()+"')";
			st.execute(quary);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(st!=null)
				st.close();
				if(con!=null)
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	//method that remove user to the database
	public User removeUser(int groupId, int userId) {
		int userIndexToRemove=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");									//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);			//get the connection instance
			st = con.createStatement();												//create a statement
			rs = st.executeQuery("select * from user where group_id=" + groupId);	//execute query
			while(rs.next()) {
				users.add(new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("color")));
				if(rs.getInt("user_id")==userId)
					userIndexToRemove=users.size()-1;
			}
			String quary = "delete from user where user_id=" + userId;
			st.execute(quary);	
			if(users.size()<2) {											//there is no more users 
				GroupResource groupService = new GroupResource();			//so delete all messages, shapes and group
				groupService.removeGroup(groupId);
				return null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if(st!=null)
				st.close();
				if(con!=null)
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return users.remove(userIndexToRemove);
	}
	
}
