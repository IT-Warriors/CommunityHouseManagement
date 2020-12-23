package communityhousecontroller;

import communityhousebean.EventBean;
import communityhousemodel.EventModel;
import communityhousemodel.HallModel;
import communityhouseservice.EventService;
import communityhouseservice.HallService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateEventController implements Initializable {
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

    EventBean event;

    EventService eventService = new EventService();
    List<HallModel> listHall = new HallService().getAllHall();
    ObservableList<String> obHall;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComboBoxData();
    }

    public void initComboBoxData(){
        List<String> hallNameList = new ArrayList<>();

        for (HallModel hall: listHall){
            hallNameList.add(hall.getHallName());
        }
        obHall = FXCollections.observableList(hallNameList);
        editHall.setItems(obHall);
    }

    public boolean checkHallFree(int eventId, HallModel hall, Date date){
        try {
            List<EventModel> events = eventService.getAllEvents();
            for(EventModel e: events){
                if(e.getEventId() != eventId && e.getHallId() == hall.getHallId()){
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

    public void initEventSelectedData(EventBean eventBeanSelected) {
        event = eventBeanSelected;
        eventNameFieldAdd.setText(eventBeanSelected.getEvent().getEventName());
        editHall.setValue(eventBeanSelected.getHall().getHallName());
        fromDateFieldAdd.setValue(
                LocalDate.parse(eventBeanSelected.getEvent().getFromDate().toString()));
        toDateFieldAdd.setValue(
                LocalDate.parse(eventBeanSelected.getEvent().getToDate().toString()));
        contentFieldAdd.setText(eventBeanSelected.getEvent().getContent());
    }

    @FXML
    public void backFromAddEventView(ActionEvent e) {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void updateEvent(ActionEvent e) {
        Stage stageInsert = (Stage) ((Node) e.getSource()).getScene().getWindow();
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
            int eventId = event.getEvent().getEventId();
            if(from.compareTo(to) > 0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn đang chọn sai ngày, xem lại nhé!", ButtonType.OK);
                alert.showAndWait();
            } else if(checkHallFree(eventId, hallModel, from) && checkHallFree(eventId, hallModel, to)){
                event.getEvent().setEventName(eventName);
                event.getEvent().setContent(content);
                event.getEvent().setHallId(hallModel.getHallId());
                event.getEvent().setFromDate(from);
                event.getEvent().setToDate(to);
                try {
                    eventService.updateEvent(event.getEvent());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Chỉnh sửa thành công!", ButtonType.OK);
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
        Stage stage = Tab3Controller.currStage;
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
}
