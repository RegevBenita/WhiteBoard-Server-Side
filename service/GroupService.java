package org.regev.benita.whiteboard.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.regev.benita.whiteboard.model.Group;

public class GroupService {
	private final String USER_NAME = "root";
	private final String PASSWORD = "Blacksta7";
	private final String URL = "jdbc:mysql://localhost:3306/whiteboard_db";
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private TreeMap<Integer, Group> groups = new TreeMap<>();
	
	//method that return a list of all the groups from the database
	public List<Group> getAllGroups() {
		initGroupsTreeMap();
		return new ArrayList<Group>(groups.values());
	}
	
	//method that get an id and return the group with the same id from the database
	public Group getGroup(int id) {
		initGroupsTreeMap();
		return groups.get(id);	
	}

	//method that add group to the database
	public Group addGroup(Group group) {
		try {
			Class.forName("com.mysql.jdbc.Driver");							//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);	//get the connection instance
			st = con.createStatement();										//create a statement
			rs = st.executeQuery("select * from groups");					//execute query
			
			System.out.println(group.getName());
			while(rs.next()) {
				System.out.println("rs string    " + rs.getString("group_name"));
				groups.put(rs.getInt("group_id"),new Group(rs.getInt("group_id"),rs.getString("group_name")));
				if(group.getName().equals(rs.getString("group_name")))
					return new Group(0, "exist");
			}
			if(!groups.isEmpty())
				group.setId(groups.lastKey() + 1);
			else group.setId(1);
			String quary = "insert into groups values(" +group.getId()+ ",'"+ group.getName()+"')";
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
		return group;
	}
	
	//method that get an id and remove the group with the same id from the database
	public Group removeGroup(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");							//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);	//get the connection instance
			st = con.createStatement();										//create a statement
			rs = st.executeQuery("select * from groups");					//execute query
			while(rs.next()) {
				groups.put(rs.getInt("group_id"),new Group(rs.getInt("group_id"),rs.getString("group_name")));
			}
			String quary = "delete from groups where group_id=" + id;
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
		return groups.remove(id);
	}
	
	//method that get all groups from the database and put inside groups tree map
	private void initGroupsTreeMap() {
		try {
			Class.forName("com.mysql.jdbc.Driver");							//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);	//get the connection instance
			st = con.createStatement();										//create a statement
			rs = st.executeQuery("select * from groups");					//execute query
			while(rs.next()) {
				groups.put(rs.getInt("group_id"),new Group(rs.getInt("group_id"),rs.getString("group_name")));
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
	}
}
