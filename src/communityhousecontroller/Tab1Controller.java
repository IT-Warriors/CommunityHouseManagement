package communityhousecontroller;

import com.jfoenix.controls.JFXToggleButton;
import communityhousebean.ContractBean;
import communityhouseservice.beanservice.ContractBeanService;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Tab1Controller implements Initializable {
    HomePageController homePageController = new HomePageController();

    @FXML
    private TextField contractId, eventName;
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
    @FXML
    private Button deleteBtn;

    public static Stage stage;
    public static Stage tab1Stage;

    ContractBeanService contractBeanService = new ContractBeanService();
    List<ContractBean> contractBeanList = new ArrayList<>();
    ObservableList<ContractBean> contractList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcome.setText("Welcome Back, " + LoginController.currentUser.getUsername());
        initContractTable();
        deleteBtn.setDisable(true);
        contractTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deleteBtn.setDisable(false);
            }
        });

        contractTableView.setRowFactory(tv -> {
            TableRow<ContractBean> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ContractBean selectedContract = row.getItem();
                    showContractForm(selectedContract);
                }
            });
            return row;
        });
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

    public void logOut(){
        Stage genStage = (Stage) eventName.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn đăng suất không?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            genStage.close();
            Platform.exit();
            System.exit(0);
        }
    }

    public void showContractForm(ContractBean contractBean){
        tab1Stage = (Stage) deleteBtn.getScene().getWindow();
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/communityhouseview/ContractForm.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ContractFormController duc = loader.getController();
        duc.initData(contractBean);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void onSearchButton(ActionEvent e){
        String id = contractId.getText();
        String name = eventName.getText();
        String date = "";
        String check;
        if(isAccepted.isSelected()){
            check = "0";
        } else {
            check = "";
        }
        if(fromDate.getValue() != null){
            date = fromDate.getValue().toString();
        }
        try {
            contractList = FXCollections.observableArrayList(contractBeanService.searchContract(id, name, date, check));
            contractTableView.setItems(contractList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
    }

    public void onBtnAdd(ActionEvent e){
        FXMLLoader loader = new FXMLLoader();
        Stage stage = new Stage();
        loader.setLocation(getClass().getResource("/communityhouseview/InsertContractForm.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle("Quit application");
        alert.setContentText(String.format("Close without saving?"));
        Optional<ButtonType> res = alert.showAndWait();

        if(res.isPresent()) {
            if(res.get().equals(ButtonType.CANCEL))
                event.consume();
            if(res.get().equals(ButtonType.YES))
                initContractTable();
        }
    }

    public void onBtnDelete(ActionEvent e){
        ContractBean selected = contractTableView.getSelectionModel().getSelectedItem();
        contractBeanService.deleteContract(selected);
        contractList.remove(selected);
        if(contractList.size() == 0){
            deleteBtn.setDisable(true);
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
