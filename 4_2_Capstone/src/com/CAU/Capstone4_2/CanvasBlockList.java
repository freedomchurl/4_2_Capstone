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
	
	public void RefreshList()
	{
		Block tmp = this.blockList.get(0); // Start만 가져오고
		this.blockList = new ArrayList<Block>();
		this.blockList.add(tmp); // 다시 start만 넣는다.
	}
	
}
