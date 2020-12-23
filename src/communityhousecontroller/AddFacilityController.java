package communityhousecontroller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import communityhousemodel.FacilityModel;
import communityhouseservice.FacilityService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AddFacilityController implements Initializable{
	FacilityService facService = new FacilityService();
	@FXML private TextField tfname; //ten csvc
	@FXML private TextField tfkind; //chung loai
	@FXML private TextField tfprice; //chung loai
	@FXML private TextField tfquan; // so luong
	@FXML private TextArea tfdes; //mo ta
	@FXML private Button backBtn;
	@FXML private Button addBtn;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public void onBackBtn() {
		Stage s = (Stage) backBtn.getScene().getWindow();
		s.close();
	}
	
	public void onAddBtn() {
		try {
			FacilityModel facModel = 
				new FacilityModel(tfname.getText(), Integer.parseInt(tfquan.getText()), Integer.parseInt(tfquan.getText()) , Long.parseLong(tfprice.getText(),10), tfdes.getText(), tfkind.getText());
			if(facService.addNewFacility(facModel)==true) {
				Tab2Controller.facilityData.add(facModel);
				tfdes.clear();
				tfkind.clear();
				tfname.clear();
				tfprice.clear();
				tfquan.clear();
				Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText("Thêm thành công!");
				a.showAndWait();
				onBackBtn();
				FXMLLoader loader = new FXMLLoader();
				Stage stage = Tab2Controller.currStage;
				loader.setLocation(getClass().getResource("/communityhouseview/Tab2.fxml"));

				Parent parent = null;
				try {
					parent = loader.load();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
				Scene scene = new Scene(parent);
				stage.setScene(scene);
				stage.show();
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Thêm thất bại! (false)");
				a.showAndWait();
			};
		} catch(Exception e) {
			Alert a = new Alert(AlertType.ERROR);
			a.setHeaderText("Thêm thất bại! (Exception)");
			a.showAndWait();
		}
	}
	
}
