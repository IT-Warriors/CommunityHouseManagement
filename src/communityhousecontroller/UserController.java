package communityhousecontroller;

import communityhouseservice.beanservice.UserBeanService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UserController {
    UserBeanService userBeanService = new UserBeanService();
    public void btnRegister(ActionEvent e){
        FXMLLoader loader = new FXMLLoader();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        loader.setLocation(getClass().getResource("/communityhouseview/RegisterPage1.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        RegisterController1 tmp = loader.getController();
        tmp.initData(userBeanService.getUserBeanById(LoginController.currentUser.getUserId()));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void btnMyRegister(ActionEvent e){
        FXMLLoader loader = new FXMLLoader();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        loader.setLocation(getClass().getResource("/communityhouseview/RegisterPage2.fxml"));
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

    public void btnMyAccount(ActionEvent e){
        FXMLLoader loader = new FXMLLoader();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        loader.setLocation(getClass().getResource("/communityhouseview/RegisterPage3.fxml"));
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
