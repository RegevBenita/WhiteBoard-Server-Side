package org.regev.benita.whiteboard.model;

public class User {
	private int id;
	private String txtColor;
	private String name;
	
	public User(){
		
	}
	
	public User(int id, String name, String txtColor){
		this.id = id;
		this.name = name;
		this.txtColor = txtColor;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTxtColor() {
		return txtColor;
	}
	
	public void setTxtColor(String txtColor) {
		this.txtColor = txtColor;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
