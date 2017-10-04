package com.CAU.Capstone4_2;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Block {

	private int x_pos; 
	private int y_pos;
	// x, y 블록의 좌표
	
	private Point2D location = null;
	
	private int height;
	private int width;
	// 블록의 크기
	
	private Block up = null;
	private Block down = null;
	// 위 블록과, 아래블록이 있을경우 여기다가 연결하기 위함이다.
	
	
	private Color blockColor;
	
	Block()
	{
		System.out.println("Default Constructor");
		//기본 생성자
	}
	
	
	// 그외 생성자들 추가할 것.

	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public void setColor(Color blockcolor)
	{
		this.blockColor = blockcolor;
	}
	
	public Color getColor()
	{
		return this.blockColor;
	}
	
	public void setLocation(int x, int y)
	{
		this.x_pos = x;
		this.y_pos = y;
		
		this.location = new Point2D(x_pos,y_pos);
		// 새로운 Point2D 좌표를 생성해서 넣어준다.
	}
	
	public Point2D getLocation()
	{
		return location;
	}
	
	
}


