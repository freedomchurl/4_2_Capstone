package com.CAU.Capstone4_2;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Block extends Button{

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
	
	private Image buttonImage;
	
	Block()
	{
		//System.out.println("Default Constructor");
		//�⺻ ������
		
		this.height = 100;
		this.width = 150;
		
		super.setHeight(100);
		super.setWidth(150);
		
		this.blockColor = Color.BLUE;
		
		//this.setName(name);
		this.setPrefSize(super.getWidth(), super.getHeight());
		this.setStyle("-fx-background-color: " + FxUtils.toRGBCode(this.blockColor) + "; -fx-text-fill: white; -fx-font-size: 50;  -fx-font-family:NanumSquare ExtraBold;"
					+ "-fx-border-color: white;");
		
	}
	
	Block(String name,int Num, Color color)
	{
		super(name); 
		// ��ư�� ���ڸ� ���� 
		// ��ư�� �̹����� �־���Ѵ�
		
		this.height = 100;
		this.width = 150;
		this.name = new String(name);
		this.blockColor = color;
		this.Data= Num;
		super.setHeight(100);
		super.setWidth(150);
		
		//this.blockColor = Color.BLUE;
		System.out.println(this.name);
		
		this.setPrefSize(super.getWidth(), super.getHeight());
		this.setStyle("-fx-background-color: " + FxUtils.toRGBCode(this.blockColor) + "; -fx-text-fill: white; -fx-font-size: 50;  -fx-font-family:NanumSquare ExtraBold;"
					+ "-fx-border-color: white;");
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
	
	public void Draw(GraphicsContext gc)
	{
		gc.setFill(this.blockColor);
		gc.fillRect(this.x_pos, this.y_pos, this.width, this.height);
	}
	
}


