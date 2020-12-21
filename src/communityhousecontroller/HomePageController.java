package communityhousecontroller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageController {
	@FXML private JFXDatePicker datePicker;

	@FXML
	public void onHomeBtn(ActionEvent e){
		try {
			Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
			Scene tab;
			tab = new Scene(FXMLLoader.load(getClass() .getResource("/communityhouseview/HomePage.fxml")));
			s.setScene(tab);
			s.centerOnScreen();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	public void onBtn1(ActionEvent e) {
		try {
			Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
			Scene tab;
			tab = new Scene(FXMLLoader.load(getClass() .getResource("/communityhouseview/Tab1.fxml")));
			s.setScene(tab);
			s.centerOnScreen();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	@FXML
	public void onBtn2(ActionEvent e) {
		try {
			Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
			Scene tab;
			tab = new Scene(FXMLLoader.load(getClass() .getResource("/communityhouseview/Tab2.fxml")));
			s.setScene(tab);
			s.centerOnScreen();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	@FXML
	public void onBtn3(ActionEvent e) {
		try {
			Stage s = (Stage) ((Node) e.getSource()).getScene().getWindow();
			Scene tab;
			tab = new Scene(FXMLLoader.load(getClass() .getResource("/communityhouseview/Tab3.fxml")));
			s.setScene(tab);
			s.centerOnScreen();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
