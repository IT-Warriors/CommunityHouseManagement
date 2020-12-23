package communityhousecontroller;

import communityhousebean.ContractBean;
import communityhousebean.HireBean;
import communityhouseservice.ContractService;
import communityhouseservice.beanservice.ContractBeanService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private boolean isAdminInsert = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(ContractBean contractBean){
        contract = contractBean;
        username.setText(contract.getNhanKhauBean().getNhanKhauModel().getHoTen());
        cmnd.setText(contract.getNhanKhauBean().getChungMinhThuModel().getSoCMT());
//        System.out.println(contract.getUserAccountModel().getPhoneNumber());
        phoneNum.setText(contract.getUserAccountModel().getPhoneNumber());
        date.setText(contract.getEventBean().getEvent().getFromDate() + " - " + contractBean.getEventBean().getEvent().getToDate());
        hallId.setText(contract.getEventBean().getHall().getHallId() + "");
        cost.setText(contract.getContractModel().getCost() + "");
        hireBeanObservableList = FXCollections.observableArrayList(contract.getFacilityModelList());
        if(LoginController.currentUser.getType() == 0){
            button2.setText("OK");
        } else {
            if(isAdminInsert){
                button2.setText("ADD");
            } else {
                button2.setText("Confirm");
            }
        }
        if(contract.getContractModel().getIsAccepted() == 1){
            button2.setDisable(true);
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
        } else {
            if(isAdminInsert){
                buttonOkAdmin();
            } else {
                confirmContract();
            }
        }
    }

    public void confirmContract(){
        contract.getContractModel().setIsAccepted(1);
        ContractService contractService = new ContractService();
        try {
            contractService.updateContract(contract.getContractModel());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Đã xác nhận!", ButtonType.OK);
            alert.showAndWait();
            Tab1Controller.stage.close();
            resetStageInsert();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void buttonOkUser(){
        contractBeanService.insertContract(contract);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Gửi thành công!", ButtonType.OK);
        alert.showAndWait();
        RegisterController1.stage.close();
        resetStageInsert();
    }

    public void buttonOkAdmin(){
        contractBeanService.insertContract(contract);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Gửi thành công!", ButtonType.OK);
        alert.showAndWait();
        InsertContractController.stage.close();
        resetStageInsert();
    }

    public void resetStageInsert(){
        FXMLLoader loader = new FXMLLoader();
        Stage stage;
        if(isAdminInsert){
            stage = InsertContractController.stageInsert;
            loader.setLocation(getClass().getResource("/communityhouseview/InsertContractForm.fxml"));
        } else if(LoginController.currentUser.getType() == 0){
            stage = RegisterController1.insertStage;
            loader.setLocation(getClass().getResource("/communityhouseview/RegisterPage1.fxml"));
        } else {
            stage = Tab1Controller.tab1Stage;
            loader.setLocation(getClass().getResource("/communityhouseview/Tab1.fxml"));
        }


        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void buttonCancel(){
        Stage stage = (Stage) button2.getScene().getWindow();
        stage.close();
    }

    public void setAdminInsert(boolean adminInsert) {
        isAdminInsert = adminInsert;
    }

    public boolean isAdminInsert() {
        return isAdminInsert;
    }
}
