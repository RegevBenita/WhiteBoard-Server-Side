package org.regev.benita.whiteboard.model;

public class MyShape {
	private int id;
	private int color;
	private String shapeType, shapeText;
	private boolean fill;
	private float x1, y1, x2, y2;
	private float radius;
	
	public MyShape() {
		
	}
	
	public MyShape(int id, String shapeType, boolean fill, int color, float x1, float y1, float x2, float y2,
			float radius, String shapeText) {
		this.id = id;
		this.shapeType = shapeType;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.radius = radius;
		this.color = color;
		this.fill = fill;
		this.shapeText = shapeText;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public float getRadius() {
		return radius;
	}

	public float getX1() {
		return x1;
	}

	public void setX1(float x1) {
		this.x1 = x1;
	}

	public float getY1() {
		return y1;
	}

	public void setY1(float y1) {
		this.y1 = y1;
	}

	public float getX2() {
		return x2;
	}

	public void setX2(float x2) {
		this.x2 = x2;
	}

	public float getY2() {
		return y2;
	}

	public void setY2(float y2) {
		this.y2 = y2;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShapeType() {
		return shapeType;
	}

	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}

	public String getShapeText() {
		return shapeText;
	}

	public void setShapeText(String shapeText) {
		this.shapeText = shapeText;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}
	
    //check if 2 shapes are equals
    @Override
    public boolean equals(Object o) {
        if(o instanceof MyShape) {
        	MyShape s = (MyShape) o;
            if ( this.shapeType.equals(s.getShapeType()) &&
                this.fill == s.isFill() && this.color == s.getColor() &&
                    (int)this.x1 == (int)s.getX1() && (int)this.x2 == (int)s.getX2() &&
                    (int)this.y1 == (int)s.getY1() && (int)this.y2 == (int)s.getY2() &&
                    (int)this.radius == (int)s.getRadius() && this.shapeText.equals(s.getShapeText())) {
                return true;

            }
        }
        return false;
    }
	
}
