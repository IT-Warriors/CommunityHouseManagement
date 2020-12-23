package communityhousecontroller;

import communityhousebean.ContractBean;
import communityhousebean.EventBean;
import communityhousebean.HireBean;
import communityhousebean.UserBean;
import communityhousemodel.*;
import communityhouseservice.EventService;
import communityhouseservice.FacilityService;
import communityhouseservice.HallService;
import communityhouseservice.beanservice.ContractBeanService;
import communityhouseservice.beanservice.UserBeanService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class InsertContractController implements Initializable {
    @FXML
    private TextField totalFee;
    @FXML
    private TextField editAddress;
    @FXML
    private TextField editName;
    @FXML
    private DatePicker editFromDate;
    @FXML
    private DatePicker editToDate;
    @FXML
    private TextField editPhone;
    @FXML
    private ComboBox<String> editHall;
    @FXML
    private ComboBox<String> editFacility;
    @FXML
    private TableView<HireBean> hireFacilities;
    @FXML
    private TableColumn<HireBean, String> indexCol;
    @FXML
    private TableColumn<HireBean, String> nameCol;
    @FXML
    private TableColumn<HireBean, String> quantityCol;
    @FXML
    private Spinner<Integer> editQuantity;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button addBtn;
    @FXML
    private CheckBox acceptCheckBox;
    @FXML
    private TextField editEventName;
    @FXML
    private TextField editContent;
    @FXML
    private ComboBox<String> selectUserId;

    private ObservableList<HireBean> hireBeanObservableList;

    static Stage stage;
    static Stage stageInsert;

    UserController userController = new UserController();

    ContractBeanService contractBeanService = new ContractBeanService();
    EventService eventService = new EventService();
    ObservableList<HallModel> listHall = FXCollections.observableList(new HallService().getAllHall());
    ObservableList<FacilityModel> listFacility;
    ObservableList<UserBean> listUser = FXCollections.observableList(new UserBeanService().getAllUserBean());

    ObservableList<String> obFacility;
    ObservableList<String> obHall;
    ObservableList<String> obUserId;

    UserBean user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editHall.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(editHall.getSelectionModel().getSelectedItem() != null){
                    caculateCost();
                }
            }
        });

        selectUserId.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(selectUserId.getSelectionModel().getSelectedItem() != null){
                    UserBean selected = listUser.get(selectUserId.getSelectionModel().getSelectedIndex());
                    user = selected;
                    editName.setText(selected.getNhanKhauBean().getNhanKhauModel().getHoTen());
                    editPhone.setText(selected.getAccountModel().getPhoneNumber());
                    editAddress.setText(selected.getNhanKhauBean().getNhanKhauModel().getDiaChiHienNay());
                }
            }
        });
        List<HireBean> hireBeanList = new ArrayList<>();
        hireBeanObservableList = FXCollections.observableList(hireBeanList);
        initComboBoxData();
        initTableHire();
    }


    public long caculateCost(){
        HallModel hallModel = new HallModel();
        long price = 0;
        for(HireBean hire: hireBeanObservableList){
            price += hire.getFacility().getPrice() * hire.getHiredQuantity();
        }
        if(editHall.getSelectionModel().getSelectedItem() != null){
            hallModel = listHall.get(editHall.getSelectionModel().getSelectedIndex());
        }
        price += hallModel.getPrice();
        totalFee.setText(price + "");
        return price;
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

    public void addHire(){
        String name = editFacility.getSelectionModel().getSelectedItem();
        FacilityModel facility = new FacilityModel();
        if(name != null){
            for(FacilityModel f: listFacility){
                if(f.getFacilityName().equals(name)){
                    facility = f;
                    break;
                }
            }
            int quantity = editQuantity.getValue();
            HireBean newHire = new HireBean();
            newHire.setHiredQuantity(quantity);
            newHire.setFacility(facility);
            obFacility.remove(name);
            hireBeanObservableList.add(newHire);
            caculateCost();
        }
    }

    public void deleteHire(){
        int selected = hireFacilities.getSelectionModel().getSelectedIndex();
        String name = hireFacilities.getSelectionModel().getSelectedItem().getFacility().getFacilityName();
        hireBeanObservableList.remove(selected);
        obFacility.add(name);
        if(hireBeanObservableList.size() == 0){
            deleteBtn.setDisable(true);
        }
        caculateCost();
    }

    public void initComboBoxData(){
        List<String> hallNameList = new ArrayList<>();
        List<String> facilityNameList = new ArrayList<>();
        List<String> userIdList = new ArrayList<>();

        try {
            listFacility = FXCollections.observableList(new FacilityService().getAllFacilities());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (HallModel hall: listHall){
            hallNameList.add(hall.getHallName());
        }

        for (UserBean user: listUser){
            userIdList.add(user.getAccountModel().getUserId() + "");
        }

        outer: for (FacilityModel f: listFacility){
            if(f.getAvailable() > 0){
                inner: for(HireBean hire: hireBeanObservableList){
                    if(f.getFacilityId() == hire.getFacility().getFacilityId()){
                        continue outer;
                    }
                }
                facilityNameList.add(f.getFacilityName());
            }
        }
        obUserId = FXCollections.observableList(userIdList);
        obHall = FXCollections.observableList(hallNameList);
        obFacility = FXCollections.observableList(facilityNameList);
        selectUserId.setItems(obUserId);
        editHall.setItems(obHall);
        editFacility.setItems(obFacility);
        editFacility.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                addBtn.setDisable(false);
                FacilityModel facility = new FacilityModel();
                for (FacilityModel f: listFacility){
                    if(f.getFacilityName().equals(newValue)){
                        facility = f;
                        break;
                    }
                }
                if(facility.getFacilityId() != 0){
                    setSpinnerValue(facility.getAvailable());
                }
            }
        });
    }

    public void showContract(ActionEvent e){
        stageInsert = (Stage) ((Node) e.getSource()).getScene().getWindow();
        ContractBean contract = new ContractBean();
        LocalDate fromDate = editFromDate.getValue();
        LocalDate toDate = editToDate.getValue();
        String phoneNum = editPhone.getText();
        String eventName = editEventName.getText();
        String content = editContent.getText();

        if(!acceptCheckBox.isSelected() || phoneNum.equals("") || editHall.getValue() == null || fromDate == null || toDate == null || eventName.equals("") || content.equals("")){
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
                UserAccountModel userAccount = user.getAccountModel();
                userAccount.setPhoneNumber(phoneNum);
                contract.setUserAccountModel(userAccount);
                contract.setNhanKhauBean(user.getNhanKhauBean());

                EventBean eventBean = new EventBean();
                eventBean.setHall(hallModel);

                EventModel event = new EventModel();
                event.setEventName(eventName);
                event.setContent(content);
                event.setHallId(hallModel.getHallId());
                event.setFromDate(from);
                event.setToDate(to);
                eventBean.setEvent(event);

                contract.setEventBean(eventBean);

                contract.setFacilityModelList(hireBeanObservableList);

                ContractModel contractModel = new ContractModel();
                contractModel.setCreateDate(new Date());
                contractModel.setCost(caculateCost());
                contractModel.setUserId(userAccount.getUserId());

                contract.setContractModel(contractModel);

                FXMLLoader loader = new FXMLLoader();
                stage = new Stage();
                loader.setLocation(getClass().getResource("/communityhouseview/ContractForm.fxml"));
                Parent parent = null;
                try {
                    parent = loader.load();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                ContractFormController tmp = loader.getController();
                tmp.setAdminInsert(true);
                tmp.initData(contract);
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();
                stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Phòng không còn trống vào ngày bạn chọn!", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");
        resetButton();
    }

    public void setSpinnerValue(int maxValue){
        final int initialValue = 1;

        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxValue, initialValue);

        editQuantity.setValueFactory(valueFactory);
        editQuantity.setEditable(true);
    }

    public void initTableHire(){
        indexCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(hireBeanObservableList.indexOf(cellData.getValue())+1)));
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFacility().getFacilityName()));
        quantityCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getHiredQuantity())));
        hireFacilities.setItems(hireBeanObservableList);
        deleteBtn.setDisable(true);
        hireFacilities.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deleteBtn.setDisable(false);
            }
        });
        if(editFacility.getSelectionModel().getSelectedItem() == null){
            addBtn.setDisable(true);
        }
    }

    public void resetButton(){
        acceptCheckBox.setSelected(false);
        editHall.getSelectionModel().clearSelection();
        editFromDate.getEditor().clear();
        editToDate.getEditor().clear();
        editEventName.clear();
        editContent.clear();
        totalFee.clear();
        hireBeanObservableList.clear();
        editAddress.clear();
        editName.clear();
        editPhone.clear();
    }

    public void RegisterBtn(ActionEvent e){
        userController.btnRegister(e);
    }

    public void MyRegisterBtn(ActionEvent e){
        userController.btnMyRegister(e);
    }

    public void MyAccountBtn(ActionEvent e){
        userController.btnMyAccount(e);
    }

}
