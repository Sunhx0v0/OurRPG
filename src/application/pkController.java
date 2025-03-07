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
    private Slider slide1;       // ���Ѫ��

    @FXML
    private Slider slide2;       // ����Ѫ��

    @FXML
    private Slider slide3;       // �������

    @FXML
    private TextField h0;        // �չ��˺���ʾ
    
    @FXML
    private TextField h1;        // ����1�˺���ʾ

    @FXML
    private TextField h2;        // ����2�˺���ʾ

    @FXML
    private TextField h3;        // ����3�˺���ʾ

    @FXML
    private TextField h4;        // ����4�˺���ʾ

    @FXML
    private TextField c1;        // ����1����

    @FXML
    private TextField c2;       

    @FXML
    private TextField c3;       

    @FXML
    private TextField c4;        
    
    @FXML
    private Button skill3;        // ����3��ť

    @FXML
    private Button skill4;        // 4��ť

    @FXML
    private Button skill1;        // 1��ť

    @FXML
    private Button skill2;        // 2��ť

    @FXML
    private Button pingA;        // �չ���ť�˺�

    @FXML
    private TextField blue;        // ������Ϣ

    @FXML
    private TextField blood1;        // ���Ѫ����Ϣ

    @FXML
    private TextField blood2;        // ����Ѫ����Ϣ

    @FXML
    private TextField info;          // ϵͳ������Ϣ
    
    @FXML
    private TextField mstName;      // ��������
    
    @FXML
    private ImageView userHead;         // ���ͷ��
    
    @FXML
    private ImageView mstHead;         // ����ͷ��
    
    @FXML
    private TextField userName;      // �������
    
    @FXML
    private Button back;            // ���������水ť
    
    @FXML
    private ImageView atkView;     // ������Ч
    
    @FXML
    private ImageView user;          //  ��ɫͼ��
    
    @FXML
    private ImageView mst;           //  ����ͼ��
    
    String account;                                       // ����˺ţ��ӵ�¼����ȡ
    String monsterName;                       // �������ƣ�������ս�����ȡ
     
    int maxBlood1;   // ���Ѫ��
    int maxBlood2;   // ����Ѫ��
    int maxBlue;     // ��������������ɳ������Ĳ��䣩
    int attack;     // �����������ʣ�
    int mstID;           // �������
    int skillNumber;         // ������
    
    boolean gameover = false;          // ��Ϸ�Ƿ����
    
    int []damage = {10, 20, 25, 30, 50};      // ���ܻ����˺�һ��
    int []blueA = {0, 10, 10, 15, 25};        // �����㶨
    

    DBOperator d1=new DBOperator();
	Connection con = d1.getConnnect();
	
	public MediaPlayer mPlayer;                // ���ֲ���
    public void initialize() throws SQLException {   // ��ս��ʼ��
    	//���˴�ս��˫����Ϣ
    	switch (gameStage.monsterProgress) {
		case 0:
			monsterName="����";
			break;
		case 1:
			monsterName="�����";
			break;
		case 2:
			monsterName="����";
			break;
		case 3:
			monsterName="������ʿ";
			break;
		case 4:
			monsterName="ż����ϰ��";
			break;
		default:
			break;
		}
    	
    	account=gameStage.account;
    	
        String sql2 = "SELECT * FROM monsterInfo WHERE monsterName='"+monsterName+"' ";           // ���ҹ�������
        String sql3 = "SELECT * FROM userInfo WHERE account='"+account+"' ";                      // �û�����
        
        
 	    Statement stmt = con.createStatement();  
 	    
    	maxBlood1 = gameStage.hp;
    	maxBlue = gameStage.mp;   
    	attack = gameStage.A; 
    	skillNumber = gameStage.skillNum;              // �ӻ����еõ�����
    	
    	if(skillNumber<4) {                        // ���ü���ͼ��ɼ���
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
    	mst.setImage(new Image(new File(rs2.getString("image")).toURI().toString()));         // ��ȡ����ͼ����ʾ
    	mstHead.setImage(new Image(new File(rs2.getString("mstHead")).toURI().toString()));    // ��ȡ����ͷ����ʾ
    	
    	if(mstID==5) {
    		String eURL = this.getClass().getResource("../BGM/jntm.mp3").toString();
            Media media = new Media(eURL);
            mPlayer = new MediaPlayer(media);
            mPlayer.setVolume(menuStage.voice);
            mPlayer.play();
    	}
    	else {
    		String eURL = this.getClass().getResource("../BGM/ս������.wav").toString();
            Media media = new Media(eURL);
            mPlayer = new MediaPlayer(media);
            mPlayer.setVolume(menuStage.voice);
            mPlayer.play();
    	}
    	
    	blood1.setText(""+maxBlood1);
    	blood2.setText(""+maxBlood2);
    	blue.setText(""+maxBlue);
    	mstName.setText(monsterName);
    	
    	h0.setText("�˺�: "+damage[0]*attack);   // �˺���text
    	h1.setText("�˺�: "+damage[1]*attack);
    	h2.setText("�˺�: "+damage[2]*attack);
    	h3.setText("�˺�: "+damage[3]*attack);
    	h4.setText("�˺�: "+damage[4]*attack);
    	
    	back.setVisible(false);         // ���ذ�ť��ʼ���ɼ�
    	atkView.setVisible(false);

    }
    
    
    class GuaiwuATK extends Thread{                            // ��������߼�
    	public void run()
	    {   
    		pingA.setDisable(true);
        	if(skillNumber>0) {                        // ���ü���ͼ��ɼ���
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
    				Thread.sleep(300);                 // �ȵ�һ�ᣬ�ü�����ʾ��
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			atkView.setVisible(false);          // �ر���Ч
    			try {
    				Thread.sleep(400);                     // �ٵ�һ���ٳ���
    				} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			Random r = new Random();
    			int num = r.nextInt(2)+1;      // 1��2
    			
    			String type="";               // ���＼���˺�
    			int hurt=0;                   // �����˺�
    			
    			Statement stmt;
				try {
					stmt = con.createStatement();
					ResultSet rs2 = stmt.executeQuery("SELECT * FROM monsterInfo WHERE monsterName='"+monsterName+"' ");
	    		    rs2.next();
	    			
	        		type = rs2.getString("skillName"+num);                // �����ݿ��ü�������
	    	    	hurt = rs2.getInt("skillDmg"+num);                    // �����ݿ����˺�
				} catch (SQLException e) {
					e.printStackTrace();
				}
    	    	
    	    	info.setText("����ʹ�á�"+type+"��,����ˣ�"+hurt+"���˺�");
    	    	
        		int afterblood = Integer.parseInt(blood1.getText()) - hurt;     // ���ﱻ��ǰ
        		
            	slide1.setValue((int)100*afterblood/maxBlood1);
            	blood1.setText(""+afterblood);
            	
            	atkView.setImage(new Image("file:src/image/����3.png"));
            	atkView.setVisible(true);
            	PathTransition pathTransition=new PathTransition(Duration.millis(200), new Line(400, 0, 100, 0), atkView);  // ��������
        	    pathTransition.play();
        	    
    	    	if(afterblood<=0) { 
    	    		info.setText("�㱻�����ɱ�ˣ��̣�");
    	    		blood1.setText(""+0);
    	    		gameover = true;
    	    		back.setVisible(true);
    	    		mPlayer.stop();
    	    	}
    	    	
    	    	pingA.setDisable(false);
    	    	if(skillNumber>0) {                        // ���ü���ͼ��ɼ���
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
    
    class Wait extends Thread{                            // ��������߼�
    	public void run()
	    {   
    	    	try {
    				Thread.sleep(1000);              
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			atkView.setVisible(false);           // �ر���Ч
        }
    }

    void playerATK(String type,int order) throws InterruptedException {    // ����˺�
    	if(!gameover) {
    		int hurt = damage[order] * attack;                                 // ʵ���˺� = �����˺� * ������
        	int bluecost = blueA[order];      
        	info.setText("��ʹ�á�"+type+"��,����ˣ�"+hurt+"���˺�");
        	
    		int afterblood = Integer.parseInt(blood2.getText()) - hurt;
    		int afterblue = Integer.parseInt(blue.getText()) - bluecost;
    		
        	slide2.setValue(100-(int)100*afterblood/maxBlood2);           // �����Ѫ
        	slide3.setValue((int)100*afterblue/maxBlue);                  // �Լ�����
        	
        	blood2.setText(""+afterblood);
        	blue.setText(""+afterblue);
        	
        	atkView.setImage(new Image("file:image/����2.png"));
        	atkView.setVisible(true);
        	PathTransition pathTransition=new PathTransition(Duration.millis(200), new Line(100, 0, 400, 0), atkView);  // ��������
    	    pathTransition.play();
        	
        	if(afterblood<=0) { 
        		Wait w1 = new Wait();
        		w1.start();
        		
        		info.setText("���ﱻ���ɱ�ˣ�666��");
        		blood2.setText(""+0);
        		gameover = true;
        		back.setVisible(true);
        		
//        		Statement stmt;
//				try {
//					stmt = con.createStatement();
//					
//					String sql4 = "UPDATE playerInfo SET monsterPrgrs ="+mstID+" WHERE account='"+account+"' ";   // �������ɱ����Ϣ
//					stmt.executeUpdate( sql4 );
//	    			
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
				
				//���±�����Ϸ����
        		gameStage.hp=100+200*mstID;    // ��ҳɳ�
        		gameStage.mp=100+25*mstID;
        		gameStage.A=1+mstID;
				gameStage.monsterProgress=mstID;
				gameStage.skillNum=mstID;              // ��ɱһ���֣����һ������
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
    void Return() {                          // ҳ�淵��
    	//��ȡ��ť���ڵĴ���
		Stage primaryStage = (Stage) back.getScene().getWindow();
		//��ǰ��������
		primaryStage.close();
		if (gameStage.PlayBGM) {
			gameStage.mPlayer.play();
		}
    }
}