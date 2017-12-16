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

	// 2017 - 12 - 05 �߰� ����
	
	// ArrayList���� �迭�� ���� ����
	private Block[][] myBlockList = new Block[13][20];
	// ���߿� �÷����ϴ°� ������, �켱�����δ� �̷��� �Ѿ��.
	
	
	
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
	// ����, ����, ���� , ��� ��ư�� ������ �������� ����â�� �ʱ�ȭ �ϱ� �����̴�.

	private CanvasBlockList myList = new CanvasBlockList();

	@FXML
	private Canvas drawCanvas; // ��ϵ��� �׷����� Canvas
	// @FXML private Group drawGroup; // Group�� � �뵵�� ���Ǵ��� �𸣰ڴ�. ������, �ϴ� �߰���
	// ����.

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

	Thread UIThread = null;

	private Block SelectedObject = null;

	private boolean selected = false;
	private boolean SceneEscSet = false;

	private Image[] num_imgs = null;
	private Image[] control_imgs = null;
	private Image[] normal_imgs = null;
	// Image���� �����ϱ� �����̴�.

	private Block Guide = new Block();

	private String[] num_path = { "variable1.png", "variable2.png", "variable3.png", "variable4.png", "num00.png",
			"num01.png", "num02.png", "num03.png", "num04.png", "num05.png", "num06.png", "num07.png", "num08.png",
			"num09.png" };

	private String[] num_label = { "��","��","��","��","0","1","2","3","4","5","6","7","8","9"};
	
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
			System.out.println("�߰����ȵǾ���");
		} catch (Exception e) {
			System.out.println("�߰����ȵǾ�1111��");
		}

		this.myBlockList[6][0] = startBlock;
		// �ʱ��� ������ ����

		InitialArray(); // 3���� ArrayList�� �ʱ�ȭ �ϴ� �۾� -> ���� �ʿ�
		// �̰� ���� ��ϵ��� �����ϱ� �����̴�.

		ButtonActionInitial();
		// ���� ��ư���� �ʱ�ȭ �ϱ� �����̴�. ��ü�� �ӽ÷� ��Ƽ� �����´ٴ� �߻��� ���Ծ��� �����ϵ��� �Ѵ�.

		setPanelBlocks(numBlocks);
		// Default��, Panel�� ��ϵ��� ����� ���� ��ϵ�� ��ġ�ϵ��� �Ѵ�.

		SetCanvasEvent();
		// �̰� �� �ٽ� ¥���Ѵ�.
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
					// UI update�� ��� �����ؾ� �Ѵ�.
					Platform.runLater(() -> {
						// Runnable ��ü�� �Ѱ������ν�, UI ������ �����ϰ� �Ѵ�.

						GraphicsContext gss = drawCanvas.getGraphicsContext2D();
						gss.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());

						// System.out.println("������� = " +
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

				if (selected == true) // ���õǾ������� ������ �� �ִ�.
				{
					SelectedObject.setLocation((int) event.getX() - 37, (int) event.getY() - 25);
					// gss.fillRect(event.getX() - 75, event.getY() - 50, 75,
					// 50);

					// ���⼭ ���� ����� ���� ��ġ�� ��� �׵θ��� ĥ�ؾ��Ѵ�.

					//Block tmpNear = NearestBlock(); // ���� ����� ����� ã�´�.									
					double x = event.getX();
					double y = event.getY();
					
					int col = ((int)x-50) / 75; // col�� 50���� 200�� �ǳʶٴϱ�, (x-50) % 200;
					
					int row = (int)Math.floor((y-300) / 50) + 6; // row�� ��� row�� 3�̴ϱ�, 3�� ���ϵ��� �Ѵ�.
					
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
					System.out.println("�������´�");
					Block tmp = new Block(SelectedObject);
					tmp.setLocation((int) Guide.getLocation().getX(), (int) Guide.getLocation().getY());

					// Ŭ���Ǵ� ������ ��ǥ�� �������� -> �װ��� row�� col�� �˾Ƴ����� �Ѵ�.
					double x = event.getX();
					double y = event.getY();
					
					int col = ((int)x-50) / 75; // col�� 50���� 200�� �ǳʶٴϱ�, (x-50) % 200;
					
					int row = (int)Math.floor((y-300) / 50) + 6; // row�� ��� row�� 3�̴ϱ�, 3�� ���ϵ��� �Ѵ�.
					
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
						// ������ ��ư�� ���
						double x = event.getX();
						double y = event.getY();
						
						int col = ((int)x-50) / 75; // col�� 50���� 200�� �ǳʶٴϱ�, (x-50) % 200;
						
						int row = (int)Math.floor((y-300) / 50) + 6; // row�� ��� row�� 3�̴ϱ�, 3�� ���ϵ��� �Ѵ�.
						
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
					System.out.println("Ÿ�̹�������");
					selected = false;
					UIUpdate();
				}
			}
		});

	}

	// ���⼭ ����� ������ �ʱ�ȭ����� �Ѵ�.
	public void InitialArray() {
		try {
			System.out.println("num_path = " + num_path.length + " control_path = " + control_path.length + " normal_path = " + normal_path.length);
			for (int i = 0; i < num_path.length; i++) {

				numBlocks.add(new Block(new Image(new FileInputStream(num_path[i])),num_label[i]));

				// NumberBlock�� �߰��Ѵ�. ������ ���ڰ� ������ �Ѵ�.
			}

			for (int i = 0; i < control_path.length; i++) {
				controlBlocks.add(new Block(new Image(new FileInputStream(control_path[i])),control_label[i]));

			}
			// NormalBlock�� ���� �ڵ�.

			for (int i = 0; i < normal_path.length; i++) {
				// controlBlocks.add(new
				normalBlocks.add(new Block(new Image(new FileInputStream(normal_path[i])),normal_label[i]));
				// NumberBlock(i,Color.ALICEBLUE,Integer.toString(i)));
			}
		} catch (Exception e) {
		}
	}

	public void ButtonActionInitial() {
		// �켱 NumberBlock���� Event�� �߰��غ���?

		for (int i = 0; i < numBlocks.size(); i++) {

			numBlocks.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					Block tmp = (Block) event.getSource();
					// System.out.println(tmp.getName());

					selected = true; // ���� �Ǿ������� �д�����.
					selectedBlock = 0; // ���õ� ����� ������ 0������ �д�.

					SelectedObject = tmp;
					// ������ ��ü�� �����´�.

					if (SceneEscSet == false) {
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

		for (int i = 0; i < controlBlocks.size(); i++) {

			controlBlocks.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					Block tmp = (Block) event.getSource();
					// System.out.println(tmp.getName());

					selected = true; // ���� �Ǿ������� �д�����.
					selectedBlock = 0; // ���õ� ����� ������ 0������ �д�.

					SelectedObject = tmp;
					// ������ ��ü�� �����´�.

					if (SceneEscSet == false) {
						myScene = drawCanvas.getScene();
						System.out.println("���� 1ȸ��");
						SetSceneEsc();
						SceneEscSet = true;
					}
					// �̰� �ٸ� ��ư���� �����ؾ��Ѵ�.
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

					selected = true; // ���� �Ǿ������� �д�����.
					selectedBlock = 0; // ���õ� ����� ������ 0������ �д�.

					SelectedObject = tmp;
					// ������ ��ü�� �����´�.

					if (SceneEscSet == false) {
						myScene = drawCanvas.getScene();
						System.out.println("���� 1ȸ��");
						SetSceneEsc();
						SceneEscSet = true;
					}
					// �̰� �ٸ� ��ư���� �����ؾ��Ѵ�.
				}

			});
		}

		controlButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				setPanelControl(event);
				// ������ �����г��� ��ư���� Control ��ϵ�� ��ġ�Ѵ�.
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

		// �ʱ�ȭ ��ư�� �����ϴ� �κ�
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

	// �� �޼ҵ带 �Ʒ��� 3���� �޼ҵ忡�� ���ϵ��� �Ѵ�.
	// -> 3���� �и��ϵ��� �Ѵ�.
	public void setPanelBlocks(ArrayList<Block> input) {
		PanelBox.getChildren().clear();
		// PanelBox �� �ʱ�ȭ �Ѵ�.

		PanelBox.setAlignment(Pos.CENTER_LEFT);
		// PanelBox.getChildren() �̰� �̿��ؼ� �߰��ϵ��� �Ѵ�.
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
			// �̺κ� �۾��� ��������, �׳� �̹����� ���°� ���� �� ����.

			// panelButton.setStyle();
			// panelButton.getStyleClass().add("-fx-background-color: " +
			// FxUtils.toRGBCode(numColors[i]));
			// ���� �κ��� �迭�� �̿��Ͽ��� ������ �� �ִ�.

			PanelBox.getChildren().add(input.get(i));

			// input.get(i).getStyleClass().add("-fx-border-color: black;");

		}
	}

	// �Ʒ� 3���� �Լ��� ���δ� ���� �д�. �ϳ��� �޼ҵ�� ����, ArrayList<Block>�� �����ϴ� ������ ���������Ѵ�.
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
		System.out.println("Canvas�� �ʱ�ȭ �ϵ��� �Ѵ�");

		GraphicsContext gc = drawCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());

		// �ʱ�ȭ �ϴ� �κ�.

		myBlockList = new Block[13][20];
		Block startBlock = null;
		try {
			startBlock = new Block(new Image(new FileInputStream(start_home[0])),"start");
			startBlock.setLocation(50, 300);
			System.out.println("�߰����ȵǾ���");
		} catch (Exception e) {
			System.out.println("�߰����ȵǾ�1111��");
		}

		this.myBlockList[6][0] = startBlock;
		
		
		System.out.println("�ʱ�ȭ�Ϸ�");
	}

	
	public void CompileCanvas()
	{
		System.out.println("Compile Start");
		// ���⼭ ArrayList�� ���� �Ѱ��� ���̴�.
		
		String result = "";
		
		for(int i=0;i<20;i++)
		{
			if(myBlockList[6][i]!=null)
			{
				if(!myBlockList[6][i].getContents().equals("home"))
					result  = result + myBlockList[6][i].getContents() + " ";
				else
					result = result + myBlockList[6][i].getContents();
				
				if(myBlockList[5][i]!=null) // null �� �ƴϸ� ������
				{
					if(myBlockList[7][i]!=null) // �Ʒ��� ������ ����
					{
						System.out.println("������ ����");
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
						
						result = result + ": "; // if���� �����ٴ� ����
					}
				}
				
				if(myBlockList[7][i]!=null) // null �� �ƴϸ� ������
				{
					if(myBlockList[5][i]!=null) // �Ʒ��� ������ ����
					{
						System.out.println("������ ����");
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
						
						result = result + ": "; // if���� �����ٴ� ����
					}
				}
				
			
			
			
			}
		}
		
		
		System.out.println("����� = " + result);
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
