package communityhousecontroller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import communityhousemodel.FacilityModel;
import communityhouseservice.FacilityService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


public class Tab2Controller implements Initializable{
	public static ObservableList<FacilityModel> facilityData;
	public static FacilityModel choosingFacModel;
	public FacilityService facService = new FacilityService();
	HomePageController homePageController = new HomePageController();
	
	public static Stage stage, currStage;

	@FXML TableView<FacilityModel> facilityTable;
	@FXML private TableColumn<FacilityModel, String> c1;
	@FXML private TableColumn<FacilityModel, String> c2;
	@FXML private TableColumn<FacilityModel, String> c3;
	@FXML private TableColumn<FacilityModel, String> c4;
	@FXML private TableColumn<FacilityModel, String> c5;
	@FXML private TableColumn<FacilityModel, String> c6;
	
	
	
	@FXML private Label logoutLabel;
	@FXML private TextField search1;
	@FXML private TextField search2;
	@FXML private TextField search3;
	@FXML private JFXButton searchBtn;
	@FXML private JFXButton resetBtn;
	@FXML private Label welcome;
	
	ToggleGroup status = new ToggleGroup();
	@FXML private JFXRadioButton radio1;
	@FXML private JFXRadioButton radio2;
	@FXML private JFXRadioButton radio3;
	
	@FXML private JFXButton addBtn;
	@FXML private JFXButton delBtn;
	@FXML private JFXButton updateBtn;
	@FXML private JFXButton statisticBtn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		welcome.setText("Welcome back, " + LoginController.currentUser.getUsername());
		buildData();
		buildRadios();
		
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
	
	public void onSearchBtn() {
		try {
			facilityData = FXCollections.observableArrayList(facService.searchFacility(search1.getText(), search2.getText(), search3.getText()));
			facilityTable.setItems(facilityData);
			
			JFXRadioButton rb = (JFXRadioButton)status.getSelectedToggle(); 
            if (rb != null) { 
                String s = rb.getText();
                if(! s.equals("Tất cả")) {
                	if(s.equals("Đang cho thuê")) {
                		int sz = facilityData.size();
                		for(int i=0; i<sz; i++) {
                			if(facilityData.get(i).getAvailable() == facilityData.get(i).getTotalQuantity()) {
                				facilityData.remove(i);
                				i--; sz--;
                			}
                		}
                	} else {
                		int sz = facilityData.size();
                		for(int i=0; i<sz; i++) {
                			if(facilityData.get(i).getAvailable() != 0) {
                				facilityData.remove(i);
                				i--; sz--;
                			}
                		}
                	}
                }
            } 
		} catch (Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setHeaderText("Có lỗi xảy ra");
			a.showAndWait();
			e.printStackTrace();
		}
	}
	
	public void onResetBtn() {
		radio1.setSelected(true);
		search1.clear();
		search2.clear();
		search3.clear();
		buildData();
	}
	
	public void buildRadios() {
		radio1.setToggleGroup(status);
		radio2.setToggleGroup(status);
		radio3.setToggleGroup(status);
		radio1.setSelected(true);
		status.selectedToggleProperty().addListener(new ChangeListener<Toggle>() { 
			@Override
            public void changed(ObservableValue<? extends Toggle> ob, Toggle o1, Toggle o2) { 
                onSearchBtn();
            }
        });
	}
	
	public void onAddBtn() {
		try {
			currStage = (Stage) addBtn.getScene().getWindow();
			stage = new Stage();
			Scene scene;
			scene = new Scene(FXMLLoader.load(getClass() .getResource("/communityhouseview/AddFacility.fxml")));
			stage.setScene(scene);;
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onDelBtn() {
		try {
			Alert a = new Alert(AlertType.CONFIRMATION);
			a.setHeaderText("Xác nhận xóa?");
			Optional<ButtonType> option = a.showAndWait();
	        if (option.get() == ButtonType.OK) {
	        	FacilityModel facModel = facilityTable.getSelectionModel().getSelectedItem();
	        	if(facService.deleteFacility(facModel.getFacilityId())==true) {
	        		facilityData.remove(facModel);

	        		Alert b = new Alert(AlertType.INFORMATION);
	        		b.setHeaderText("Xóa thành công");
	        		b.showAndWait();
	        	} else {;
	        		Alert b = new Alert(AlertType.ERROR);
	        		b.setHeaderText("Xóa thất bại");
	        		b.showAndWait();
	        	}
	        } else {}
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}

	public void onUpdateBtn() {
		try {
			choosingFacModel = facilityTable.getSelectionModel().getSelectedItem();
			stage = new Stage();
			Scene scene;
			scene = new Scene(FXMLLoader.load(getClass() .getResource("/communityhouseview/UpdateFacility.fxml"))); 
			stage.setScene(scene);;
			stage.centerOnScreen();
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onStatisticBtn() {
		try {
			Stage s = new Stage();
			Scene scene;
			scene = new Scene(FXMLLoader.load(getClass() .getResource("/communityhouseview/StatisticFacility.fxml")));
			s.setScene(scene);;
			s.centerOnScreen();
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void buildData() {
		try {
			facilityData = FXCollections.observableList(facService.getAllFacilities());
			System.out.println(facilityData);
	        
	        c1.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFacilityId())));
	        c2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFacilityName()));
	        c3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFacilityKind()));
	        c4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesciption()));
	        c5.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalQuantity())));
	        c6.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalQuantity()-cellData.getValue().getAvailable())));
	        
	        //on double clicked for row
	        facilityTable.setRowFactory( tv -> {
	            TableRow<FacilityModel> row = new TableRow<>();
	            row.setOnMouseClicked(event -> {
	                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
	                    onUpdateBtn();
	                }
	            });
	            return row ;
	        });
	        
	        facilityTable.setItems(facilityData);

		} catch (Exception e) {
			e.printStackTrace();
		}
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
