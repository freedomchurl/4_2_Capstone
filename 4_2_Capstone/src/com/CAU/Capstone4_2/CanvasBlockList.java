package com.CAU.Capstone4_2;

import java.util.ArrayList;

public class CanvasBlockList {

	private ArrayList<Block> blockList = new ArrayList<Block>();
	// 여기다가 블록들의 목록을 저장하도록 한다.
	
	CanvasBlockList()
	{
		
	}
	
	public ArrayList<Block> getResultList()
	{
		return this.blockList;
	}
	
	public void setResulList(ArrayList<Block> input)
	{
		this.blockList = input;
	}
	
}
