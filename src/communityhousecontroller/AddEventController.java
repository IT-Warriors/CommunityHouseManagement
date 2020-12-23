package communityhousecontroller;

import communityhousebean.ContractBean;
import communityhousebean.EventBean;
import communityhousebean.HireBean;
import communityhousebean.UserBean;
import communityhousemodel.*;
import communityhouseservice.EventService;
import communityhouseservice.FacilityService;
import communityhouseservice.HallService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddEventController implements Initializable {
    @FXML
    private TextField eventNameFieldAdd;
    @FXML
    private DatePicker fromDateFieldAdd;
    @FXML
    private DatePicker toDateFieldAdd;
    @FXML
    private TextArea contentFieldAdd;
    @FXML
    private ComboBox<String> editHall;

    EventService eventService = new EventService();
    List<HallModel> listHall = new HallService().getAllHall();
    ObservableList<String> obHall;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComboBoxData();
    }

    @FXML
    public void backFromAddEventView(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    public void initComboBoxData(){
        List<String> hallNameList = new ArrayList<>();

        for (HallModel hall: listHall){
            hallNameList.add(hall.getHallName());
        }
        obHall = FXCollections.observableList(hallNameList);
        editHall.setItems(obHall);
    }

    @FXML
    public void addNewEvent(ActionEvent e) {
        Stage stageInsert = (Stage) ((Node) e.getSource()).getScene().getWindow();
        EventModel event = new EventModel();
        LocalDate fromDate = fromDateFieldAdd.getValue();
        LocalDate toDate = toDateFieldAdd.getValue();
        String eventName = eventNameFieldAdd.getText();
        String content = contentFieldAdd.getText();

        if(editHall.getValue() == null || fromDate == null || toDate == null || eventName == null || content == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn chưa điền đầy đủ thông tin", ButtonType.OK);
            alert.showAndWait();
        } else{
            Date from = java.sql.Date.valueOf(fromDate);
            Date to = java.sql.Date.valueOf(toDate);
            HallModel hallModel = listHall.get(editHall.getSelectionModel().getSelectedIndex());
            if(from.compareTo(to) > 0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn đang chọn sai ngày, xem lại nhé!", ButtonType.OK);
                alert.showAndWait();
            } else if(checkHallFree(hallModel, from) && checkHallFree(hallModel, to)){
                event.setEventName(eventName);
                event.setContent(content);
                event.setHallId(hallModel.getHallId());
                event.setFromDate(from);
                event.setToDate(to);
                try {
                    eventService.addNewEvent(event);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Thêm thành công!", ButtonType.OK);
                    alert.showAndWait();
                    stageInsert.close();
                    reloadStage();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Something went wrong!", ButtonType.OK);
                    alert.showAndWait();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Something went wrong!", ButtonType.OK);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Phòng không còn trống vào ngày bạn chọn!", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    public void reloadStage(){
        FXMLLoader loader = new FXMLLoader();
        Stage stage = Tab2Controller.currStage;
        loader.setLocation(getClass().getResource("/communityhouseview/Tab3.fxml"));
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


    public boolean checkHallFree(HallModel hall, Date date){
        try {
            List<EventModel> events = eventService.getAllEvents();
            for(EventModel e: events){
                if(e.getHallId() == hall.getHallId()){
                    Date from = e.getFromDate();
                    Date to = e.getToDate();
                    if(date.compareTo(from) >= 0 && date.compareTo(to) <= 0){
                        return false;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

}
