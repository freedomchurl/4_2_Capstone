package com.CAU.Capstone4_2;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CapstoneMainController implements Initializable{

	
	@FXML private Button initial;
	@FXML private Button controlButton;
	@FXML private Button numButton;
	@FXML private Button normalButton;
	@FXML private HBox PanelBox;
	@FXML private Button RunBlock;
	// ����, ����, ���� , ��� ��ư�� ������ �������� ����â�� �ʱ�ȭ �ϱ� �����̴�.
	
	private CanvasBlockList myList = new CanvasBlockList();

	@FXML private Canvas drawCanvas; // ��ϵ��� �׷����� Canvas
	//@FXML private Group drawGroup; // Group�� � �뵵�� ���Ǵ��� �𸣰ڴ�. ������, �ϴ� �߰��� ����.
	
	private ArrayList<Block> numBlocks = new ArrayList<Block>();
	// ���� ��ϵ��� �����ϱ� ���� -> ������ �� �гο� ��� ������ �����ϰ� �ȴ�.
	
	private ArrayList<Block> controlBlocks = new ArrayList<Block>();
	// ���� ��ϵ��� �����ϱ� ����
	
	private ArrayList<Block> normalBlocks = new ArrayList<Block>();
	// �Ϲ� ��� ��ϵ��� �����ϱ� ����

	private Scene myScene = null;
	
	private int currentPanel = 0;
	// 0�� Num, 1�� Control, 2�� Normal
	private int selectedBlock = 0;
	
	private Block SelectedObject = null;
	
	private boolean selected = false;
	private boolean SceneEscSet = false;
	
	private Color[] numColors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.BROWN, Color.PURPLE, Color.PINK, Color.AQUA, Color.CHARTREUSE};
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//myScene = drawCanvas.getScene();
		
		GraphicsContext gc = drawCanvas.getGraphicsContext2D();

		Block startBlock = new Block();
		
		startBlock.Draw(gc);
		
		InitialArray(); // 3���� ArrayList�� �ʱ�ȭ �ϴ� �۾� -> ���� �ʿ� 
		
		ButtonActionInitial();
		
		setPanelBlocks(numBlocks); 
		// Default��, Panel�� ��ϵ��� ����� ���� ��ϵ�� ��ġ�ϵ��� �Ѵ�.
		
		SetCanvasEvent();
		
	}
	
	public void SetCanvasEvent()
	{
		drawCanvas.setOnMouseMoved(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				GraphicsContext gss = drawCanvas.getGraphicsContext2D();
				gss.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
				
				for(int i=0;i<myList.getResultList().size();i++)
				{
					gss.setFill(myList.getResultList().get(i).getColor());
					gss.fillRect(50+i*160,100,150,100);
				}
				
				if(selected == true) // ���õǾ������� ������ �� �ִ�.
				{
					
					
					gss.setFill(SelectedObject.getColor());
					gss.fillRect(event.getX()-75, event.getY()-50, 150, 100);
				}
			}
			
		});
		
		drawCanvas.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(selected == true)
				{
					myList.getResultList().add(new Block(SelectedObject.getName(),SelectedObject.getData(),SelectedObject.getColor()));
					
					
				}
			}
			
		});
	}
	
	
	public void SetSceneEsc()
	{
		if(myScene == null)
		{
			System.out.println("What");
		}
		myScene.setOnKeyPressed(new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if(event.getCode() == KeyCode.ESCAPE)
				{
					System.out.println("Ÿ�̹�������");
					selected = false;
				}
			}
		});
		
	}
	
	
	// ���⼭ ����� ������ �ʱ�ȭ����� �Ѵ�.
	public void InitialArray()
	{
		for(int i=0;i<10;i++)
		{
			numBlocks.add(new NumberBlock(i,numColors[i],Integer.toString(i)));
			
			// NumberBlock�� �߰��Ѵ�. ������ ���ڰ� ������ �Ѵ�.
		}
		
		for(int i=0;i<10;i++)
		{
			controlBlocks.add(new NumberBlock(i,Color.BLUE,"��"));
			
		}
			// NormalBlock�� ���� �ڵ�.
		
		for(int i=0;i<8;i++)
		{
			//controlBlocks.add(new NumberBlock(i,Color.ALICEBLUE,Integer.toString(i)));
		}
	}
	
	public void ButtonActionInitial()
	{
		// �켱 NumberBlock���� Event�� �߰��غ���?
		for(int i=0;i<10;i++)
		{
			
			numBlocks.get(i).setOnAction(new EventHandler<ActionEvent>(){		
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					Block tmp = (Block) event.getSource();
					System.out.println(tmp.getName());
					
					selected = true; // ���� �Ǿ������� �д�����.
					selectedBlock = 0; // ���õ� ����� ������ 0������ �д�.
					
					SelectedObject = tmp;
					// ������ ��ü�� �����´�.
					
					if(SceneEscSet == false)
					{
						myScene = drawCanvas.getScene();
						System.out.println("���� 1ȸ��");
						SetSceneEsc();
						SceneEscSet = true;
					}
					// �̰� �ٸ� ��ư���� �����ؾ��Ѵ�.
				}
				
			});
		}
		
		// ���⿡ �ٸ� Block �����鵵 �ʱ�ȭ ������Ѵ�.
		
		
		
		
		/////
		
		
		controlButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				setPanelControl(event);
				// ������ �����г��� ��ư���� Control ��ϵ�� ��ġ�Ѵ�.
				currentPanel = 1;
			}
			
		});
		
		
		normalButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				setPanelNormal(event);
				
				currentPanel = 2;
			}
			
		});
			
		numButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				setPanelNum(event);
				
				currentPanel = 0;
			}
			
		});
			
		// �ʱ�ȭ ��ư�� �����ϴ� �κ�
		initial.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub	
				InitialCanvas(event);
			}		
		});
		
		RunBlock.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	
	
	
	// �� �޼ҵ带 �Ʒ��� 3���� �޼ҵ忡�� ���ϵ��� �Ѵ�.
	// -> 3���� �и��ϵ��� �Ѵ�.
	public void setPanelBlocks(ArrayList<Block> input)
	{
		PanelBox.getChildren().clear();
		// PanelBox �� �ʱ�ȭ �Ѵ�.
		
		PanelBox.setAlignment(Pos.CENTER_LEFT);
		//PanelBox.getChildren() �̰� �̿��ؼ� �߰��ϵ��� �Ѵ�.
		for(int i=0;i<input.size();i++)
		{
			//Button panelButton = new Button(input.get(i).getName());
			PanelBox.setMargin(input.get(i), new Insets(10,10,15,15)); // top, bottom, right, left
			//panelButton.setPrefSize(input.get(i).getWidth(), input.get(i).getHeight());
			//panelButton.setStyle("-fx-background-color: " + FxUtils.toRGBCode(numColors[i]) + "; -fx-text-fill: white; -fx-font-size: 50;  -fx-font-family:NanumSquare ExtraBold;"
				//	+ "-fx-border-color: white;");
			//panelButton.getStyleClass().add("-fx-border-color: white; -fx-border-radius:50;");
			//�̺κ� �۾��� ��������, �׳� �̹����� ���°� ���� �� ����.
			
			//panelButton.setStyle();
			//panelButton.getStyleClass().add("-fx-background-color: " + FxUtils.toRGBCode(numColors[i]));
			// ���� �κ��� �迭�� �̿��Ͽ��� ������ �� �ִ�.
			PanelBox.getChildren().add(input.get(i));			
		}
	}
	
	
	
	
	// �Ʒ� 3���� �Լ��� ���δ� ���� �д�. �ϳ��� �޼ҵ�� ����, ArrayList<Block>�� �����ϴ� ������ ���������Ѵ�.
	public void setPanelNormal(ActionEvent event)
	{
		this.setPanelBlocks(normalBlocks);
	}
	
	public void setPanelNum(ActionEvent event)
	{
		this.setPanelBlocks(numBlocks);
	}
	
	public void setPanelControl(ActionEvent event)
	{
		this.setPanelBlocks(controlBlocks);
	}
	
	
	public void InitialCanvas(ActionEvent event)
	{
		System.out.println("Canvas�� �ʱ�ȭ �ϵ��� �Ѵ�");
		
		GraphicsContext gc = drawCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
		
		// �ʱ�ȭ �ϴ� �κ�.
		
		System.out.println("�ʱ�ȭ�Ϸ�");		
	}
	
}
