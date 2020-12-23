package communityhousecontroller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXButton;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import communityhousemodel.UserAccountModel;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import views.LoginUI;
import views.MainFrame;

import javax.swing.*;

public class GeneralController implements Initializable {
	   @FXML private JFXButton btn1;
	   @FXML private JFXButton btn2;
	   @FXML private Label welcome;
	   @FXML private BorderPane root;

	   UserAccountModel user = LoginController.currentUser;
	   
	   @Override
	   public void initialize(URL location, ResourceBundle resources) {
	   		welcome.setText("Welcome back, " + user.getUsername());
		   DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
		   Node popupContent = datePickerSkin.getPopupContent();

		   root.setCenter(popupContent);
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
			    Scene QLCSVCScene = new Scene(FXMLLoader.load(getClass().getResource("/communityhouseview/HomePage.fxml")));
		        Stage stage = new Stage();
		        stage.setScene(QLCSVCScene);
		        stage.centerOnScreen();	
		        stage.show();
		    }catch (IOException io){
		        io.printStackTrace();
		    }
	   }

	public void logOut(){
		Stage genStage = (Stage) btn1.getScene().getWindow();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn đăng suất không?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if(alert.getResult() == ButtonType.YES){
			genStage.close();
			Platform.exit();
			System.exit(0);
		}
	}
}