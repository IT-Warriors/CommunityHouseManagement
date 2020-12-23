package communityhousecontroller;

import communityhousebean.ContractBean;
import communityhouseservice.ContractService;
import communityhouseservice.beanservice.ContractBeanService;
import communityhouseservice.beanservice.EventBeanService;
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

public class RegisterController2 implements Initializable {
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
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML private Label welcome;

    public static Stage currStage;

    private ContractBeanService contractBeanService = new ContractBeanService();

    private List<ContractBean> contractBeanList = new ArrayList<>();

    private ObservableList<ContractBean> contractList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcome.setText("Welcome back, " + LoginController.currentUser.getUsername());
        deleteBtn.setDisable(true);
        updateBtn.setDisable(true);
        contractTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(contractTableView.getSelectionModel().getSelectedItem() != null && contractTableView.getSelectionModel().getSelectedItem().getContractModel().getIsAccepted() == 0){
                    deleteBtn.setDisable(false);
                    updateBtn.setDisable(false);
                } else {
                    deleteBtn.setDisable(true);
                    updateBtn.setDisable(true);
                }
            }
        });
        initRegisterForm();
    }

    public void logOut(){
        Stage genStage = (Stage) deleteBtn.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn đăng suất không?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            genStage.close();
            Platform.exit();
            System.exit(0);
        }
    }

    public void initRegisterForm(){
        contractBeanList = contractBeanService.getContractBeanByUserId(LoginController.currentUser.getUserId());

        contractList = FXCollections.observableArrayList(contractBeanList);
        contractIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getContractModel().getContractId())));
        hallNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEventBean().getHall().getHallName()));
        fromDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventBean().getEvent().getFromDate())));
        toDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEventBean().getEvent().getToDate())));
        stateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContractModel().getIsAccepted()==1?"Đã duyệt":"Chưa duyệt"));

        contractTableView.setItems(contractList);
    }

    public void updateRegister(ActionEvent e){
        currStage = (Stage) deleteBtn.getScene().getWindow();

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
        UserContractDetailController tmp = loader.getController();
        tmp.initData(contractBean);
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
        }
    }

    public void deleteRegister(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Xác nhận xóa?", ButtonType.YES, ButtonType.NO,
                ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            ContractBean contractSelect = contractTableView.getSelectionModel().getSelectedItem();
            contractBeanService.deleteContract(contractSelect);
            contractList.remove(contractSelect);
            alert.setContentText("Xóa thành công!");
            alert.showAndWait();
        }
    }

    public void insertRegister(ActionEvent e){
        UserController userController = new UserController();
        userController.btnRegister(e);
    }

    public void RegisterBtn(ActionEvent e){
        UserController userController = new UserController();
        userController.btnRegister(e);
    }

    public void MyRegisterBtn(ActionEvent e){
        UserController userController = new UserController();
        userController.btnMyRegister(e);
    }

    public void MyAccountBtn(ActionEvent e){
        UserController userController = new UserController();
        userController.btnMyAccount(e);
    }
}
