package application;


import java.io.File;


import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Slider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class pkController {

    @FXML
    private Slider slide1;       // 玩家血条

    @FXML
    private Slider slide2;       // 怪物血条

    @FXML
    private Slider slide3;       // 玩家蓝条

    @FXML
    private TextField h0;        // 普攻伤害提示
    
    @FXML
    private TextField h1;        // 技能1伤害提示

    @FXML
    private TextField h2;        // 技能2伤害提示

    @FXML
    private TextField h3;        // 技能3伤害提示

    @FXML
    private TextField h4;        // 技能4伤害提示

    @FXML
    private TextField c1;        // 技能1耗蓝

    @FXML
    private TextField c2;       

    @FXML
    private TextField c3;       

    @FXML
    private TextField c4;        
    
    @FXML
    private Button skill3;        // 技能3按钮

    @FXML
    private Button skill4;        // 4按钮

    @FXML
    private Button skill1;        // 1按钮

    @FXML
    private Button skill2;        // 2按钮

    @FXML
    private Button pingA;        // 普攻按钮伤害

    @FXML
    private TextField blue;        // 蓝量信息

    @FXML
    private TextField blood1;        // 玩家血条信息

    @FXML
    private TextField blood2;        // 怪物血条信息

    @FXML
    private TextField info;          // 系统播报信息
    
    @FXML
    private TextField mstName;      // 怪物名称
    
    @FXML
    private ImageView userHead;         // 玩家头像
    
    @FXML
    private ImageView mstHead;         // 怪物头像
    
    @FXML
    private TextField userName;      // 玩家名称
    
    @FXML
    private Button back;            // 返回主界面按钮
    
    @FXML
    private ImageView atkView;     // 攻击特效
    
    @FXML
    private ImageView user;          //  角色图像
    
    @FXML
    private ImageView mst;           //  怪物图像
    
    String account;                                       // 玩家账号，从登录处获取
    String monsterName;                       // 怪物名称，根据挑战对象获取
     
    int maxBlood1;   // 玩家血量
    int maxBlood2;   // 怪物血量
    int maxBlue;     // 玩家蓝条（蓝条成长，蓝耗不变）
    int attack;     // 攻击力（倍率）
    int mstID;           // 怪物序号
    int skillNumber;         // 技能数
    
    boolean gameover = false;          // 游戏是否结束
    
    int []damage = {10, 20, 25, 30, 50};      // 技能基础伤害一定
    int []blueA = {0, 10, 10, 15, 25};        // 耗蓝恒定
    

    DBOperator d1=new DBOperator();
	Connection con = d1.getConnnect();
	
	public MediaPlayer mPlayer;                // 音乐播放
    public void initialize() throws SQLException {   // 对战初始化
    	//传此次战斗双方信息
    	switch (gameStage.monsterProgress) {
		case 0:
			monsterName="树精";
			break;
		case 1:
			monsterName="喷火龙";
			break;
		case 2:
			monsterName="骷髅";
			break;
		case 3:
			monsterName="镰刀武士";
			break;
		case 4:
			monsterName="偶像练习生";
			break;
		default:
			break;
		}
    	
    	account=gameStage.account;
    	
        String sql2 = "SELECT * FROM monsterInfo WHERE monsterName='"+monsterName+"' ";           // 查找怪物数据
        String sql3 = "SELECT * FROM userInfo WHERE account='"+account+"' ";                      // 用户数据
        
        
 	    Statement stmt = con.createStatement();  
 	    
    	maxBlood1 = gameStage.hp;
    	maxBlue = gameStage.mp;   
    	attack = gameStage.A; 
    	skillNumber = gameStage.skillNum;              // 从缓存中得到技能
    	
    	if(skillNumber<4) {                        // 设置技能图标可见性
    		skill4.setVisible(false);
    		skill4.setDisable(true);
    		h4.setVisible(false);
    		c4.setVisible(false);
    	}
    	if(skillNumber<3) {
    		skill3.setVisible(false);
    		skill3.setDisable(true);
    		h3.setVisible(false);
    		c3.setVisible(false);
    	}
		if(skillNumber<2) {
			skill2.setVisible(false);
    		skill2.setDisable(true);
    		h2.setVisible(false);
    		c2.setVisible(false);
		}
		if(skillNumber<1) {
			skill1.setVisible(false);
    		skill1.setDisable(true);
    		h1.setVisible(false);
    		c1.setVisible(false);
		}
		

    	ResultSet rs3 = stmt.executeQuery( sql3 );
    	rs3.next();
    	userName.setText(rs3.getString("userName"));
    	                
	    ResultSet rs2 = stmt.executeQuery( sql2 );
	    rs2.next();
    	maxBlood2 = rs2.getInt("hp");
    	mstID = rs2.getInt("ID");
    	System.out.println(""+mstID);
    	mst.setImage(new Image(new File(rs2.getString("image")).toURI().toString()));         // 获取怪物图像并显示
    	mstHead.setImage(new Image(new File(rs2.getString("mstHead")).toURI().toString()));    // 获取怪物头像并显示
    	
    	if(mstID==5) {
    		String eURL = this.getClass().getResource("../BGM/jntm.mp3").toString();
            Media media = new Media(eURL);
            mPlayer = new MediaPlayer(media);
            mPlayer.setVolume(menuStage.voice);
            mPlayer.play();
    	}
    	else {
    		String eURL = this.getClass().getResource("../BGM/战斗音乐.wav").toString();
            Media media = new Media(eURL);
            mPlayer = new MediaPlayer(media);
            mPlayer.setVolume(menuStage.voice);
            mPlayer.play();
    	}
    	
    	blood1.setText(""+maxBlood1);
    	blood2.setText(""+maxBlood2);
    	blue.setText(""+maxBlue);
    	mstName.setText(monsterName);
    	
    	h0.setText("伤害: "+damage[0]*attack);   // 伤害的text
    	h1.setText("伤害: "+damage[1]*attack);
    	h2.setText("伤害: "+damage[2]*attack);
    	h3.setText("伤害: "+damage[3]*attack);
    	h4.setText("伤害: "+damage[4]*attack);
    	
    	back.setVisible(false);         // 返回按钮初始不可见
    	atkView.setVisible(false);

    }
    
    
    class GuaiwuATK extends Thread{                            // 怪物出手逻辑
    	public void run()
	    {   
    		pingA.setDisable(true);
        	if(skillNumber>0) {                        // 设置技能图标可见性
        		skill1.setDisable(true);
        	}
        	if(skillNumber>1) {
        		skill2.setDisable(true);
        	}
    		if(skillNumber>2) {
        		skill3.setDisable(true);
    		}
    		if(skillNumber>3) {
        		skill4.setDisable(true);
    		}
    		
    		if(!gameover) {
    			try {
    				Thread.sleep(300);                 // 先等一会，让技能显示完
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			atkView.setVisible(false);          // 关闭特效
    			try {
    				Thread.sleep(400);                     // 再等一会再出手
    				} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			Random r = new Random();
    			int num = r.nextInt(2)+1;      // 1或2
    			
    			String type="";               // 怪物技能伤害
    			int hurt=0;                   // 怪物伤害
    			
    			Statement stmt;
				try {
					stmt = con.createStatement();
					ResultSet rs2 = stmt.executeQuery("SELECT * FROM monsterInfo WHERE monsterName='"+monsterName+"' ");
	    		    rs2.next();
	    			
	        		type = rs2.getString("skillName"+num);                // 从数据库获得技能名称
	    	    	hurt = rs2.getInt("skillDmg"+num);                    // 从数据库获得伤害
				} catch (SQLException e) {
					e.printStackTrace();
				}
    	    	
    	    	info.setText("怪物使用“"+type+"”,造成了："+hurt+"点伤害");
    	    	
        		int afterblood = Integer.parseInt(blood1.getText()) - hurt;     // 怪物被打前
        		
            	slide1.setValue((int)100*afterblood/maxBlood1);
            	blood1.setText(""+afterblood);
            	
            	atkView.setImage(new Image("file:src/image/技能3.png"));
            	atkView.setVisible(true);
            	PathTransition pathTransition=new PathTransition(Duration.millis(200), new Line(400, 0, 100, 0), atkView);  // 动画表现
        	    pathTransition.play();
        	    
    	    	if(afterblood<=0) { 
    	    		info.setText("你被怪物击杀了，蔡！");
    	    		blood1.setText(""+0);
    	    		gameover = true;
    	    		back.setVisible(true);
    	    		mPlayer.stop();
    	    	}
    	    	
    	    	pingA.setDisable(false);
    	    	if(skillNumber>0) {                        // 设置技能图标可见性
            		skill1.setDisable(false);
            	}
            	if(skillNumber>1) {
            		skill2.setDisable(false);
            	}
        		if(skillNumber>2) {
            		skill3.setDisable(false);
        		}
        		if(skillNumber>3) {
            		skill4.setDisable(false);
        		}
    	    	
    		}
        }
    }
    
    class Wait extends Thread{                            // 怪物出手逻辑
    	public void run()
	    {   
    	    	try {
    				Thread.sleep(1000);              
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			atkView.setVisible(false);           // 关闭特效
        }
    }

    void playerATK(String type,int order) throws InterruptedException {    // 玩家伤害
    	if(!gameover) {
    		int hurt = damage[order] * attack;                                 // 实际伤害 = 基础伤害 * 攻击力
        	int bluecost = blueA[order];      
        	info.setText("你使用“"+type+"”,造成了："+hurt+"点伤害");
        	
    		int afterblood = Integer.parseInt(blood2.getText()) - hurt;
    		int afterblue = Integer.parseInt(blue.getText()) - bluecost;
    		
        	slide2.setValue(100-(int)100*afterblood/maxBlood2);           // 怪物扣血
        	slide3.setValue((int)100*afterblue/maxBlue);                  // 自己扣蓝
        	
        	blood2.setText(""+afterblood);
        	blue.setText(""+afterblue);
        	
        	atkView.setImage(new Image("file:image/技能2.png"));
        	atkView.setVisible(true);
        	PathTransition pathTransition=new PathTransition(Duration.millis(200), new Line(100, 0, 400, 0), atkView);  // 动画表现
    	    pathTransition.play();
        	
        	if(afterblood<=0) { 
        		Wait w1 = new Wait();
        		w1.start();
        		
        		info.setText("怪物被你击杀了，666！");
        		blood2.setText(""+0);
        		gameover = true;
        		back.setVisible(true);
        		
//        		Statement stmt;
//				try {
//					stmt = con.createStatement();
//					
//					String sql4 = "UPDATE playerInfo SET monsterPrgrs ="+mstID+" WHERE account='"+account+"' ";   // 更新玩家杀敌信息
//					stmt.executeUpdate( sql4 );
//	    			
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
				
				//更新本局游戏进度
        		gameStage.hp=100+200*mstID;    // 玩家成长
        		gameStage.mp=100+25*mstID;
        		gameStage.A=1+mstID;
				gameStage.monsterProgress=mstID;
				gameStage.skillNum=mstID;              // 击杀一个怪，获得一个技能
				mPlayer.stop();
        	}
    	}
    }
    
    
    @FXML
    void pingAATK(ActionEvent event) throws InterruptedException {
    	String type = pingA.getText();
    	playerATK(type,0);
    	GuaiwuATK g1 = new GuaiwuATK();
    	g1.start();
    	Wait w2 = new Wait();
    	w2.start();
    }	

    @FXML
    void skill2ATK(ActionEvent event) throws InterruptedException {
    	String type = skill2.getText();
    	playerATK(type,2);
    	GuaiwuATK g1 = new GuaiwuATK();
    	g1.start();
    	Wait w2 = new Wait();
    	w2.start();
    }

    @FXML
    void skill3ATK(ActionEvent event) throws InterruptedException {
    	String type = skill3.getText();
    	playerATK(type,3);
    	GuaiwuATK g1 = new GuaiwuATK();
    	g1.start();
    	Wait w2 = new Wait();
    	w2.start();
    }

    @FXML
    void skill1ATK(ActionEvent event) throws InterruptedException {
    	String type = skill1.getText();
    	playerATK(type,1);
    	GuaiwuATK g1 = new GuaiwuATK();
    	g1.start();
    	Wait w2 = new Wait();
    	w2.start();
    }

    @FXML
    void skill4ATK(ActionEvent event) throws InterruptedException {
    	String type = skill4.getText();
    	playerATK(type,4);
    	GuaiwuATK g1 = new GuaiwuATK();
    	g1.start();
    	Wait w2 = new Wait();
    	w2.start();
    }	
    
    @FXML
    void Return() {                          // 页面返回
    	//获取按钮所在的窗口
		Stage primaryStage = (Stage) back.getScene().getWindow();
		//当前窗口隐藏
		primaryStage.close();
		if (gameStage.PlayBGM) {
			gameStage.mPlayer.play();
		}
    }
}