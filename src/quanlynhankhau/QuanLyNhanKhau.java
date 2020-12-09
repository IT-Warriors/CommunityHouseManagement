package quanlynhankhau;

import java.util.Calendar;
import javax.swing.UIManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import views.LoginUI;
import views.MainFrame;

/**
 *
 * @author Hai
 */
public class QuanLyNhanKhau{
    public static Calendar calendar = Calendar.getInstance();
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        LoginUI loginUI = new LoginUI();
        loginUI.setVisible(true); 
    }
}
