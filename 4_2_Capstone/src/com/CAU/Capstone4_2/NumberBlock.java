package com.CAU.Capstone4_2;

import javafx.scene.paint.Color;

public class NumberBlock extends Block{

	
	NumberBlock(int Num, Color color, String buttonText)
	{
		super();
		this.setData(Num);
		this.setColor(color);
		this.setName(buttonText);
		// ���� �߰��ϵ��� �Ѵ�.
	}
}
