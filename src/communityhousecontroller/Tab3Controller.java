package communityhousecontroller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.mysql.jdbc.Connection;

import communityhousebean.EventBean;
import communityhousemodel.EventModel;
import communityhousemodel.HallModel;
import communityhouseservice.EventService;
import communityhouseservice.HallService;
import communityhouseservice.beanservice.EventBeanService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.MysqlConnection;

public class Tab3Controller implements Initializable {

	@FXML
	private TableView<EventBean> eventTable;
	@FXML
	private TableColumn<EventBean, String> eventIdCol;
	@FXML
	private TableColumn<EventBean, String> eventNameCol;
	@FXML
	private TableColumn<EventBean, String> hallNameCol;
	@FXML
	private TableColumn<EventBean, String> fromDateCol;
	@FXML
	private TableColumn<EventBean, String> toDateCol;
	@FXML
	private TableColumn<EventBean, String> contentCol;

	@FXML
	private TextField eventIdField;
	@FXML
	private TextField eventNameField;
	@FXML
	private DatePicker dateField;
	@FXML
	private JFXButton searchBtn;
	@FXML
	private Label welcome;

	@FXML
	private JFXButton deleteBtn;
	@FXML
	private JFXButton updateBtn;
	@FXML
	private JFXButton resetBtn;

	private EventService eventService = new EventService();
	private EventBeanService eventBeanService = new EventBeanService();

	ObservableList<EventBean> listEventBean;

	HomePageController homePageController = new HomePageController();
	public static Stage currStage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcome.setText("Welcome back, " + LoginController.currentUser.getUsername());
		try {
			listEventBean = FXCollections.observableList(new EventBeanService().getAllEventBean());
			System.out.println(listEventBean.size());
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		initEventTable();
		setVisibleDeleteUpdateBtn();
	}

	public void initEventTable() {
		eventIdCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEvent().getEventId())));
		eventNameCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEvent().getEventName())));
		hallNameCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getHall().getHallName())));
		fromDateCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEvent().getFromDate())));
		toDateCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEvent().getToDate())));
		contentCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEvent().getContent())));
		eventTable.setItems(listEventBean);
	}

	@FXML
	public void showAddEventView(ActionEvent e) {
		currStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/communityhouseview/AddEvent.fxml"));
		Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void showUpdateEventView(ActionEvent e) {
		currStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		EventBean eventBeanSelected = eventTable.getSelectionModel().getSelectedItem();
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/communityhouseview/UpdateEvent.fxml"));
		Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		UpdateEventController tmp = loader.getController();
		tmp.initEventSelectedData(eventBeanSelected);
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.show();
	}

	public void setVisibleDeleteUpdateBtn() {
		deleteBtn.setDisable(true);
		updateBtn.setDisable(true);
		eventTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (eventTable.getSelectionModel().getSelectedItem() != null) {
					deleteBtn.setDisable(false);
					updateBtn.setDisable(false);
				} else {
					deleteBtn.setDisable(true);
					deleteBtn.setDisable(true);
				}
			}
		});
	}

	@FXML
	public void deleteEvent() {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Xác nhận xóa?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			EventBean eventBeanSelected = eventTable.getSelectionModel().getSelectedItem();
			if (new EventBeanService().deleteEvent(eventBeanSelected.getEvent().getEventId())){
				listEventBean.remove(eventBeanSelected);
			} else {
				alert = new Alert(AlertType.CONFIRMATION, "Xóa thất bại, sự kiện này do người dân đăng ký!", ButtonType.OK);
				alert.showAndWait();
			}
		}
	}

	@FXML
	public void searchBtn() {
		String eventId = eventIdField.getText();
		String eventName = eventNameField.getText();
		String date = "";
		if(dateField.getValue() != null){
			date = dateField.getValue().toString();
		}
		ObservableList<EventBean> list = eventBeanService.searchEvent(eventId, eventName, date);
		System.out.println(list.size());
		listEventBean = list;
		eventTable.setItems(listEventBean);
	}

	public void logOut() {
		Stage genStage = (Stage) resetBtn.getScene().getWindow();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn đăng suất không?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if(alert.getResult() == ButtonType.YES){
			genStage.close();
			Platform.exit();
			System.exit(0);
		}
	};

	public void resetEvent() {
		eventIdField.setText("");
		eventNameField.setText("");
		dateField.setValue(null);
		searchBtn();
	}



	public void onBtn1(ActionEvent e){
		homePageController.onBtn1(e);
	}
	public void onBtn2(ActionEvent e){
		homePageController.onBtn2(e);
	}
	public void onBtn3(ActionEvent e){
		homePageController.onBtn3(e);
	}
	public void onHomeBtn(ActionEvent e){
		homePageController.onHomeBtn(e);
	}
}
