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

	private String[] control_path = { "loop1.png", "loop2.png", "branchdown1.png", "branchdown2.png", "branchup1.png",
			"branchup2.png", "jump1.png", "jump2.png", "equal.png", "upper.png", "lower.png" };

	private String[] normal_path = { "bulb1.png", "speaker2.png" };

	private String[] start_home = { "start.png", "home.png" };

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// myScene = drawCanvas.getScene();
		Block startBlock = null;
		try {
			startBlock = new Block(new Image(new FileInputStream(start_home[0])));
			startBlock.setLocation(50, 150);
			System.out.println("�߰����ȵǾ���");
		} catch (Exception e) {
			System.out.println("�߰����ȵǾ�1111��");
		}

		myList.getResultList().add(startBlock);
		// �ʱ��� ������ ����

		InitialArray(); // 3���� ArrayList�� �ʱ�ȭ �ϴ� �۾� -> ���� �ʿ�
		// �̰� ���� ��ϵ��� �����ϱ� �����̴�.

		ButtonActionInitial();
		// ���� ��ư���� �ʱ�ȭ �ϱ� �����̴�. ��ü�� �ӽ÷� ��Ƽ� �����´ٴ� �߻��� ���Ծ��� �����ϵ��� �Ѵ�.

		setPanelBlocks(numBlocks);
		// Default��, Panel�� ��ϵ��� ����� ���� ��ϵ�� ��ġ�ϵ��� �Ѵ�.

		SetCanvasEvent();
		// �̰� �� �ٽ� ¥���Ѵ�.
		StartUIUpdate();

	}

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
						for (int i = 0; i < myList.getResultList().size(); i++) {
							myList.getResultList().get(i).Draw(gss);
						}

						if (selected == true) {
							SelectedObject.Draw(gss);

							gss.setStroke(Color.BLACK);
							gss.setLineWidth(2.0);
							gss.strokeRect(Guide.getLocation().getX(), Guide.getLocation().getY(), 150, 100);
						}
					});

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		};

		UIThread.start();
	}

	public void SetCanvasEvent() {
		
		drawCanvas.setWidth(2000);
		drawCanvas.setHeight(2000);
		
		drawCanvas.setOnMouseMoved(new EventHandler<MouseEvent>() {

			int minindex = 0;

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub

				if (selected == true) // ���õǾ������� ������ �� �ִ�.
				{
					SelectedObject.setLocation((int) event.getX() - 75, (int) event.getY() - 50);
					// gss.fillRect(event.getX() - 75, event.getY() - 50, 150,
					// 100);

					// ���⼭ ���� ����� ���� ��ġ�� ��� �׵θ��� ĥ�ؾ��Ѵ�.

					Block tmpNear = NearestBlock(); // ���� ����� ����� ã�´�.

					if (minindex == 0) // �׳� ���� ���� ��ư �ϳ��� �ִ� ���
					{
						Guide.setLocation((int) myList.getResultList().get(0).getLocation().getX() + 170,
								(int) myList.getResultList().get(0).getLocation().getY());
					} else {
						if (minindex == myList.getResultList().size() - 1) // ������
																			// ���ΰ��
						{
							// ���⿡ ������?
							System.out.println("�̾��������縮");
							Point2D lastblock = myList.getResultList().get(minindex).getLocation();
							Point2D rightblock_pos = new Point2D(lastblock.getX() + 170, lastblock.getY());
							Point2D upperblock_pos = new Point2D(lastblock.getX(), lastblock.getY() - 110);
							Point2D lowblock_pos = new Point2D(lastblock.getX(), lastblock.getY() + 110);

							double rightp = SelectedObject.getLocation().distance(rightblock_pos);
							double upperp = SelectedObject.getLocation().distance(upperblock_pos);
							double lowp = SelectedObject.getLocation().distance(lowblock_pos);

							if (rightp < upperp) {
								if (lowp < rightp) {
									Guide.setLocation((int) lowblock_pos.getX(), (int) lowblock_pos.getY());
								} else if (lowp >= rightp) {
									Guide.setLocation((int) rightblock_pos.getX(), (int) rightblock_pos.getY());
								}
							} else if (rightp >= upperp) {
								if (lowp < upperp) {
									Guide.setLocation((int) lowblock_pos.getX(), (int) lowblock_pos.getY());
								} else {
									Guide.setLocation((int) upperblock_pos.getX(), (int) upperblock_pos.getY());
								}
							}

						} else {
							double updown_dis = myList.getResultList().get(minindex).getLocation()
									.distance(SelectedObject.getLocation());
							double side_dis = myList.getResultList().get(myList.getResultList().size() - 1)
									.getLocation().distance(SelectedObject.getLocation());

							System.out.println("���� ����� �ε�����" + minindex + " side_dis = " + side_dis + ", updown_dis = "
									+ updown_dis);

							if (side_dis <= updown_dis) {
								Guide.setLocation(
										(int) myList.getResultList().get(myList.getResultList().size() - 1)
												.getLocation().getX() + 170,
										(int) myList.getResultList().get(myList.getResultList().size() - 1)
												.getLocation().getY());
							} else {
								Guide.setLocation((int) myList.getResultList().get(minindex).getLocation().getX(),
										(int) myList.getResultList().get(minindex).getLocation().getY() - 110);
							}
						}
					}

				}
			}

			public Block NearestBlock() {
				double minDis = 9999999.0;
				minindex = 0;
				for (int i = 1; i < myList.getResultList().size(); i++) {
					// ��ȸ�ϸ鼭 ���� ����� ����� ã�´�. ����� �������� ����Ѵ�.

					double tmpdis = myList.getResultList().get(i).getLocation().distance(SelectedObject.getLocation());
					if (tmpdis < minDis) {
						minDis = tmpdis;
						minindex = i;
					}
				}

				return myList.getResultList().get(minindex);
			}

		});

		drawCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (selected == true) {
					Block tmp = new Block(SelectedObject);
					tmp.setLocation((int) Guide.getLocation().getX(), (int) Guide.getLocation().getY());

					myList.getResultList().add(tmp);

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
				}
			}
		});

	}

	// ���⼭ ����� ������ �ʱ�ȭ����� �Ѵ�.
	public void InitialArray() {
		try {
			for (int i = 0; i < num_path.length; i++) {

				numBlocks.add(new Block(new Image(new FileInputStream(num_path[i]))));

				// NumberBlock�� �߰��Ѵ�. ������ ���ڰ� ������ �Ѵ�.
			}

			for (int i = 0; i < control_path.length; i++) {
				controlBlocks.add(new Block(new Image(new FileInputStream(control_path[i]))));

			}
			// NormalBlock�� ���� �ڵ�.

			for (int i = 0; i < normal_path.length; i++) {
				// controlBlocks.add(new
				normalBlocks.add(new Block(new Image(new FileInputStream(normal_path[i]))));
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
				myList.RefreshList();
			}
		});

		RunBlock.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

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
			input.get(i).setFitHeight(100);
			input.get(i).setFitWidth(150);
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

		System.out.println("�ʱ�ȭ�Ϸ�");
	}

}
