package communityhousecontroller;

import communityhousebean.ContractBean;
import communityhousebean.HireBean;
import communityhousemodel.EventModel;
import communityhousemodel.FacilityModel;
import communityhousemodel.HallModel;
import communityhouseservice.EventService;
import communityhouseservice.FacilityService;
import communityhouseservice.HallService;
import communityhouseservice.beanservice.ContractBeanService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UserContractDetailController implements Initializable {
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


    private ContractBean contract;
    private ObservableList<HireBean> hireBeanObservableList;

    ContractBeanService contractBeanService = new ContractBeanService();
    EventService eventService = new EventService();
    ObservableList<HallModel> listHall = FXCollections.observableList(new HallService().getAllHall());
    ObservableList<FacilityModel> listFacility;

    ObservableList<String> obFacility;
    ObservableList<String> obHall;

    public void initData(ContractBean contractBean){
        List<HireBean> hireBeanList;

        contract = contractBean;
        editAddress.setText(contract.getNhanKhauBean().getNhanKhauModel().getDiaChiHienNay());
        editName.setText(contract.getNhanKhauBean().getNhanKhauModel().getHoTen());
        editPhone.setText(contract.getUserAccountModel().getPhoneNumber());
        LocalDate fromDate = LocalDate.parse(contract.getEventBean().getEvent().getFromDate().toString());
        LocalDate toDate = LocalDate.parse(contract.getEventBean().getEvent().getToDate().toString());
        editFromDate.setValue(fromDate);
        editEventName.setText(contract.getEventBean().getEvent().getEventName());
        editContent.setText(contract.getEventBean().getEvent().getContent());
        editToDate.setValue(toDate);
        editHall.setValue(contract.getEventBean().getHall().getHallName());
        hireBeanList = new ArrayList<>(contract.getFacilityModelList());
        hireBeanObservableList = FXCollections.observableList(hireBeanList);
        acceptCheckBox.setSelected(true);
        initComboBoxData();
        initTableHire();
    }

    public void initComboBoxData(){
        List<String> hallNameList = new ArrayList<>();
        List<String> facilityNameList = new ArrayList<>();

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
        obHall = FXCollections.observableList(hallNameList);
        obFacility = FXCollections.observableList(facilityNameList);
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    }

    public void resetBtn(){
        initData(contract);
    }

    public void saveChange(){
        HallModel hallModel = listHall.get(editHall.getSelectionModel().getSelectedIndex());
        LocalDate fromDate = editFromDate.getValue();
        LocalDate toDate = editToDate.getValue();
        String phoneNum = editPhone.getText();
        String eventName = editEventName.getText();
        String content = editContent.getText();
        Date from = java.sql.Date.valueOf(fromDate);
        Date to = java.sql.Date.valueOf(toDate);
        int eventId = contract.getEventBean().getEvent().getEventId();
        if(!acceptCheckBox.isSelected() || phoneNum == null || hallModel == null || fromDate == null || toDate == null || eventName == null || content == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn chưa điền đầy đủ thông tin", ButtonType.OK);
            alert.showAndWait();
        } else if(checkHallFree(eventId, hallModel, from) && checkHallFree(eventId, hallModel, to)){
            contract.getEventBean().getEvent().setEventName(eventName);
            contract.getEventBean().getEvent().setContent(content);
            contract.getUserAccountModel().setPhoneNumber(phoneNum);
            contract.getEventBean().setHall(hallModel);
            contract.getEventBean().getEvent().setHallId(hallModel.getHallId());
            contract.getEventBean().getEvent().setFromDate(java.sql.Date.valueOf(fromDate));
            contract.getEventBean().getEvent().setToDate(java.sql.Date.valueOf(toDate));
            contract.setFacilityModelList(hireBeanObservableList);
            contract.getContractModel().setCost(caculateCost(contract));
            contract.getContractModel().setCreateDate(new Date());
            contractBeanService.updateContract(contract);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update Successful!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    public long caculateCost(ContractBean contractBean){
        List<HireBean> list = contractBean.getFacilityModelList();
        long price = 0;
        for(HireBean hire: list){
            price += hire.getFacility().getPrice() * hire.getHiredQuantity();
        }
        price += contractBean.getEventBean().getHall().getPrice();
        return price;
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
}
