package com.CAU.Capstone4_2;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Block extends ImageView{

	private int x_pos; 
	private int y_pos;
	// x, y 블록의 좌표
		
	private int height;
	private int width;
	// 블록의 크기
	
	
	private ArrayList<Block> upperList = new ArrayList<Block>();
	private ArrayList<Block> lowList = new ArrayList<Block>();
	
	private String contents = null;
	private int value=0;
	
	private Image buttonImage;
	private Image buttonImage2;
	
	private boolean hasBorder = true;
	
	Block()
	{
		this.height = 100;
		this.width = 150;		
		super.setFitHeight(100);
		super.setFitWidth(150);
		
	}
	
	Block(Image input)
	{
		this.height = 100;
		this.width = 150;
		this.buttonImage = input;
		this.setImage(buttonImage);
	}
	
	Block(String name)
	{
		//super(name); 
		// 버튼의 글자를 위해 
		// 버튼의 이미지를 넣어야한다
		
		this.height = 100;
		this.width = 150;
		super.setFitHeight(100);
		super.setFitWidth(150);
		
	}
	
	Block(Block input)
	{
		this.x_pos = input.x_pos;
		this.y_pos = input.y_pos;
		this.height = input.height;
		this.width = input.width;
		this.contents = input.contents;
		this.value = input.value;
		this.buttonImage = input.buttonImage;
		this.buttonImage2 = input.buttonImage2;
		
		this.hasBorder = input.hasBorder;
	}
	
	public void setContents(String input)
	{
		this.contents = new String(input);
		
	}
	
	public String getContents()
	{
		return this.contents;
	}
	
	public void setValue(int Datatype)
	{
		this.value = Datatype;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	
	
	// 그외 생성자들 추가할 것.

	public void setLocation(int x, int y)
	{
		this.x_pos = x;
		this.y_pos = y;
		
	}
	
	public Point2D getLocation()
	{
		return new Point2D(this.x_pos,this.y_pos);
	}
	
	public void Draw(GraphicsContext gc)
	{
		
		if(hasBorder == false)
		{
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1.0);
			gc.strokeRect(this.x_pos, this.y_pos, this.width, this.height);
		}
		gc.drawImage(this.buttonImage, x_pos, y_pos, width, height);
		
	}
	
	public void Run()
	{
		
	}
	
}


