package ourcontroller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.*;

import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import views.MainFrame;
import java.awt.Window;
public class GeneralController implements Initializable {
	   @FXML private Button btn1;
	   @FXML private Button btn2;
	   
	   @Override
	   public void initialize(URL location, ResourceBundle resources) {	      
	   }
	 
	   public void onClose(ActionEvent event) {
		   System.exit(0);
	   }
	   
	   public void onBtn1(ActionEvent event) {
		   Stage genStage = (Stage) btn2.getScene().getWindow();
		   genStage.close();
		   CreateGeneralScene.genStage.close();
           MainFrame mainFrame = new MainFrame();
           mainFrame.setLocationRelativeTo(null);
           mainFrame.setResizable(false);
           mainFrame.setVisible(true);
	   }
	   
	   public void onBtn2(ActionEvent event) {
		   try {
			    Stage genStage = (Stage) btn2.getScene().getWindow();
			    genStage.close();
			    Scene QLCSVCScene = new Scene(FXMLLoader.load(getClass().getResource("/ourview/QLCSVCScene.fxml")));
		        Stage stage = new Stage();
		        stage.setScene(QLCSVCScene);
		        stage.centerOnScreen();	
		        stage.show();
		    }catch (IOException io){
		        io.printStackTrace();
		    }
	   }
}