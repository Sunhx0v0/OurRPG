package application;

import java.io.File;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.map1controller.listen;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class map2controller implements Initializable {
	// fxml�Զ���������
	@FXML
	private Label dashuLabel;

	@FXML
	private AnchorPane mainpane2;

	@FXML
	private ImageView mainrole;

	@FXML
	private Button cxkButton;

	@FXML
	private Label cxkLabel;

	@FXML
	private Button penhuolongButton;

	@FXML
	private Label kulouLabel;

	@FXML
	private Button shenmirenButton;

	@FXML
	private Label shenmirenLabel;

	@FXML
	private Label penhuolongLabel;

	@FXML
	private Button kulouButton;

	@FXML
	private Button dashuButton;

	@FXML
	private ImageView tpToMap1;

	@FXML
	private Button liandaoButton;

	@FXML
	private ImageView map2image;

	@FXML
	private Label shujingLabel;

	@FXML
	private Button shujingButton;

	@FXML
	private Label liandaoLabel;

	@FXML
	private ImageView shujingImage;

	@FXML
	private ImageView cxkImage;

	@FXML
	private ImageView dragonImage;

	@FXML
	private ImageView kulouImage;

	@FXML
	private ImageView liandaoImage;
	// ��ͼ����
	public double x_leftBound = 0, x_rightBound = 845, y_topBound = 10, y_bottomBound = 745;
	// �����������ҵ�ͼƬ
	public Image downImage = new Image(new File("image/down.png").toURI().toString());
	public Image upImage = new Image(new File("image/up.png").toURI().toString());
	public Image leftImage = new Image(new File("image/left.png").toURI().toString());
	public Image rightImage = new Image(new File("image/right.png").toURI().toString());
	// �ĸ������ܷ���
	public boolean U = false;
	public boolean D = false;
	public boolean L = false;
	public boolean R = false;

	//
	public boolean needReset = false;
	private Block[] blocks2;
	private TP[] tps;
	private NPC[] npcs;
	private String[] plots;
	private boolean leaved = false;

	// �ڲ��ࣺ����ǽ����ϰ���
	class Block {
		private double left, right, top, bottom;

		public Block(float l, float r, float t, float b) {
			// TODO Auto-generated constructor stub
			left = l;
			right = r;
			top = t;
			bottom = b;
		}

		public boolean inAera(double x, double y) {
			if (x >= left && x <= right && y >= top && y <= bottom)
				return true;
			else {
				return false;
			}
		}
	}

	// ����:�����ݿ���ص�ͼ�ϰ���
	private void loadBlocks() {
		try { // ��дͳһ��¼�ӿ�
			DBOperator dbop = new DBOperator();
			// SQL���
			String sql = "SELECT Left, Right, Top, Bottom FROM blocks2";
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
				Block b2 = new Block(left, right, top, bottom);
				blocklist.add(b2);
			}
			// �رն���
			rs.close();
			blocks2 = (Block[]) blocklist.toArray(new Block[blocklist.size()]);
		} catch (SQLException e) {
			System.out.println("����" + e);
		}
	}

	// ����:�����ݿ���ص�ͼ���͵�
	private void loadTP_NPC() {
		try { // ��дͳһ��¼�ӿ�
			DBOperator dbop = new DBOperator();
			// SQL���
			String sql = "SELECT x,y,tomap FROM tps where atmap='map2'";
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
			sql = "SELECT x,y,name FROM NPC where atmap='map2'";
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
			npcs = (NPC[]) NPClist.toArray(new NPC[NPClist.size()]);
			// �رն���
			rs.close();
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
		for (int i = 0; i < blocks2.length; i++) {
			if (blocks2[i].inAera(x, y))
				return true;
		}
		return false;
	}

	private void runlaterExchangeScene(double x, double y) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// ����JavaFX�����̵߳Ĵ�����ڴ˴�
				// ���ȼ�����ͼ����
				for (int i = 0; i < tps.length; i++) {
					if (tps[i].inAera(x, y)) {
						System.out.println("map2controller.listen.run()");
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
				// �ټ���npc
				for (int i = 0; i < npcs.length; i++) {
					if (npcs[i].inAera(x, y)) {
						switch (npcs[i].getName()) {
						case "�������":
							dashuButton.setVisible(true);
							break;
						case "������":
							shenmirenButton.setVisible(true);
							break;
						case "����":
							if (gameStage.progress <= 28)
								shujingButton.setVisible(true);
							break;
						case "�����":
							if (gameStage.progress <= 49)
								penhuolongButton.setVisible(true);
							break;
						case "����":
							if (gameStage.progress <= 62)
								kulouButton.setVisible(true);
							break;
						case "������ʿ":
							if (gameStage.progress <= 69)
								liandaoButton.setVisible(true);
							break;
						case "ż����ϰ��":
							if (gameStage.progress <= 79)
								cxkButton.setVisible(true);
							break;
						default:
							break;
						}
					}
					if (!npcs[i].inAera(x, y)) {
						switch (npcs[i].getName()) {
						case "�������":
							dashuButton.setVisible(false);
							dashuLabel.setVisible(false);
							break;
						case "����":
							shujingButton.setVisible(false);
							shujingLabel.setVisible(false);
							break;
						case "�����":
							penhuolongButton.setVisible(false);
							penhuolongLabel.setVisible(false);
							break;
						case "������":
							shenmirenButton.setVisible(false);
							shenmirenLabel.setVisible(false);
							break;
						case "����":
							kulouButton.setVisible(false);
							kulouLabel.setVisible(false);
							break;
						case "������ʿ":
							liandaoButton.setVisible(false);
							liandaoLabel.setVisible(false);
							break;
						case "ż����ϰ��":
							cxkButton.setVisible(false);
							cxkLabel.setVisible(false);
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
		// ����ʼ��ͼ
		File file2 = new File("image/map2.png");
		File tpfile = new File("image/tp.png");
		Image image2 = new Image(file2.toURI().toString());
		Image tpImage = new Image(tpfile.toURI().toString());
		map2image.setImage(image2);
		tpToMap1.setImage(tpImage);

		// ������
		mainrole.setImage(downImage);

		// ����
		loadBlocks();// �ϰ���
		loadTP_NPC();// tp��
		loadPlots();// ����

		// �½��̣߳���·
		walk w = new walk();
		Thread walkThread = new Thread(w);
		walkThread.start();

		// �½��̣߳������������ͼ����
		listen l = new listen();
		Thread listenThread = new Thread(l);
		listenThread.start();
		System.out.println("(" + mainrole.getLayoutX() + "," + mainrole.getLayoutY() + ")");
		
		if (gameStage.progress >= 25) {
			shujingButton.setVisible(false);
			shujingImage.setVisible(false);
		}
		if (gameStage.progress > 47) {
			penhuolongButton.setVisible(false);
			dragonImage.setVisible(false);
		}
		if (gameStage.progress > 61) {
			kulouButton.setVisible(false);
			kulouImage.setVisible(false);
		}
		if (gameStage.progress > 67) {
			liandaoButton.setVisible(false);
			liandaoImage.setVisible(false);
		}
		if (gameStage.progress > 77) {
			cxkButton.setVisible(false);
			cxkImage.setVisible(false);
		}
	}

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
	void dashuButtonClicked(MouseEvent event) {
		System.out.println("map2controller.dashuButtonClicked()");
		// Ӳ�������������
		if (gameStage.progress == 0) {
			dashuLabel.setText("�԰ף���ȥ�Ҵ峤�ɣ��������л�����˵......");
		}
		if (gameStage.progress >= 28 && gameStage.progress < 37) {
			dashuLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
		}
		System.out.println("map1controller.cunzhangButtonCliced()");
		dashuLabel.setVisible(true);
	}

	@FXML
	void shenmirenButtonClicked(MouseEvent event) {
		if (gameStage.progress == 49) {// ���ݿ����⣬���ù�
			gameStage.progress = 50;
		}
		System.out.println("map2controller.shenmirenButtonClicked()");
		// Ӳ���������˾���
		if (gameStage.progress == 0) {
			shenmirenLabel.setText("�԰ף���ȥ�Ҵ峤�ɣ��������л�����˵......");
		}
		if (gameStage.progress >= 50 && gameStage.progress < 58) {
			shenmirenLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
		}
		shenmirenLabel.setVisible(true);
	}

	@FXML
	void shujingButtonClicked(MouseEvent event) {
		if (gameStage.progress == 17) {// ���ݿ����⣬���ù�
			gameStage.progress = 18;
		}
		System.out.println("map2controller.shujingButtonClicked()");
		// Ӳ������������
		// ����ǰ
		if (gameStage.progress < 18) {
			shujingLabel.setText("�԰ף�����������������˵̫ǿ�󣬻��ǵȻ�������......");
		}
		if (gameStage.progress >= 18 && gameStage.progress < 24 && gameStage.monsterProgress == 0) {
			shujingLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
		}
		// ����ս��
		if (gameStage.progress == 24 && gameStage.monsterProgress == 0) {
			gameStage.mPlayer.pause();
			try {
				new PKStage().start(new Stage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ���ܺ�
		if (gameStage.progress >= 24 && gameStage.progress < 28 && gameStage.monsterProgress == 1) {
			shujingLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
			if (gameStage.progress == 25) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("ϵͳ��ʾ");
				alert.setHeaderText(null);
				alert.setContentText("ϰ�ü��ܣ�������ײ������");
				alert.showAndWait();
			}
		}
		shujingLabel.setVisible(true);
		if (gameStage.progress >= 25) {
			shujingButton.setVisible(false);
			shujingImage.setVisible(false);
		}
	}

	@FXML
	void penhuolongButtonClicked(MouseEvent event) {
		System.out.println("map2controller.penhuolongButtonClicked()");
		// Ӳ�������������
		// ����ǰ
		if (gameStage.progress < 37) {
			penhuolongLabel.setText("�԰ף�����������������˵̫ǿ�󣬻��ǵȻ�������......");
		}
		if (gameStage.progress >= 37 && gameStage.progress < 45 && gameStage.monsterProgress == 1) {
			penhuolongLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
		}
		// ����ս��
		if (gameStage.progress == 45 && gameStage.monsterProgress == 1) {
			gameStage.mPlayer.pause();
			try {

				new PKStage().start(new Stage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ���ܺ�
		if (gameStage.progress >= 45 && gameStage.progress < 49 && gameStage.monsterProgress == 2) {
			penhuolongLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
			if (gameStage.progress == 49) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��ϲ����");
				alert.setHeaderText(null);
				alert.setContentText("ϰ�ü��ܣ��������ȭ����");
				alert.showAndWait();
			}
		}
		penhuolongLabel.setVisible(true);
		if (gameStage.progress > 47) {
			penhuolongButton.setVisible(false);
			dragonImage.setVisible(false);
		}
	}

	@FXML
	void kulouButtonClicked(MouseEvent event) {
		System.out.println("map2controller.kulouButtonClicked()");
		// Ӳ�������þ���
		// ����ǰ
		if (gameStage.progress < 58) {
			kulouLabel.setText("�԰ף�����������������˵̫ǿ�󣬻��ǵȻ�������......");
		}
		if (gameStage.progress >= 58 && gameStage.progress < 60 && gameStage.monsterProgress == 2) {
			kulouLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
		}
		// ����ս��
		if (gameStage.progress == 60 && gameStage.monsterProgress == 2) {
			gameStage.mPlayer.pause();
			try {
				new PKStage().start(new Stage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ���ܺ�
		if (gameStage.progress >= 60 && gameStage.progress < 62 && gameStage.monsterProgress == 3) {
			kulouLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
			if (gameStage.progress == 63) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��ϲ����");
				alert.setHeaderText(null);
				alert.setContentText("ϰ�ü��ܣ�������һ������");
				alert.showAndWait();
			}
		}
		kulouLabel.setVisible(true);
		if (gameStage.progress > 61) {
			kulouButton.setVisible(false);
			kulouImage.setVisible(false);
		}
	}

	@FXML
	void liandaoButtonClicked(MouseEvent event) {
		System.out.println("map2controller.liandaoButtonClicked()");
		// Ӳ����������ʿ����
		// ����ǰ
		if (gameStage.progress < 62) {
			liandaoLabel.setText("�԰ף�����������������˵̫ǿ�󣬻��ǵȻ�������......");
		}
		if (gameStage.progress >= 62 && gameStage.progress < 66 && gameStage.monsterProgress == 3) {
			liandaoLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
		}
		// ����ս��
		if (gameStage.progress == 66 && gameStage.monsterProgress == 3) {
			gameStage.mPlayer.pause();
			try {
				new PKStage().start(new Stage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ���ܺ�
		if (gameStage.progress >= 66 && gameStage.progress < 69 && gameStage.monsterProgress == 4) {
			liandaoLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
			if (gameStage.progress == 67) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("��ϲ����");
				alert.setHeaderText(null);
				alert.setContentText("ϰ�ü��ܣ�����ɽ������");
				alert.showAndWait();
			}
		}
		liandaoLabel.setVisible(true);
		if (gameStage.progress > 67) {
			liandaoButton.setVisible(false);
			liandaoImage.setVisible(false);
		}
	}

	@FXML
	void cxkButtonClicked(MouseEvent event) {
		System.out.println("map2controller.cxkButtonClicked()");
		// Ӳ����cxk����
		// ����ǰ
		if (gameStage.progress < 69) {
			cxkLabel.setText("�԰ף�����������������˵̫ǿ�󣬻��ǵȻ�������......");
		}
		if (gameStage.progress >= 69 && gameStage.progress < 76 && gameStage.monsterProgress == 4) {
			cxkLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
		}
		// ����ս��
		if (gameStage.progress == 76 && gameStage.monsterProgress == 4) {
			gameStage.mPlayer.pause();
			try {
				new PKStage().start(new Stage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// ���ܺ�
		if (gameStage.progress >= 76 && gameStage.progress < 80 && gameStage.monsterProgress == 5) {
			cxkLabel.setText(plots[gameStage.progress]);
			gameStage.progress++;
		}
		if (gameStage.progress == 80) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("��Ϸʤ��!!!");
			alert.setHeaderText("ֻ����ʵ����̩����!!!");
			alert.setContentText("��սż����ϰ��2!�����ڴ�...");
			alert.showAndWait();
		}
		cxkLabel.setVisible(true);
		if (gameStage.progress > 77) {
			cxkButton.setVisible(false);
			cxkImage.setVisible(false);
		}
	}

}
