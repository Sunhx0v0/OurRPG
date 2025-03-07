package application;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class map1controller implements Initializable {
	// ������FXML
	@FXML
	private ImageView cunzhangimage;

	@FXML
	private ImageView map1image;

	@FXML
	private ImageView mainrole;

	@FXML
	private Button xiaomeiButton;

	@FXML
	private Label xiaomeiLabel;

	@FXML
	private Button cunzhangButton;

	@FXML
	private ImageView xiaomeiimage;

	@FXML
	private ImageView tpToMap2;

	@FXML
	private Label cunzhangLabel;

	@FXML
	private AnchorPane mainpane;
	// �����ͼ����
//	public int modiy=16;
	public double x_leftBound = 70, x_rightBound = 805.5, y_topBound = 13.5, y_bottomBound = 745.5;
	public Image downImage = new Image(new File("image/down.png").toURI().toString());
	public Image upImage = new Image(new File("image/up.png").toURI().toString());
	public Image leftImage = new Image(new File("image/left.png").toURI().toString());
	public Image rightImage = new Image(new File("image/right.png").toURI().toString());
	public boolean U = false;
	public boolean D = false;
	public boolean L = false;
	public boolean R = false;
	public boolean needReset = false;
	private Block[] blocks;
	private TP[] tps;
	private NPC[] npcs;
	private String[] plots;
	private boolean leaved = false;

	// ����:�����ݿ���ص�ͼ�ϰ���
	private void loadBlocks() {
		try { // ��дͳһ��¼�ӿ�
			DBOperator dbop = new DBOperator();
			// SQL���
			String sql = "SELECT Left,Right,Top,Bottom FROM blocks";
			ResultSet rs = dbop.sqlQuery(sql);
			// �洢��ѯ�����
			float left, right, top, bottom;
			ArrayList<Block> blocklist = new ArrayList<Block>();
			while (rs.next()) {
				// ���ÿһ����ÿһ�е�����
				// �˴���Ҳ������id=rs.getString("id"),������ͬ
				// account = rs.getString("account");
				left = rs.getFloat("Left");
				right = rs.getFloat("Right");
				top = rs.getFloat("Top");
				bottom = rs.getFloat("Bottom");
				Block b = new Block(left, right, top, bottom);
				blocklist.add(b);
			}
			// �رն���
			rs.close();
			blocks = (Block[]) blocklist.toArray(new Block[blocklist.size()]);
		} catch (SQLException e) {
			System.out.println("����" + e);
		}
	}

	// ����:�����ݿ���ص�ͼ���͵�
	private void loadTP_NPC() {
		try { // ��дͳһ��¼�ӿ�
			DBOperator dbop = new DBOperator();
			// SQL���
			// ����tp��
			String sql = "SELECT x,y,tomap FROM tps where atmap='map1'";
			ResultSet rs = dbop.sqlQuery(sql);
			// �洢��ѯ�����
			double x, y;
			String to;
			ArrayList<TP> TPlist = new ArrayList<TP>();
			while (rs.next()) {
				// ���ÿһ����ÿһ�е�����
				// �˴���Ҳ������id=rs.getString("id"),������ͬ
				// account = rs.getString("account");
				x = rs.getDouble("x");
				y = rs.getDouble("y");
				to = rs.getString("tomap");
				TP tpPoint = new TP(x, y, to);
				TPlist.add(tpPoint);
			}
			tps = (TP[]) TPlist.toArray(new TP[TPlist.size()]);
			// SQL���
			// ����NPC
			sql = "SELECT x,y,name FROM NPC where atmap='map1'";
			rs = dbop.sqlQuery(sql);
			// �洢��ѯ�����
			String name;
			ArrayList<NPC> NPClist = new ArrayList<NPC>();
			while (rs.next()) {
				// ���ÿһ����ÿһ�е�����
				// �˴���Ҳ������id=rs.getString("id"),������ͬ
				// account = rs.getString("account");
				x = rs.getDouble("x");
				y = rs.getDouble("y");
				name = rs.getString("name");
				NPC npc = new NPC(x, y, name);
				NPClist.add(npc);
			}
			// �رն���
			rs.close();
			npcs = (NPC[]) NPClist.toArray(new NPC[NPClist.size()]);
		} catch (SQLException e) {
			System.out.println("����" + e);
		}

	}

	// ����:�����ݿ���ؾ���
	private void loadPlots() {
		try { // ��дͳһ��¼�ӿ�
			DBOperator dbop = new DBOperator();
			// SQL���
			String sql = "SELECT content FROM Plot";
			ResultSet rs = dbop.sqlQuery(sql);
			// �洢��ѯ�����
			String c;
			ArrayList<String> plotList = new ArrayList<String>();
			while (rs.next()) {
				// ���ÿһ����ÿһ�е�����
				// �˴���Ҳ������id=rs.getString("id"),������ͬ
				// account = rs.getString("account");
				c = rs.getString("content");
				plotList.add(c);
			}
			// �رն���
			rs.close();
			plots = (String[]) plotList.toArray(new String[plotList.size()]);
		} catch (SQLException e) {
			System.out.println("����" + e);
		}
	}

	// ����:�ж��Ƿ������ϰ���
	private boolean collision(double x, double y) {
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i].inAera(x, y))
				return true;
		}
		return false;
	}

	//
	private void runlaterExchangeScene(double x, double y) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// ����JavaFX�����̵߳Ĵ�����ڴ˴�
				// ������ͼ����
				for (int i = 0; i < tps.length; i++) {
					if (tps[i].inAera(x, y)) {
						System.out.println("map1controller.listen.run()");
						Stage primaryStage = (Stage) mainrole.getScene().getWindow();

						Scene newScene = tps[i].TPto();
						// ���µ�ͼע��˵������¼�
						newScene.setOnKeyPressed(e -> {
							// ���¡�ESC����
							System.out.println(e.getCode());
							if (e.getCode() == KeyCode.ESCAPE) {
								if (!gameStage.menuDisplay) {
									try {
										new menuStage().start(new Stage());
										gameStage.menuDisplay = true;
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							}
						});
						// ˢ�µ�ͼ
						primaryStage.setScene(newScene);
						leaved = true;
					}
				}
				// ����npc
				for (int i = 0; i < npcs.length; i++) {
					if (npcs[i].inAera(x, y)) {
						switch (npcs[i].getName()) {
						case "С��":
							if (gameStage.progress < 25 || gameStage.progress > 77)
								xiaomeiButton.setVisible(true);
							break;
						case "�峤":
							cunzhangButton.setVisible(true);
							break;
						default:
							break;
						}
					}
					if (!npcs[i].inAera(x, y)) {
						switch (npcs[i].getName()) {
						case "С��":
							xiaomeiButton.setVisible(false);
							xiaomeiLabel.setVisible(false);
							break;
						case "�峤":
							cunzhangButton.setVisible(false);
							cunzhangLabel.setVisible(false);
							break;
						default:
							break;
						}
					}

				}
			}
		});
	}

	// �ڲ��ࣺ���߳���·������
	class walk implements Runnable {
		private double step = 1.5;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (!leaved) {
				if (!U && !D && L && !R) { // A
					System.out.println("moving left!");
					if (needReset) {
						mainrole.setImage(leftImage);
						needReset = false;
					}
					double x = mainrole.getLayoutX() - step;
					double y = mainrole.getLayoutY();
					if (x >= x_leftBound && !collision(x, y)) {
						mainrole.setLayoutX(x);
					}
				} else if (!U && !D && !L && R) { // D
					System.out.println("moving right!");
					if (needReset) {
						mainrole.setImage(rightImage);
						needReset = false;
					}
					double x = mainrole.getLayoutX() + step;
					double y = mainrole.getLayoutY();
					if (x <= x_rightBound && !collision(x, y)) {
						mainrole.setLayoutX(x);
					}
				} else if (U && !D && !L && !R) { // W
					System.out.println("moving up!");
					if (needReset) {
						mainrole.setImage(upImage);
						needReset = false;
					}
					double x = mainrole.getLayoutX();
					double y = mainrole.getLayoutY() - step;
					if (y >= y_topBound && !collision(x, y)) {
						mainrole.setLayoutY(y);
					}
				} else if (!U && D && !L && !R) { // S
					System.out.println("moving down!");
					if (needReset) {
						mainrole.setImage(downImage);
						needReset = false;
					}
					double x = mainrole.getLayoutX();
					double y = mainrole.getLayoutY() + step;
					if (y <= y_bottomBound && !collision(x, y)) {
						mainrole.setLayoutY(y);
					}
				} else if (U && !D && L && !R) { // AW
					if (needReset) {
						mainrole.setImage(upImage);
						needReset = false;
					}
					double x = mainrole.getLayoutX() - step;
					double y = mainrole.getLayoutY() - step;
					if (x >= x_leftBound && !collision(x, mainrole.getLayoutY())) {
						mainrole.setLayoutX(x);
					}
					if (y >= y_topBound && !collision(mainrole.getLayoutX(), y)) {
						mainrole.setLayoutY(y);
					}
				} else if (!U && D && L && !R) { // AS
					if (needReset) {
						mainrole.setImage(downImage);
						needReset = false;
					}
					double x = mainrole.getLayoutX() - step;
					double y = mainrole.getLayoutY() + step;
					if (x >= x_leftBound && !collision(x, mainrole.getLayoutY())) {
						mainrole.setLayoutX(x);
					}
					if (y <= y_bottomBound && !collision(mainrole.getLayoutX(), y)) {
						mainrole.setLayoutY(y);
					}
				} else if (U && !D && !L && R) { // WD
					if (needReset) {
						mainrole.setImage(upImage);
						needReset = false;
					}
					double x = mainrole.getLayoutX() + step;
					double y = mainrole.getLayoutY() - step;
					if (y >= y_topBound && !collision(mainrole.getLayoutX(), y)) {
						mainrole.setLayoutY(y);
					}
					if (x <= x_rightBound && !collision(x, mainrole.getLayoutY())) {
						mainrole.setLayoutX(x);
					}
				} else if (!U && D && !L && R) { // SD
					if (needReset) {
						mainrole.setImage(downImage);
						needReset = false;
					}
					double x = mainrole.getLayoutX() + step;
					double y = mainrole.getLayoutY() + step;
					if (x <= x_rightBound && !collision(x, mainrole.getLayoutY())) {
						mainrole.setLayoutX(x);
					}
					if (y <= y_bottomBound && !collision(mainrole.getLayoutX(), y)) {
						mainrole.setLayoutY(y);
					}
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// �ڲ��ࣺ���̼߳�����ͼ�����¼�
	class listen implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (!leaved) {
				if (!leaved) {
					runlaterExchangeScene(mainrole.getLayoutX(), mainrole.getLayoutY());
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		// ��ʼ��
		leaved = false;
		// ����ʼ��ͼ
		File file1 = new File("image/map1.png");
		File file2 = new File("image/tp.png");
		Image image1 = new Image(file1.toURI().toString());
		Image image2 = new Image(file2.toURI().toString());
		map1image.setImage(image1);
		tpToMap2.setImage(image2);

		// ������
//        mainrole.setImage(downImage);

		// ����
		loadBlocks();// �ϰ���
		loadTP_NPC();// ��ͼ���͵�
		loadPlots();// ���ؾ���

		// Ӳ����npc
//        С��(784.0,287.0)
//        �峤(254.5,674.0)

		// �½��̣߳���·
		walk w = new walk();
		Thread walkThread = new Thread(w);
		walkThread.start();

		// �½��̣߳������������ͼ����
		listen l = new listen();
		Thread listenThread = new Thread(l);
		listenThread.start();

		if (gameStage.progress >= 25 && gameStage.progress < 77) {
			xiaomeiimage.setVisible(false);
			xiaomeiButton.setVisible(false);
			xiaomeiLabel.setVisible(false);
		}

	}

	// ����WSAD��·
	@FXML
	void keydown(KeyEvent event) {
		if (event.getCode() == KeyCode.W) {
			mainrole.setImage(upImage);
			U = true;
		} else if (event.getCode() == KeyCode.S) {
			mainrole.setImage(downImage);
			D = true;
		} else if (event.getCode() == KeyCode.A) {
			mainrole.setImage(leftImage);
			L = true;
		} else if (event.getCode() == KeyCode.D) {
			mainrole.setImage(rightImage);
			R = true;
		}
	}

	@FXML
	void keyrelease(KeyEvent event) {
		if (event.getCode() == KeyCode.W) {
			U = false;
		} else if (event.getCode() == KeyCode.S) {
			D = false;
		} else if (event.getCode() == KeyCode.A) {
			L = false;
		} else if (event.getCode() == KeyCode.D) {
			R = false;
		}
		if (U || D || L || R) {
			needReset = true;
		}
		System.out.println("(" + mainrole.getLayoutX() + "," + mainrole.getLayoutY() + ")");
	}

	@FXML
	void cunzhangButtonCliced(MouseEvent event) {
		// Ӳ����峤����
		if (gameStage.progress < 11) {
			cunzhangLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
		}
		System.out.println("map1controller.cunzhangButtonCliced()");
		cunzhangLabel.setVisible(true);
	}

	@FXML
	void xiaomeiButtonCliced(MouseEvent event) {
		if (gameStage.progress == 0) {
			xiaomeiLabel.setText("�԰ף���ȥ�Ҵ峤�ɣ��������л�����˵......");
		}
		if (gameStage.progress >= 11 && gameStage.progress < 17) {
			xiaomeiLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
		}
		System.out.println("map1controller.xiaomeiButtonCliced()");
		xiaomeiLabel.setVisible(true);
	}

}
