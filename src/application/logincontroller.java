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
    		Alert alert = new Alert(Alert.AlertType.ERROR); // ����һ������Ի���
	        alert.setHeaderText("������δ��");                
	        alert.show();
    	}
    	else {
    		try 
    		{   // ��дͳһ��¼�ӿ�
        		DBOperator l=new DBOperator();
    			//SQL��䣬���ڲ�ѯ
    			String sql = "SELECT * FROM userInfo where userName='"+accountEdit.getText()+"'";                                //�����˺�
    			ResultSet rs = l.sqlQuery( sql );
    			
    			//��ӡ����ѯ�����
    			String secret = "";
    			rs.next();
    			secret = rs.getString("password");	
    			if(secret.equals(secretEdit.getText())) {
    				gameStage.userName = rs.getString("userName");                            // ��ȡ�˺�
				    // ����浵ѡ�����
					Stage primaryStage=(Stage)loginButton.getScene().getWindow();
    				primaryStage.close();
    				try {
    					new fileStage().start(primaryStage);
    				} catch(Exception e) {
    					e.printStackTrace();
    				}
    			}
    			else {
    				Alert alert = new Alert(Alert.AlertType.ERROR); // ����һ������Ի���
    		        alert.setHeaderText("�������");                
    		        alert.show();
    			} 
    	        rs.close();
    		}
    		catch(SQLException e) {
    		    System.out.println("����" + e);
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
    	accountEdit.setText("�ο͵�¼");
    	secretEdit.setText("123456");
    }
    
    @FXML
    void regist(ActionEvent event) { 	              // ����ע�����
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