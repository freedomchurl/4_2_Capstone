package com.CAU.Capstone4_2;

import java.util.ArrayList;

public class CanvasBlockList {

	private ArrayList<Block> blockList = new ArrayList<Block>();
	// ����ٰ� ���ϵ��� ����� �����ϵ��� �Ѵ�.
	
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