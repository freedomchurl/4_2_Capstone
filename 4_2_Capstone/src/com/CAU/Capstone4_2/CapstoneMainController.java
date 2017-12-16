package com.CAU.Capstone4_2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CapstoneMainController implements Initializable {

	// 2017 - 12 - 05 추가 사항
	
	// ArrayList에서 배열로 구조 변경
	private Block[][] myBlockList = new Block[13][20];
	// 나중에 늘려야하는게 맞지만, 우선적으로는 이렇게 넘어간다.
	
	
	
	@FXML
	private Button initial;
	@FXML
	private Button controlButton;
	@FXML
	private Button numButton;
	@FXML
	private Button normalButton;
	@FXML
	private HBox PanelBox;
	@FXML
	private Button RunBlock;
	// 각각, 숫자, 제어 , 기능 버튼을 눌러서 오른쪽의 선택창을 초기화 하기 위함이다.

	private CanvasBlockList myList = new CanvasBlockList();

	@FXML
	private Canvas drawCanvas; // 블록들이 그려지는 Canvas
	// @FXML private Group drawGroup; // Group은 어떤 용도로 사용되는지 모르겠다. 하지만, 일단 추가해
	// 놨다.

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

	Thread UIThread = null;

	private Block SelectedObject = null;

	private boolean selected = false;
	private boolean SceneEscSet = false;

	private Image[] num_imgs = null;
	private Image[] control_imgs = null;
	private Image[] normal_imgs = null;
	// Image들을 저장하기 위함이다.

	private Block Guide = new Block();

	private String[] num_path = { "variable1.png", "variable2.png", "variable3.png", "variable4.png", "num00.png",
			"num01.png", "num02.png", "num03.png", "num04.png", "num05.png", "num06.png", "num07.png", "num08.png",
			"num09.png" };

	private String[] num_label = { "가","나","다","라","0","1","2","3","4","5","6","7","8","9"};
	
	private String[] control_path = { "loop1.png", "loop2.png", "branchup1.png",
			"branchup2.png", "equal.png", "divide.png","minus.png","multiply.png","plus.png","remainder.png","upper.png", "lower.png","column.png" };

	private String[] control_label = {"loop","}","if","}","=","/","-","*","+","%",">","<",","};

	
	private String[] normal_path = { "bulb1.png", "speaker2.png","home.png" ,"average.png","max.png","min.png","random.png","sum.png" };
	
	private String[] normal_label = {"light","sound","home","average","max","min","random","sum"};

	private String[] start_home = { "start.png", "home.png" };

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// myScene = drawCanvas.getScene();
		Block startBlock = null;
		try {
			startBlock = new Block(new Image(new FileInputStream(start_home[0])),"start");
			startBlock.setLocation(50, 300);
			System.out.println("추가가안되었나");
		} catch (Exception e) {
			System.out.println("추가가안되었1111나");
		}

		this.myBlockList[6][0] = startBlock;
		// 초기블록 설정을 위함

		InitialArray(); // 3개의 ArrayList를 초기화 하는 작업 -> 수정 필요
		// 이건 위의 블록들을 생성하기 위함이다.

		ButtonActionInitial();
		// 위의 버튼들을 초기화 하기 위함이다. 객체를 임시로 담아서 가져온다는 발상은 변함없이 유지하도록 한다.

		setPanelBlocks(numBlocks);
		// Default로, Panel의 블록들의 모양을 숫자 블록들로 배치하도록 한다.

		SetCanvasEvent();
		// 이건 좀 다시 짜야한다.
		//StartUIUpdate();

		UIUpdate();

	}

	
	public void UIUpdate()
	{
		GraphicsContext gss = drawCanvas.getGraphicsContext2D();
		gss.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
		
		for (int i = 0; i < 13; i++) {
			//myList.getResultList().get(i).Draw(gss);
			for(int j=0;j<20;j++)
			{
				//System.out.println(i + " " + j);
				if(myBlockList[i][j]!=null)
					myBlockList[i][j].Draw(gss);
			}
		}

		
		if (selected == true) {
			SelectedObject.Draw(gss);

			gss.setStroke(Color.BLACK);
			gss.setLineWidth(2.0);
			gss.strokeRect(Guide.getLocation().getX(), Guide.getLocation().getY(), 75, 50);
		}
		
	}
	
	
	/*
	public void StartUIUpdate() {
		UIThread = new Thread() {
			public void run() {
				while (true) {
					// UI update를 계속 진행해야 한다.
					Platform.runLater(() -> {
						// Runnable 객체를 넘겨줌으로써, UI 수정이 가능하게 한다.

						GraphicsContext gss = drawCanvas.getGraphicsContext2D();
						gss.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());

						// System.out.println("사이즈는 = " +
						// myList.getResultList().size());
						for (int i = 0; i < 7; i++) {
							//myList.getResultList().get(i).Draw(gss);
							for(int j=0;j<20;j++)
							{
								System.out.println(i + " " + j);
								if(myBlockList[i][j]!=null)
									myBlockList[i][j].Draw(gss);
							}
						}

						if (selected == true) {
							SelectedObject.Draw(gss);

							gss.setStroke(Color.BLACK);
							gss.setLineWidth(2.0);
							gss.strokeRect(Guide.getLocation().getX(), Guide.getLocation().getY(), 75, 50);
						}
					});

				}
			}
		};

		UIThread.start();
	}
*/

	public void SetCanvasEvent() {
		
		//drawCanvas.setWidth(2000);
		//drawCanvas.setHeight(2000);
		
		drawCanvas.setOnMouseMoved(new EventHandler<MouseEvent>() {

			int minindex = 0;

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub

				if (selected == true) // 선택되었을때만 움직일 수 있다.
				{
					SelectedObject.setLocation((int) event.getX() - 37, (int) event.getY() - 25);
					// gss.fillRect(event.getX() - 75, event.getY() - 50, 75,
					// 50);

					// 여기서 가장 가까운 놓는 위치에 흰색 테두리를 칠해야한다.

					//Block tmpNear = NearestBlock(); // 가장 가까운 블록을 찾는다.									
					double x = event.getX();
					double y = event.getY();
					
					int col = ((int)x-50) / 75; // col은 50부터 200씩 건너뛰니까, (x-50) % 200;
					
					int row = (int)Math.floor((y-300) / 50) + 6; // row는 가운데 row가 3이니까, 3을 더하도록 한다.
					
					Guide.setLocation(col*75+50,(row-6)*50 + 300);
					
					

					UIUpdate();
				}
			}

	

		});

		
		
		drawCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (selected == true) {
					System.out.println("내려놓는다");
					Block tmp = new Block(SelectedObject);
					tmp.setLocation((int) Guide.getLocation().getX(), (int) Guide.getLocation().getY());

					// 클릭되는 순간의 좌표를 바탕으로 -> 그곳의 row와 col을 알아내도록 한다.
					double x = event.getX();
					double y = event.getY();
					
					int col = ((int)x-50) / 75; // col은 50부터 200씩 건너뛰니까, (x-50) % 200;
					
					int row = (int)Math.floor((y-300) / 50) + 6; // row는 가운데 row가 3이니까, 3을 더하도록 한다.
					
					System.out.println(col + " " + row);
					
					myBlockList[row][col] = tmp;
					
					UIUpdate();
					
					//myList.getResultList().add(tmp);

				}
				else
				{
					if(event.isMetaDown())
					{
						System.out.println("dddd");
						// 오른쪽 버튼일 경우
						double x = event.getX();
						double y = event.getY();
						
						int col = ((int)x-50) / 75; // col은 50부터 200씩 건너뛰니까, (x-50) % 200;
						
						int row = (int)Math.floor((y-300) / 50) + 6; // row는 가운데 row가 3이니까, 3을 더하도록 한다.
						
						System.out.println(col + " " + row);
						
						myBlockList[row][col] = null;
						
						UIUpdate();
					}
				}
			}

		});
	}

	public void SetSceneEsc() {
		if (myScene == null) {
			System.out.println("What");
		}
		myScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getCode() == KeyCode.ESCAPE) {
					System.out.println("타이밍을보자");
					selected = false;
					UIUpdate();
				}
			}
		});

	}

	// 여기서 블록의 종류를 초기화해줘야 한다.
	public void InitialArray() {
		try {
			System.out.println("num_path = " + num_path.length + " control_path = " + control_path.length + " normal_path = " + normal_path.length);
			for (int i = 0; i < num_path.length; i++) {

				numBlocks.add(new Block(new Image(new FileInputStream(num_path[i])),num_label[i]));

				// NumberBlock을 추가한다. 각각의 숫자가 들어가도록 한다.
			}

			for (int i = 0; i < control_path.length; i++) {
				controlBlocks.add(new Block(new Image(new FileInputStream(control_path[i])),control_label[i]));

			}
			// NormalBlock에 대한 코드.

			for (int i = 0; i < normal_path.length; i++) {
				// controlBlocks.add(new
				normalBlocks.add(new Block(new Image(new FileInputStream(normal_path[i])),normal_label[i]));
				// NumberBlock(i,Color.ALICEBLUE,Integer.toString(i)));
			}
		} catch (Exception e) {
		}
	}

	public void ButtonActionInitial() {
		// 우선 NumberBlock들의 Event를 추가해볼까?

		for (int i = 0; i < numBlocks.size(); i++) {

			numBlocks.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					Block tmp = (Block) event.getSource();
					// System.out.println(tmp.getName());

					selected = true; // 선택 되었음으로 둔다음에.
					selectedBlock = 0; // 선택된 블록의 종류를 0번으로 둔다.

					SelectedObject = tmp;
					// 선택한 객체를 가져온다.

					if (SceneEscSet == false) {
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

		for (int i = 0; i < controlBlocks.size(); i++) {

			controlBlocks.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					Block tmp = (Block) event.getSource();
					// System.out.println(tmp.getName());

					selected = true; // 선택 되었음으로 둔다음에.
					selectedBlock = 0; // 선택된 블록의 종류를 0번으로 둔다.

					SelectedObject = tmp;
					// 선택한 객체를 가져온다.

					if (SceneEscSet == false) {
						myScene = drawCanvas.getScene();
						System.out.println("최초 1회만");
						SetSceneEsc();
						SceneEscSet = true;
					}
					// 이걸 다른 버튼에도 적용해야한다.
				}

			});
		}
		/////

		for (int i = 0; i < normalBlocks.size(); i++) {

			normalBlocks.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					Block tmp = (Block) event.getSource();
					// System.out.println(tmp.getName());

					selected = true; // 선택 되었음으로 둔다음에.
					selectedBlock = 0; // 선택된 블록의 종류를 0번으로 둔다.

					SelectedObject = tmp;
					// 선택한 객체를 가져온다.

					if (SceneEscSet == false) {
						myScene = drawCanvas.getScene();
						System.out.println("최초 1회만");
						SetSceneEsc();
						SceneEscSet = true;
					}
					// 이걸 다른 버튼에도 적용해야한다.
				}

			});
		}

		controlButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				setPanelControl(event);
				// 오른쪽 선택패널의 버튼들을 Control 블록들로 배치한다.
				currentPanel = 1;
			}

		});

		normalButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				setPanelNormal(event);

				currentPanel = 2;
			}

		});

		numButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				setPanelNum(event);

				currentPanel = 0;
			}

		});

		// 초기화 버튼을 설정하는 부분
		initial.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				InitialCanvas(event);
				//myList.RefreshList();
			}
		});

		RunBlock.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				CompileCanvas();
			}

		});

	}

	// 이 메소드를 아래의 3개의 메소드에서 콜하도록 한다.
	// -> 3개로 분리하도록 한다.
	public void setPanelBlocks(ArrayList<Block> input) {
		PanelBox.getChildren().clear();
		// PanelBox 를 초기화 한다.

		PanelBox.setAlignment(Pos.CENTER_LEFT);
		// PanelBox.getChildren() 이걸 이용해서 추가하도록 한다.
		for (int i = 0; i < input.size(); i++) {
			// Button panelButton = new Button(input.get(i).getName());
			PanelBox.setMargin(input.get(i), new Insets(10, 10, 15, 15)); // top,
																			// bottom,
																			// right,
																			// left
			input.get(i).setFitHeight(50);
			input.get(i).setFitWidth(75);
			// panelButton.setPrefSize(input.get(i).getWidth(),
			// input.get(i).getHeight());
			// panelButton.setStyle("-fx-background-color: " +
			// FxUtils.toRGBCode(numColors[i]) + "; -fx-text-fill: white;
			// -fx-font-size: 50; -fx-font-family:NanumSquare ExtraBold;"
			// + "-fx-border-color: white;");
			// panelButton.getStyleClass().add("-fx-border-color: white;
			// -fx-border-radius:50;");
			// 이부분 글씨로 쓰지말고, 그냥 이미지로 쓰는게 나을 것 같다.

			// panelButton.setStyle();
			// panelButton.getStyleClass().add("-fx-background-color: " +
			// FxUtils.toRGBCode(numColors[i]));
			// 색상 부분은 배열을 이용하여서 변경할 수 있다.

			PanelBox.getChildren().add(input.get(i));

			// input.get(i).getStyleClass().add("-fx-border-color: black;");

		}
	}

	// 아래 3개의 함수의 내부는 같게 둔다. 하나의 메소드로 빼서, ArrayList<Block>을 전달하는 것으로 끝내도록한다.
	public void setPanelNormal(ActionEvent event) {
		this.setPanelBlocks(normalBlocks);
	}

	public void setPanelNum(ActionEvent event) {
		this.setPanelBlocks(numBlocks);
	}

	public void setPanelControl(ActionEvent event) {
		this.setPanelBlocks(controlBlocks);
	}

	public void InitialCanvas(ActionEvent event) {
		System.out.println("Canvas를 초기화 하도록 한다");

		GraphicsContext gc = drawCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());

		// 초기화 하는 부분.

		myBlockList = new Block[13][20];
		Block startBlock = null;
		try {
			startBlock = new Block(new Image(new FileInputStream(start_home[0])),"start");
			startBlock.setLocation(50, 300);
			System.out.println("추가가안되었나");
		} catch (Exception e) {
			System.out.println("추가가안되었1111나");
		}

		this.myBlockList[6][0] = startBlock;
		
		
		System.out.println("초기화완료");
	}

	
	public void CompileCanvas()
	{
		System.out.println("Compile Start");
		// 여기서 ArrayList로 만들어서 넘겨줄 것이다.
		
		String result = "";
		
		for(int i=0;i<20;i++)
		{
			if(myBlockList[6][i]!=null)
			{
				if(!myBlockList[6][i].getContents().equals("home"))
					result  = result + myBlockList[6][i].getContents() + " ";
				else
					result = result + myBlockList[6][i].getContents();
				
				if(myBlockList[5][i]!=null) // null 이 아니면 위에가
				{
					if(myBlockList[7][i]!=null) // 아래도 있으면 문제
					{
						System.out.println("컴파일 오류");
					}
					else
					{
						for(int j=5;j>=0;j--)
						{
							if(myBlockList[j][i]!=null)
							{
								result = result + myBlockList[j][i].getContents() + " ";
							}
						}
						
						result = result + ": "; // if문이 끝낫다는 내용
					}
				}
				
				if(myBlockList[7][i]!=null) // null 이 아니면 위에가
				{
					if(myBlockList[5][i]!=null) // 아래도 있으면 문제
					{
						System.out.println("컴파일 오류");
					}
					else
					{
						for(int j=7;j<=12;j++)
						{
							if(myBlockList[j][i]!=null)
							{
								result = result + myBlockList[j][i].getContents() + " ";
							}
						}
						
						result = result + ": "; // if문이 끝낫다는 내용
					}
				}
				
			
			
			
			}
		}
		
		
		System.out.println("결과는 = " + result);
		System.out.println("-----------------------------------------------------------------------------\n\n");
		
		final String reallyresult = result;
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Compiler cm = new Compiler(reallyresult);
			}
			
		}).start();
	
		
	}
	
}
