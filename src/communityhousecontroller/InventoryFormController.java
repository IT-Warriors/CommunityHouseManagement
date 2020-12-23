package communityhousecontroller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import communityhousemodel.FacilityModel;
import communityhouseservice.FacilityService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class InventoryFormController implements Initializable{
	FacilityService facService =  new FacilityService();
	@FXML private TableView<FacilityModel> table;
	ObservableList<FacilityModel> data = FXCollections.observableArrayList();
	
	@FXML private TableColumn<FacilityModel, String> c1;
	@FXML private TableColumn<FacilityModel, String> c2;
	@FXML private TableColumn<FacilityModel, String> c3;
	@FXML private TableColumn<FacilityModel, String> c4;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTable();
	}
	
	public void initTable() {
		try {
			data = FXCollections.observableArrayList(facService.getAllFacilities());
			c1.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFacilityId())));
			c2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFacilityName()));
			c3.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalQuantity())));
			c4.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalQuantity()-cellData.getValue().getAvailable())));
			table.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
