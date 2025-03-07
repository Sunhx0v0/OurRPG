package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.net.ssl.SSLContext;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class filesController{
	// �Ǽ��ش浵���Ǳ���浵��TRUE��ʾ���ش浵
	class Saves {
		public String userName;
		public String account;
		public int progress;
		public int monsterPrgrs;

		Saves() {
		}

		Saves(String na, String a) {
			userName = na;
			account = a;
		}
	}
	Saves[] ss=new Saves[4];
	
	
	boolean SaveProg(String ac) {
		Connection conn;
		DBOperator dbop = new DBOperator();
		// SQL���
		String sql = "UPDATE playerInfo SET hp = ?, mp = ?, A = ?, skillNum = ?,progress= ?, monsterPrgrs= ? WHERE account=?";
		try {
			dbop=new DBOperator();
			conn=dbop.getConnnect();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, gameStage.hp);
			ps.setInt(2, gameStage.mp);
			ps.setInt(3, gameStage.A);
			ps.setInt(4, gameStage.skillNum);
			ps.setInt(5, gameStage.progress);
			ps.setInt(6, gameStage.monsterProgress);
			ps.setString(7, ac);
			ps.executeUpdate();
			System.out.println("success");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("���ݿ��޸Ĵ���"+e);
		}
		return true;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button button4;

	@FXML
	private Label selectTable;

	@FXML
	private Button button2;

	@FXML
	private Button button3;

	@FXML
	private Label saveTable;

	@FXML
	private Button button1;

	@FXML
	private Button backButton;

	Saves nowSaves = new Saves();

	@FXML
	void file1ButtonClicked(MouseEvent event) {
		if (gameStage.loadOrSave) {// ���ش浵
			Stage primaryStage = (Stage) backButton.getScene().getWindow();
			primaryStage.close();
			try {
				nowSaves = ss[0];
				gameStage.account = nowSaves.account;
				gameStage.progress = nowSaves.progress;
				gameStage.monsterProgress = nowSaves.monsterPrgrs;
				new gameStage(gameStage.account).start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {// ����浵
			nowSaves.account = "1_"+gameStage.userName;
			nowSaves.progress = gameStage.progress;// ��Ϸ�д���Ľ���
			nowSaves.monsterPrgrs = gameStage.monsterProgress;// ����Ĺ������
			ss[0] = nowSaves;
			SaveProg(ss[0].account);
			nowSaves.account = gameStage.account;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ϵͳ��ʾ");
			alert.setHeaderText(null);
			alert.setContentText("����ɹ�!");
			 
			alert.showAndWait();
		}
	}

	@FXML
	void file2ButtonClicked(MouseEvent event) {
		if (gameStage.loadOrSave) {// ���ش浵
			Stage primaryStage = (Stage) backButton.getScene().getWindow();
			primaryStage.close();
			try {
				nowSaves = ss[1];
				gameStage.account = nowSaves.account;
				gameStage.progress = nowSaves.progress;
				gameStage.monsterProgress = nowSaves.monsterPrgrs;
				new gameStage(gameStage.account).start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {// ����浵
			nowSaves.account = "2_"+gameStage.userName;
			nowSaves.progress = gameStage.progress;// ��Ϸ�д���Ľ���
			nowSaves.monsterPrgrs = gameStage.monsterProgress;// ����Ĺ������
			ss[1] = nowSaves;
			SaveProg(ss[1].account);
			nowSaves.account = gameStage.account;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ϵͳ��ʾ");
			alert.setHeaderText(null);
			alert.setContentText("����ɹ�!");
			 
			alert.showAndWait();
		}
	}

	@FXML
	void file3ButtonClicked(MouseEvent event) {
		if (gameStage.loadOrSave) {// ���ش浵
			Stage primaryStage = (Stage) backButton.getScene().getWindow();
			primaryStage.close();
			try {
				nowSaves = ss[2];
				gameStage.account = nowSaves.account;
				gameStage.progress = nowSaves.progress;
				gameStage.monsterProgress = nowSaves.monsterPrgrs;
				new gameStage(gameStage.account).start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {// ����浵
			nowSaves.account = "3_"+gameStage.userName;
			nowSaves.progress = gameStage.progress;// ��Ϸ�д���Ľ���
			nowSaves.monsterPrgrs = gameStage.monsterProgress;// ����Ĺ������
			ss[2] = nowSaves;
			SaveProg(ss[2].account);
			nowSaves.account = gameStage.account;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ϵͳ��ʾ");
			alert.setHeaderText(null);
			alert.setContentText("����ɹ�!");
			 
			alert.showAndWait();
		}
	}

	@FXML
	void file4ButtonClicked(MouseEvent event) {
		if (gameStage.loadOrSave) {// ���ش浵
			Stage primaryStage = (Stage) backButton.getScene().getWindow();
			primaryStage.close();
			try {
				nowSaves = ss[3];
				gameStage.account = nowSaves.account;
				gameStage.progress = nowSaves.progress;
				gameStage.monsterProgress = nowSaves.monsterPrgrs;
				new gameStage(gameStage.account).start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {// ����浵
			nowSaves.account = "4_"+gameStage.userName;
			nowSaves.progress = gameStage.progress;// ��Ϸ�д���Ľ���
			nowSaves.monsterPrgrs = gameStage.monsterProgress;// ����Ĺ������
			ss[3] = nowSaves;
			SaveProg(ss[3].account);
			nowSaves.account = gameStage.account;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ϵͳ��ʾ");
			alert.setHeaderText(null);
			alert.setContentText("����ɹ�!");
			 
			alert.showAndWait();
		}
	}

	@FXML
	void backButtonClicked(MouseEvent event) {// �˳���Ϸ�򷵻�
		try {
			Stage primaryStage = (Stage) backButton.getScene().getWindow();
			primaryStage.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	@FXML
	void initialize() {
		selectTable.setVisible(gameStage.loadOrSave);
		saveTable.setVisible(!gameStage.loadOrSave);
		if (gameStage.loadOrSave) {
			backButton.setText("�˳���Ϸ");
		} else {
			backButton.setText("������Ϸ");
		}
		for(int i=0;i<4;i++) {
			ss[i]=new Saves("asdad","asdad");
		}

		ss[0].userName =gameStage.userName;

		// �����ݿ��ж�ȡ���û���4���浵
		try {
			DBOperator dbop = new DBOperator();
			// SQL���
			String sql = "SELECT account FROM userInfo WHERE userName='" + ss[0].userName + "'";
			ResultSet rs = dbop.sqlQuery(sql);
			int j = 0;
			while (rs.next()) {
				System.out.println(rs.getString("account"));
				ss[j].account = rs.getString("account");
				j++;
			}
			// �رն���
			rs.close();
		} catch (SQLException e) {
			System.out.println("�û��浵���ش���" + e);
		}
		try {
			DBOperator dbop = new DBOperator();
			for (int i = 0; i < 4; i++) {
				// SQL���
				String sql = "SELECT progress, monsterPrgrs FROM playerInfo WHERE account='" + ss[i].account + "'";
				ResultSet rs = dbop.sqlQuery(sql);
				while (rs.next()) {
					ss[i].progress = rs.getInt("progress");
					ss[i].monsterPrgrs = rs.getInt("monsterPrgrs");
				}
				// �رն���
				rs.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("��ȡ���ȴ���" + e);
		}
	}
}

class fileStage extends Application {
	public Scene fScene;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			Parent fileroot = FXMLLoader.load(getClass().getResource("files.fxml"));
			fScene = new Scene(fileroot);
			primaryStage.setTitle("�浵");
			primaryStage.setScene(fScene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}