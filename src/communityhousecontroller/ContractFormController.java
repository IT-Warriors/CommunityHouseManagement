package communityhousecontroller;

import communityhousebean.ContractBean;
import communityhousebean.HireBean;
import communityhouseservice.beanservice.ContractBeanService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ContractFormController implements Initializable {
    @FXML
    public Text username, cmnd, phoneNum, date, manager, hallId;
    @FXML
    public TableView<HireBean> tableHireView;
    @FXML
    public TableColumn<HireBean, String> indexCol, nameCol, quantityCol, priceCol;
    @FXML
    public Label cost;
    @FXML
    public Button button2;

    ContractBean contract;
    ObservableList<HireBean> hireBeanObservableList;
    ContractBeanService contractBeanService = new ContractBeanService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(ContractBean contractBean){
        contract = contractBean;
        username.setText(contract.getNhanKhauBean().getNhanKhauModel().getHoTen());
        cmnd.setText(contract.getNhanKhauBean().getChungMinhThuModel().getSoCMT());
        phoneNum.setText(contract.getUserAccountModel().getPhoneNumber());
        date.setText(contract.getEventBean().getEvent().getFromDate() + " - " + contractBean.getEventBean().getEvent().getToDate());
        hallId.setText(contract.getEventBean().getHall().getHallId() + "");
        cost.setText(contract.getContractModel().getCost() + "");
        hireBeanObservableList = FXCollections.observableArrayList(contract.getFacilityModelList());
        if(LoginController.currentUser.getType() == 0){
            button2.setText("OK");
        } else {
            button2.setText("Confirm");
        }
        initTableHire();
    }

    public void initTableHire(){
        indexCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(hireBeanObservableList.indexOf(cellData.getValue())+1)));
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFacility().getFacilityName()));
        quantityCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getHiredQuantity())));
        priceCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFacility().getPrice())));
        tableHireView.setItems(hireBeanObservableList);
    }

    public void buttonOk(){
        int type = LoginController.currentUser.getType();
        if(type == 0){
            buttonOkUser();
        }
    }

    public void buttonOkUser(){
        contractBeanService.insertContract(contract);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Gửi thành công!", ButtonType.OK);
        alert.showAndWait();
        RegisterController1.stage.close();
    }
    public void buttonCancel(){
        RegisterController1.stage.close();
    }
}
