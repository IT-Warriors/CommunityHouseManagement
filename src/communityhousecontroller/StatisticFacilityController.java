package communityhousecontroller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import communityhousemodel.FacilityModel;
import communityhouseservice.FacilityService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class StatisticFacilityController implements Initializable{
	FacilityService facService = new FacilityService();
	ObservableList<FacilityModel> data = FXCollections.observableArrayList();
	@FXML private TableView<FacilityModel> table;
	@FXML private TableColumn<FacilityModel, String> c1;
	@FXML private TableColumn<FacilityModel, String> c2;
	@FXML private TableColumn<FacilityModel, String> c3;
	@FXML private TableColumn<FacilityModel, String> c4;
	@FXML private TableColumn<FacilityModel, String> c5;
	@FXML private TableColumn<FacilityModel, String> c6;
	@FXML private TableColumn<FacilityModel, String> c7;
	@FXML private TableColumn<FacilityModel, String> c8;
	
	@FXML private ComboBox<String> kindComboBox;
	@FXML private ComboBox<String> sortComboBox;
	@FXML private JFXButton kiemkeBtn;
	
	@FXML private TableView<FacilityModel> facilityTableView;
	@FXML private TableColumn<FacilityModel, String> facilityIdCol, facilityNameCol, facilityKindCol, facilityDesCol, facilityQuantityCol, facilityHireCol, facilityPriceCol, facilityLastUpdateCol;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTable();
		buildKind();
		buildSort();
	}

	public void buildKind() {
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("Tất cả");
		list.addAll(facService.getKinds());
		kindComboBox.setItems(list);
	}
	public void buildSort() {
		ObservableList<String> list = FXCollections.observableArrayList("Chưa sắp xếp", "Theo tên A-Z", "Theo tên Z-A", "Theo ngày Update");
		sortComboBox.setItems(list);
	}
	
	public void onChangeKind() {
		sortComboBox.setValue("Chưa sắp xếp");
		try {
			if(kindComboBox.getValue().equals("Tất cả")) {
				data = FXCollections.observableArrayList(facService.getAllFacilities());
			} else {
				data = facService.getFacilitiesByKind(kindComboBox.getValue());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		table.setItems(data);
	}
	
	public void onChangeSort() {
		try {
			if(! sortComboBox.getValue().equals("Chưa sắp xếp")) {
				if(sortComboBox.getValue().equals("Theo tên A-Z")) {
					data = FXCollections.observableArrayList(facService.getFacilityByNameAToZ());
					removeByKind();
				} else if(sortComboBox.getValue().equals("Theo tên Z-A")) {
					data = FXCollections.observableArrayList(facService.getFacilityByNameZToA());
					removeByKind();
				} else {
					data = FXCollections.observableArrayList(facService.getFacilityByLastUpdate());
					removeByKind();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		table.setItems(data);
	}
	public void removeByKind() {
		String kind = kindComboBox.getValue();
		if(! kind.equals("Tất cả")) {
			int sz = data.size();
			for(int i=0; i<sz; i++) {
				if(! data.get(i).getFacilityKind().equals(kind)) {
					data.remove(data.get(i));
					i--; sz--;
				}
			}
		}

	}
	
	public void onKiemkeBtn() {
		try {
			Stage s = new Stage();
			Scene scene;
			scene = new Scene(FXMLLoader.load(getClass() .getResource("/communityhouseview/InventoryForm.fxml")));
			s.setScene(scene);;
			s.centerOnScreen();
			s.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void initTable() {
		try {
			data = FXCollections.observableArrayList(facService.getAllFacilities());
			c1.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFacilityId())));
	        c2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFacilityName()));
	        c3.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFacilityKind()));
	        c4.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesciption()));
	        c5.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalQuantity())));
	        c6.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalQuantity()-cellData.getValue().getAvailable())));
	        c7.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
	        c8.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getLastUpdate())));
	        table.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
