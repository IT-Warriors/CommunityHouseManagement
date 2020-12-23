package communityhousecontroller;

import communityhousebean.ContractBean;
import communityhousebean.UserBean;
import communityhouseservice.beanservice.ContractBeanService;
import communityhouseservice.beanservice.UserBeanService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterController3 implements Initializable {
    @FXML
    public TextField editName;
    @FXML
    public TextField editPhone;
    @FXML
    public TextField editGender;
    @FXML
    public TextField editAddress;
    @FXML
    public TextField editCMND;
    @FXML
    public TextField editBirthDate;
    @FXML private Label welcome;

    ObservableList<String> gender = FXCollections.observableArrayList("Nam", "Nữ");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
        welcome.setText("Welcome back, " + LoginController.currentUser.getUsername());
    }

    public void logOut(){
        Stage genStage = (Stage) editBirthDate.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn đăng suất không?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            genStage.close();
            Platform.exit();
            System.exit(0);
        }
    }

    public void initData(){
        UserBean userBean = new UserBeanService().getUserBeanById(LoginController.currentUser.getUserId());
        editName.setText(userBean.getNhanKhauBean().getNhanKhauModel().getHoTen());
        editPhone.setText(userBean.getAccountModel().getPhoneNumber());
        editAddress.setText(userBean.getNhanKhauBean().getNhanKhauModel().getDiaChiHienNay());
        editCMND.setText(userBean.getNhanKhauBean().getChungMinhThuModel().getSoCMT());
        editGender.setText(userBean.getNhanKhauBean().getNhanKhauModel().getGioiTinh());
        editBirthDate.setText(userBean.getNhanKhauBean().getNhanKhauModel().getNamSinh().toString());
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
