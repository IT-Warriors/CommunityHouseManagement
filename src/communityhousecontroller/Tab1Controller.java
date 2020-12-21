package communityhousecontroller;

import com.jfoenix.controls.JFXToggleButton;
import communityhousebean.ContractBean;
import communityhouseservice.beanservice.ContractBeanService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Tab1Controller implements Initializable {
    HomePageController homePageController = new HomePageController();

    @FXML
    private TextField contactId, eventName;
    @FXML
    private DatePicker fromDate;
    @FXML
    private Label welcome, signOut;
    @FXML
    private JFXToggleButton isAccepted;
    @FXML
    private TableView<ContractBean> contractTableView;
    @FXML
    private TableColumn<ContractBean, String> contractIdCol, fullNameCol, eventNameCol, hallNameCol, fromDateCol, toDateCol, createDateCol, isAcceptedCol;

    ContractBeanService contractBeanService = new ContractBeanService();
    List<ContractBean> contractBeanList = new ArrayList<>();
    ObservableList<ContractBean> contractList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initContractTable();
    }

    public void initContractTable(){
        contractBeanList = contractBeanService.getAllContractBean();

        contractList = FXCollections.observableArrayList(contractBeanList);
        contractIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getContractModel().getContractId())));
        hallNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventBean().getHall().getHallName()));
        fromDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventBean().getEvent().getFromDate())));
        toDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventBean().getEvent().getToDate())));
        isAcceptedCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContractModel().getIsAccepted()==1?"Đã duyệt":"Chưa duyệt"));
        fullNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNhanKhauBean().getNhanKhauModel().getHoTen()));
        eventNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventBean().getEvent().getEventName()));
        createDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContractModel().getCreateDate().toString()));

        contractTableView.setItems(contractList);
    }

    public void onSearchButton(ActionEvent e){

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
