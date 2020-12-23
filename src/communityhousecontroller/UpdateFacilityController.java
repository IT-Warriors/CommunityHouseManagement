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

public class UpdateFacilityController implements Initializable{
	FacilityService facService = new FacilityService();
	FacilityModel oldFacModel = Tab2Controller.choosingFacModel;
	FacilityModel newFacModel = oldFacModel;
	@FXML private TextField tfname; //ten csvc
	@FXML private TextField tfkind; //chung loai
	@FXML private TextField tfprice; //chung loai
	@FXML private TextField tfquan;
	@FXML private TextArea tfdes; //mo ta
	@FXML private Button backBtn;
	@FXML private Button updateBtn;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initData();
	}
	
	public void initData() {
		tfname.setText(oldFacModel.getFacilityName());
		tfkind.setText(oldFacModel.getFacilityKind());
		tfprice.setText(String.valueOf(oldFacModel.getPrice()));
		tfdes.setText(oldFacModel.getDesciption());
		tfquan.setText(String.valueOf(oldFacModel.getTotalQuantity()));
	}
	
	public void onBackBtn() {
		Stage s = (Stage) backBtn.getScene().getWindow();
		s.close();
	}
	
	public void onUpdateBtn() {
		try {
			int quantity = Integer.parseInt(tfquan.getText());
			int hiredQuantity = oldFacModel.getTotalQuantity()-oldFacModel.getAvailable(); 
			if(quantity >= hiredQuantity) {
				newFacModel.setTotalQuantity(quantity);
				newFacModel.setAvailable(quantity-hiredQuantity);
				newFacModel.setFacilityName(tfname.getText());
				newFacModel.setFacilityKind(tfkind.getText());
				newFacModel.setPrice(Long.parseLong(tfprice.getText()));
				newFacModel.setDesciption(tfdes.getText());
				if(facService.updateFacility(newFacModel)==true) {
					Alert a = new Alert(AlertType.INFORMATION);
					a.setHeaderText("Update thành công!");
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
					a.setHeaderText("Update thất bại! (false)");
					a.showAndWait();
				};
			} else {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Update thất bại!");
				a.setContentText("Số lượng mới < số lượng hiện đã cho thuê");
				a.showAndWait();
			}
		} catch(Exception e) {
			e.printStackTrace();
			Alert a = new Alert(AlertType.ERROR);
			a.setHeaderText("Update thất bại! (Exception)");
			a.showAndWait();
		}
	}
	
}
