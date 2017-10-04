package com.CAU.Capstone4_2;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Block {

	private int x_pos; 
	private int y_pos;
	// x, y ����� ��ǥ
	
	private Point2D location = null;
	
	private int height;
	private int width;
	// ����� ũ��
	
	private Block up = null;
	private Block down = null;
	// �� ��ϰ�, �Ʒ������ ������� ����ٰ� �����ϱ� �����̴�.
	
	private String name = null;
	
	private int Data=0;
	
	private Color blockColor;
	
	Block()
	{
		System.out.println("Default Constructor");
		//�⺻ ������
		
		this.height = 100;
		this.width = 150;
		
		this.blockColor = Color.BLUE;
		
	}
	
	public void setName(String input)
	{
		this.name = new String(input);
		
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setData(int Datatype)
	{
		this.Data = Datatype;
	}
	
	public int getData()
	{
		return this.Data;
	}
	
	// �׿� �����ڵ� �߰��� ��.

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
		// ���ο� Point2D ��ǥ�� �����ؼ� �־��ش�.
	}
	
	public Point2D getLocation()
	{
		return location;
	}
	
	
}


