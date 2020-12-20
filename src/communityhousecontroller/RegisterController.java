package communityhousecontroller;

import communityhousebean.ContractBean;
import communityhouseservice.ContractService;
import communityhouseservice.beanservice.ContractBeanService;
import communityhouseservice.beanservice.EventBeanService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private TableView<ContractBean> contractTableView;
    @FXML
    private TableColumn<ContractBean, String> contractIdCol;
    @FXML
    private TableColumn<ContractBean, String> hallNameCol;
    @FXML
    private TableColumn<ContractBean, String> fromDateCol;
    @FXML
    private TableColumn<ContractBean, String> toDateCol;
    @FXML
    private TableColumn<ContractBean, String> stateCol;

    private ContractBeanService contractBeanService = new ContractBeanService();

    private List<ContractBean> contractBeanList = new ArrayList<>();

    private ObservableList<ContractBean> contractList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initRegisterForm();
    }

    public void initRegisterForm(){
        contractBeanList = contractBeanService.getContractBeanByUserId(6);
        System.out.println(contractBeanList);

        contractList = FXCollections.observableArrayList(contractBeanList);
        contractIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getContractModel().getContractId())));
        hallNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventBean().getHall().getHallName()));
        fromDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventBean().getEvent().getFromDate())));
        toDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventBean().getEvent().getToDate())));
        stateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContractModel().getIsAccepted()==1?"Đã duyệt":"Chưa duyệt"));

        contractTableView.setItems(contractList);
    }

    public void updateRegister(ActionEvent e){
        ContractBean contractBean = contractTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        Stage stage = new Stage();
        loader.setLocation(getClass().getResource("/communityhouseview/UserContractDetail.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        UserContractDetailController tmp = new UserContractDetailController();
        tmp.initData(contractBean);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteRegister(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Xác nhận xóa?", ButtonType.YES, ButtonType.NO,
                ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            ContractBean contractSelect = contractTableView.getSelectionModel().getSelectedItem();
            ContractService contractService = new ContractService();
            EventBeanService eventBeanService = new EventBeanService();
            try {
                contractService.deleteContract(contractSelect.getContractModel().getContractId());
                eventBeanService.deleteEvent(contractSelect.getEventBean().getEvent().getEventId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
            contractList.remove(contractSelect);
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Xóa thành công!");
            alert.showAndWait();
        }
    }

    public void insertRegister(ActionEvent e){
        ContractBean contractBean = contractTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        loader.setLocation(getClass().getResource("/communityhouseview/RegisterPage1.fxml"));
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
}
