package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PKStage extends Application{

	
	@Override
    public void start(Stage primaryStage) throws Exception {
		try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/pk.fxml"));

            primaryStage.setTitle("��ս����");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
//              System.out.print("���������ڹر�");
            	if(gameStage.PlayBGM) {
            		gameStage.mPlayer.play();
            	}
            	
            }
        });
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	

}
