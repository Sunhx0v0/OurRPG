package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.Window;

public class menucontroller implements Initializable{
    @FXML
    private Button exitGameButton;

    @FXML
    private CheckBox voiceCheckBox;

    @FXML
    private Button aboutUsButton;

    @FXML
    private Slider voiceVolumn;

    @FXML
    private Button saveButton;

    @FXML
    private Button returnButton;
    
	@FXML
    void DragDone(MouseEvent event) {
		System.out.println("menucontroller.DragDone()");
		menuStage.voice=voiceVolumn.getValue();
    }
	
	@FXML
    void CheckBoxClicked(MouseEvent event) {
		System.out.println("menucontroller.CheckBoxClicked()");
		boolean Music=voiceCheckBox.isSelected();
		if (Music) {
			gameStage.mPlayer.play();
			gameStage.PlayBGM=true;
		}
		else {
			gameStage.mPlayer.pause();
			gameStage.PlayBGM=false;
		}
    }
	@FXML
	void saveButtonClicked(MouseEvent event) {   // 保存
		gameStage.loadOrSave = false;
		Stage primaryStage = (Stage) saveButton.getScene().getWindow();
		primaryStage.close();
		gameStage.menuDisplay = false;
		try {
			new fileStage().start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @FXML
    void returnButtonClicked(MouseEvent event) {
    	Stage stage = (Stage)returnButton.getScene().getWindow();
        stage.close();
        gameStage.menuDisplay=false;
    }

    @FXML
    void exitGameButtonClicked(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void aboutUsButtonClicked(MouseEvent event) {
    	try {
			new aboutUsStage().start(new Stage());
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		voiceVolumn.setValue(menuStage.voice);
		voiceCheckBox.setSelected(gameStage.PlayBGM);
	    (gameStage.mPlayer).volumeProperty().bind(voiceVolumn.valueProperty().divide(100));
	}

}

class aboutUsStage extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		try {
			Parent loginroot = FXMLLoader.load(getClass().getResource("aboutus.fxml"));
            primaryStage.setTitle("关于我们");
            primaryStage.setScene(new Scene(loginroot));
            primaryStage.setResizable(false);
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}