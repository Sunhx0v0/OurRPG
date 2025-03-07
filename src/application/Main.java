package application;
	
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


public class Main extends Application {
	public Scene loginScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent loginroot = FXMLLoader.load(getClass().getResource("login.fxml"));
			loginScene = new Scene(loginroot);
            primaryStage.setTitle("Welcome");
            primaryStage.setScene(loginScene);
            primaryStage.setResizable(false);
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

//������Ϸ���壬�澲̬����
class gameStage extends Application{
	public Scene map1Scene;
	public static MediaPlayer mPlayer;
	public static MediaView mView;
	
	public static boolean menuDisplay=false;
	public static boolean PlayBGM=true;
	
	//�������Ϸ�浵�������
	public static String account;
	public static int hp,mp,A,skillNum,progress,monsterProgress;
	public static String userName;         // �û���Ϣ
	public static String monsterName;          // ������Ϣ
	public static boolean loadOrSave=true;    //��/��
	//��¼���ù��캯������
	public gameStage(String acc) throws SQLException {
	// TODO Auto-generated constructor stub
		DBOperator dbop = new DBOperator();
		String sql = "SELECT * FROM playerInfo WHERE account='" + acc + "'";
		ResultSet rs = dbop.sqlQuery(sql);
		rs.next();
		account=acc;
		hp = rs.getInt("hp");
		mp = rs.getInt("mp");
		A = rs.getInt("A");
		skillNum = rs.getInt("skillNum");
		progress= rs.getInt("progress");
		monsterProgress= rs.getInt("monsterPrgrs");
	}
	
	public gameStage() {
		account="10002";
		hp=100;
		mp=100;
		A=1;
		skillNum=0;
		progress=0;
		monsterProgress=0;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//���mediaview����������Ϸ��
		mView=new MediaView();
		//�����Ǵ���
		String eURL = this.getClass().getResource("../image/BGM.mp3").toString();
        Media media = new Media(eURL);
        mPlayer = new MediaPlayer(media);
        mPlayer.play();
//        mPlayer.setAutoPlay(true);
        mPlayer.setCycleCount (MediaPlayer.INDEFINITE);
		
        //��ʼ��ͼ
		try {
			Parent map1root = FXMLLoader.load(getClass().getResource("map1.fxml"));
			map1Scene=new Scene(map1root);
			map1Scene.setOnKeyPressed(e -> {
	        	// ���¡�ESC����
				System.out.println(e.getCode());
	            if (e.getCode() == KeyCode.ESCAPE) {
	                if (!menuDisplay) {
	    				try {
	    					new menuStage().start(new Stage());
	    					menuDisplay=true;
	    				} catch(Exception e1) {
	    					e1.printStackTrace();
	    				}
					}
	            }
	        });
            primaryStage.setTitle("��սż����ϰ��");
            primaryStage.setScene(map1Scene);
            primaryStage.setResizable(false);
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void main(String[] args) {
		launch(args);
	}
	public static void exit() {
		Stage primaryStage=(Stage)mView.getScene().getWindow();
		primaryStage.close();
	}
}


class menuStage extends Application{
	private Scene menuScene;
	public static double voice=100;
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			Parent loginroot = FXMLLoader.load(getClass().getResource("menu.fxml"));
			menuScene = new Scene(loginroot);
            primaryStage.setTitle("Menu");
            primaryStage.setScene(menuScene);
            primaryStage.setResizable(false);
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
//              System.out.print("���������ڹر�");
            	gameStage.menuDisplay=false;
            	
            }
        });

	}
	
}