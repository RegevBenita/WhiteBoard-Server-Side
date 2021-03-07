package org.regev.benita.whiteboard.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.regev.benita.whiteboard.model.Message;

public class MessageService {
	private final String USER_NAME = "root";
	private final String PASSWORD = "Blacksta7";
	private final String URL = "jdbc:mysql://localhost:3306/whiteboard_db";
	private Connection con;
	private Statement st;
	private ResultSet rs;
//	private List<Message> messages = new ArrayList<>();
	
	//method that return a list of all the messages from the database
	public List<Message> getAllMessages(int groupId) {
		List<Message> messages = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");										//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);				//get the connection instance
			st = con.createStatement();													//create a statement
			rs = st.executeQuery("select * from message where group_id=" + groupId);	//execute query
			while(rs.next()) {
				messages.add(new Message(rs.getInt("message_id"),rs.getString("user_name"),rs.getString("message_txt"), rs.getInt("user_color")));
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
		return messages;
	}
	
	//method that add message to the database
	public Message addMessage(int groupId, Message message) {
		List<Message> messages = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");							//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);	//get the connection instance
			st = con.createStatement();										//create a statement
			rs = st.executeQuery("select * from message");					//execute query
			while(rs.next()) {
				messages.add(new Message(rs.getInt("message_id"),rs.getString("user_name"),rs.getString("message_txt"), rs.getInt("user_color")));
			}
//			if(!messages.isEmpty())
//				message.setId(messages.get(messages.size()-1).getId() +1);
//			else message.setId(1);
//			String quary = "insert into message values("+groupId+","+message.getId()+",'"+message.getUserName()+"','"+message.getText()+"','"+message.getUserColor()+"')";
			String quary;
			if(!messages.isEmpty())
				quary = "insert into message values("+groupId+","+null+",'"+message.getUserName()+"','"+message.getText()+"','"+message.getUserColor()+"')";
			else
				quary = "insert into message values("+groupId+","+ 1 +",'"+message.getUserName()+"','"+message.getText()+"','"+message.getUserColor()+"')";
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
		return message;
	}
	
	//method that get an group id and remove all the messages in that group, return the group id that been remove
	public Integer removeAllMessages(int groupId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");							//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);	//get the connection instance
			st = con.createStatement();										//create a statement
			String quary = "delete from message where group_id=" + groupId;
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
		return groupId;
	}
}
