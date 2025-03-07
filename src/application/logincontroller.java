package application;

import java.io.IOException;
import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class logincontroller{

    @FXML
    private Button cancelButton;

    @FXML
    private Label secretLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button registButton;

    @FXML
    private Label accountLabel;

    @FXML
    private TextField accountEdit;

    @FXML
    private PasswordField secretEdit;
    
    @FXML
    void loginButtonClicked(MouseEvent event) {
    	if(secretEdit.getText().equals("")) {
    		Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个错误对话框
	        alert.setHeaderText("有数据未填");                
	        alert.show();
    	}
    	else {
    		try 
    		{   // 自写统一登录接口
        		DBOperator l=new DBOperator();
    			//SQL语句，用于查询
    			String sql = "SELECT * FROM userInfo where userName='"+accountEdit.getText()+"'";                                //密码账号
    			ResultSet rs = l.sqlQuery( sql );
    			
    			//打印出查询结果。
    			String secret = "";
    			rs.next();
    			secret = rs.getString("password");	
    			if(secret.equals(secretEdit.getText())) {
    				gameStage.userName = rs.getString("userName");                            // 获取账号
				    // 进入存档选择界面
					Stage primaryStage=(Stage)loginButton.getScene().getWindow();
    				primaryStage.close();
    				try {
    					new fileStage().start(primaryStage);
    				} catch(Exception e) {
    					e.printStackTrace();
    				}
    			}
    			else {
    				Alert alert = new Alert(Alert.AlertType.ERROR); // 创建一个错误对话框
    		        alert.setHeaderText("密码错误");                
    		        alert.show();
    			} 
    	        rs.close();
    		}
    		catch(SQLException e) {
    		    System.out.println("错误：" + e);
    		}
    	}
    }

    @FXML
    void cancelButtonClicked(MouseEvent event) {
    	Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void customerloginButtonClicked(MouseEvent event) {
    	accountEdit.setText("游客登录");
    	secretEdit.setText("123456");
    }
    
    @FXML
    void regist(ActionEvent event) { 	              // 进入注册界面
		Stage primaryStage = (Stage) registButton.getScene().getWindow();
		
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("regist.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		primaryStage.setScene(new Scene(root));
    }
}