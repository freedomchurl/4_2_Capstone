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
	// 각각, 숫자, 제어 , 기능 버튼을 눌러서 오른쪽의 선택창을 초기화 하기 위함이다.
	
	private CanvasBlockList myList = new CanvasBlockList();

	@FXML private Canvas drawCanvas; // 블록들이 그려지는 Canvas
	//@FXML private Group drawGroup; // Group은 어떤 용도로 사용되는지 모르겠다. 하지만, 일단 추가해 놨다.
	
	private ArrayList<Block> numBlocks = new ArrayList<Block>();
	// 숫자 블록들을 저장하기 위함 -> 오른쪽 위 패널에 띄울 종류를 저장하게 된다.
	
	private ArrayList<Block> controlBlocks = new ArrayList<Block>();
	// 제어 블록들을 저장하기 위함
	
	private ArrayList<Block> normalBlocks = new ArrayList<Block>();
	// 일반 기능 블록들을 저장하기 위함

	private Scene myScene = null;
	
	private int currentPanel = 0;
	// 0은 Num, 1은 Control, 2는 Normal
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
		
		InitialArray(); // 3개의 ArrayList를 초기화 하는 작업 -> 수정 필요 
		
		ButtonActionInitial();
		
		setPanelBlocks(numBlocks); 
		// Default로, Panel의 블록들의 모양을 숫자 블록들로 배치하도록 한다.
		
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
				
				if(selected == true) // 선택되었을때만 움직일 수 있다.
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
					System.out.println("타이밍을보자");
					selected = false;
				}
			}
		});
		
	}
	
	
	// 여기서 블록의 종류를 초기화해줘야 한다.
	public void InitialArray()
	{
		for(int i=0;i<10;i++)
		{
			numBlocks.add(new NumberBlock(i,numColors[i],Integer.toString(i)));
			
			// NumberBlock을 추가한다. 각각의 숫자가 들어가도록 한다.
		}
		
		for(int i=0;i<10;i++)
		{
			controlBlocks.add(new NumberBlock(i,Color.BLUE,"▶"));
			
		}
			// NormalBlock에 대한 코드.
		
		for(int i=0;i<8;i++)
		{
			//controlBlocks.add(new NumberBlock(i,Color.ALICEBLUE,Integer.toString(i)));
		}
	}
	
	public void ButtonActionInitial()
	{
		// 우선 NumberBlock들의 Event를 추가해볼까?
		for(int i=0;i<10;i++)
		{
			
			numBlocks.get(i).setOnAction(new EventHandler<ActionEvent>(){		
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					Block tmp = (Block) event.getSource();
					System.out.println(tmp.getName());
					
					selected = true; // 선택 되었음으로 둔다음에.
					selectedBlock = 0; // 선택된 블록의 종류를 0번으로 둔다.
					
					SelectedObject = tmp;
					// 선택한 객체를 가져온다.
					
					if(SceneEscSet == false)
					{
						myScene = drawCanvas.getScene();
						System.out.println("최초 1회만");
						SetSceneEsc();
						SceneEscSet = true;
					}
					// 이걸 다른 버튼에도 적용해야한다.
				}
				
			});
		}
		
		// 여기에 다른 Block 종류들도 초기화 해줘야한다.
		
		
		
		
		/////
		
		
		controlButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				setPanelControl(event);
				// 오른쪽 선택패널의 버튼들을 Control 블록들로 배치한다.
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
			
		// 초기화 버튼을 설정하는 부분
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
	
	
	
	
	// 이 메소드를 아래의 3개의 메소드에서 콜하도록 한다.
	// -> 3개로 분리하도록 한다.
	public void setPanelBlocks(ArrayList<Block> input)
	{
		PanelBox.getChildren().clear();
		// PanelBox 를 초기화 한다.
		
		PanelBox.setAlignment(Pos.CENTER_LEFT);
		//PanelBox.getChildren() 이걸 이용해서 추가하도록 한다.
		for(int i=0;i<input.size();i++)
		{
			//Button panelButton = new Button(input.get(i).getName());
			PanelBox.setMargin(input.get(i), new Insets(10,10,15,15)); // top, bottom, right, left
			//panelButton.setPrefSize(input.get(i).getWidth(), input.get(i).getHeight());
			//panelButton.setStyle("-fx-background-color: " + FxUtils.toRGBCode(numColors[i]) + "; -fx-text-fill: white; -fx-font-size: 50;  -fx-font-family:NanumSquare ExtraBold;"
				//	+ "-fx-border-color: white;");
			//panelButton.getStyleClass().add("-fx-border-color: white; -fx-border-radius:50;");
			//이부분 글씨로 쓰지말고, 그냥 이미지로 쓰는게 나을 것 같다.
			
			//panelButton.setStyle();
			//panelButton.getStyleClass().add("-fx-background-color: " + FxUtils.toRGBCode(numColors[i]));
			// 색상 부분은 배열을 이용하여서 변경할 수 있다.
			PanelBox.getChildren().add(input.get(i));			
		}
	}
	
	
	
	
	// 아래 3개의 함수의 내부는 같게 둔다. 하나의 메소드로 빼서, ArrayList<Block>을 전달하는 것으로 끝내도록한다.
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
		System.out.println("Canvas를 초기화 하도록 한다");
		
		GraphicsContext gc = drawCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
		
		// 초기화 하는 부분.
		
		System.out.println("초기화완료");		
	}
	
}
