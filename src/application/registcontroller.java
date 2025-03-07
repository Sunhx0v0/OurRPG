package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class registcontroller {


	    @FXML
	    private Button validButton;

	    @FXML
	    private Button exitButton;

	    @FXML
	    private TextField name;

	    @FXML
	    private Button back;
	    
	    @FXML
	    private TextField password2;

	    @FXML
	    private TextField password1;

	    @FXML
	    void validation(ActionEvent event) throws SQLException, ClassNotFoundException {
			//SQL语句，用于查询
			String sql = "SELECT * FROM userInfo where userName='"+name.getText()+"'";                                //密码账号
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
	    	Connection con = DriverManager.getConnection("jdbc:ucanaccess://application/Mytestdatabase.accdb", "", "");
	    	Statement stmt = con.createStatement();  
		    ResultSet rs = stmt.executeQuery( sql );
			
	    	if(password1.getText().equals("")||password2.getText().equals("")||name.getText().equals("") ) {
	    		Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个错误对话框
		        alert.setHeaderText("有数据未填");                
		        alert.show();
	    	}
	    	else if(!password1.getText().equals(password2.getText())) {
	    		Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个错误对话框
		        alert.setHeaderText("两次密码不一致");                
		        alert.show();
	    	}
	    	else if(rs.next()) {
				Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个错误对话框
		        alert.setHeaderText("用户名已存在");                
		        alert.show();
			}
	    	else {
	    		String userName = name.getText();
	    		String password = password1.getText();
	    		String sql2 = "INSERT INTO userInfo (account,userName,password) VALUES(?,?,?)"; //account从全局得到
	    		PreparedStatement pstmt;
	    		pstmt = con.prepareStatement(sql2);
	    		pstmt.setString(2, userName);
	    		pstmt.setString(3, password);
	    		for(int i=0;i<4;i++) {
	    			pstmt.setString(1, (i+1)+"_"+userName);    // 记录账号密码
	    			pstmt.executeUpdate();
	    		}
	    		
	    		String sql3 = "INSERT INTO playerInfo (account,hp,mp,A,skillNum,progress,monsterPrgrs) values(?,100, 100, 1, 0, 0, 0)";
	    		pstmt = con.prepareStatement(sql3);
	    		for(int i=0;i<4;i++) {
	    			pstmt.setString(1, (i+1)+"_"+userName);     // 分配存档
	    			pstmt.executeUpdate();
	    		}
	    		back.setVisible(true);
	    		validButton.setVisible(false);
	    		exitButton.setVisible(false);
	    	}
	    }

	    @FXML
	    void exit(ActionEvent event) {
	    	Stage primaryStage = (Stage) exitButton.getScene().getWindow();
			
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("login.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
			primaryStage.setScene(new Scene(root));
	    }


}