package org.regev.benita.whiteboard.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.regev.benita.whiteboard.model.MyShape;

public class MyShapeService {
	private final String USER_NAME = "root";
	private final String PASSWORD = "Blacksta7";
	private final String URL = "jdbc:mysql://localhost:3306/whiteboard_db";
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	//method that return a list of all the shapes from the database
	public List<MyShape> getAllShapes(int groupId) {
		List<MyShape> shapes = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");									//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);			//get the connection instance
			st = con.createStatement();												//create a statement
			rs = st.executeQuery("select * from shape where group_id=" + groupId);	//execute query
			while(rs.next()) {
				shapes.add(new MyShape(rs.getInt("shape_id"), rs.getString("shape_type"), rs.getBoolean("fill"), rs.getInt("color"),
										rs.getFloat("x1"), rs.getFloat("y1"),rs.getFloat("x2"), rs.getFloat("y2"),
										rs.getFloat("radius"), rs.getString("shape_text")));
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
		return shapes;
	}
	
	
	//method that update all the shapes that in a specific group
	public List<MyShape> updateShapes(List<MyShape> newShapes, int groupId) {
		int id=1;
		String quary;
		try {
			Class.forName("com.mysql.jdbc.Driver");									//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);			//get the connection instance
			st = con.createStatement();												//create a statement
			st.execute("delete from shape where group_id="+groupId);				//execute query
			for(MyShape shape: newShapes) {
				 quary = "insert into shape values("+groupId+","+id+",'"+shape.getShapeType()+"',"+shape.getX1()+
						 ","+shape.getY1()+","+shape.getX2()+","+shape.getY2()+
						 ","+shape.getRadius()+",'"+shape.getColor()+"',"+shape.isFill()+",'"+shape.getShapeText()+"')";	
				 st.execute(quary);
				 shape.setId(id);		//set the real id for the newShape list
				 id++;
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
		return newShapes;
	}
	
	//method that get an group id and remove all the shapes in that group, return the group id that been remove
	public int removeAllShapes(int groupId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");							//load and register the driver
			con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);	//get the connection instance
			st = con.createStatement();										//create a statement
			String quary = "delete from shape where group_id=" + groupId;
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
