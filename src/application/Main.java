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

//整个游戏本体，存静态变量
class gameStage extends Application{
	public Scene map1Scene;
	public static MediaPlayer mPlayer;
	public static MediaView mView;
	
	public static boolean menuDisplay=false;
	public static boolean PlayBGM=true;
	
	//存这个游戏存档相关数据
	public static String account;
	public static int hp,mp,A,skillNum,progress,monsterProgress;
	public static String userName;         // 用户信息
	public static String monsterName;          // 怪物信息
	public static boolean loadOrSave=true;    //存/读
	//登录后用构造函数传参
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
		//这个mediaview是用来关游戏的
		mView=new MediaView();
		//以下是代码
		String eURL = this.getClass().getResource("../image/BGM.mp3").toString();
        Media media = new Media(eURL);
        mPlayer = new MediaPlayer(media);
        mPlayer.play();
//        mPlayer.setAutoPlay(true);
        mPlayer.setCycleCount (MediaPlayer.INDEFINITE);
		
        //初始地图
		try {
			Parent map1root = FXMLLoader.load(getClass().getResource("map1.fxml"));
			map1Scene=new Scene(map1root);
			map1Scene.setOnKeyPressed(e -> {
	        	// 按下“ESC”键
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
            primaryStage.setTitle("决战偶像练习生");
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
//              System.out.print("监听到窗口关闭");
            	gameStage.menuDisplay=false;
            	
            }
        });

	}
	
}