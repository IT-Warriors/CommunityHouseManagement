package communityhousecontroller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import communityhousebean.UserBean;
import communityhousemodel.ContractModel;
import communityhousemodel.EventModel;
import communityhousemodel.FacilityModel;
import communityhousemodel.UserAccountModel;
import communityhouseservice.ContractService;
import communityhouseservice.EventService;
import communityhouseservice.FacilityService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HomePageController implements Initializable {
	@FXML private JFXDatePicker datePicker;
	@FXML private Label welcome;
	@FXML private Label cell00, cell10, cell01, cell11, cell02, cell12, cell03, cell13;
	UserAccountModel user;

	ContractService contractService = new ContractService();
	EventService eventService = new EventService();

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		user = LoginController.currentUser;
		welcome.setText("Welcome back, " + user.getUsername());
		initNews();
		datePicker.setValue(LocalDate.now());
		datePicker.setEditable(false);
	}

	public void logOut(){
		Stage genStage = (Stage) datePicker.getScene().getWindow();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn đăng suất không?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if(alert.getResult() == ButtonType.YES){
			genStage.close();
			Platform.exit();
			System.exit(0);
		}
	}

	public void initNews(){
		int count = 0;
		Date lastedDate = new Date();
		List<EventModel> eventModels = new ArrayList<>();
		FacilityModel facilityModel = new FacilityModel();
		try {
			List<FacilityModel> list = new FacilityService().getFacilityByLastUpdate();
			facilityModel = list.isEmpty()?null: list.get(0);
			lastedDate = contractService.getLasted();
			List<ContractModel> contractModels = contractService.getListContract();
			eventModels = eventService.getLastedEvent();
			for(ContractModel c: contractModels){
				if(c.getIsAccepted() == 0){
					count++;
				}
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String contract = "<Contract> Có " + count + " đơn đăng ký mới!";
		int s = eventModels.size();
		if(s == 1){
			cell01.setText("<Event> " + eventModels.get(0).getEventName());
			cell11.setText(eventModels.get(0).getFromDate().toString());
		}
		if(s == 2){
			cell01.setText("<Event> " + eventModels.get(0).getEventName());
			cell11.setText(eventModels.get(0).getFromDate().toString());
			cell02.setText("<Event> " + eventModels.get(1).getEventName());
			cell12.setText(eventModels.get(1).getFromDate().toString());
		}
		if(facilityModel != null){
			cell03.setText("<Action> Thay đổi " + facilityModel.getFacilityName() + " trong kho");
			cell13.setText(facilityModel.getLastUpdate().toString());
		} else {
			cell03.setText("");
			cell13.setText("");
		}
		cell00.setText(contract);
		cell10.setText(lastedDate.toString());
	}
}
