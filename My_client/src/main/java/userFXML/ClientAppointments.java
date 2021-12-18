package userFXML;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.Requests;
import enums.Roles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.AppointmentModel;
import models.TCP.Request;
import models.entities.Appointment;
import utils.ClientSocket;
import utils.GetService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientAppointments implements Initializable {
    private final String blackbutton = "-fx-text-fill: black;\n" +
            "    -fx-font-family: \"Arial Narrow\";\n" +
            "    -fx-font-weight: bold;\n" +
            "   -fx-border-color:black;\n" +
            "   -fx-background-color:null;";


    public TableView<AppointmentModel> appointmentTable;
    public TableColumn<AppointmentModel, String> id;
    public TableColumn<AppointmentModel, String> start;
    public TableColumn<AppointmentModel, String> end;
    public TableColumn<AppointmentModel, String> date;
    public TableColumn<AppointmentModel, String> service;
    public TableColumn<AppointmentModel, String> client;
    public Button btnAdd;
    public Button btnEdit;
    public Button btnDelete;
    public Button btnBack;
    public Button btnLogOut;
    public AnchorPane anchorPane;

    public void OnMouseClicked() {
        if (appointmentTable.getSelectionModel().getSelectedItem() != null) {
            btnDelete.setDisable(false);
            btnEdit.setDisable(false);
        } else {
            btnDelete.setDisable(true);
            btnEdit.setDisable(true);
        }
    }

    public void OnBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("account.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void OnAdd() throws IOException {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("userFXML/AddAppointment.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void OnEdit() throws IOException {
        try {
            AppointmentModel appointmentModel = appointmentTable.getSelectionModel().getSelectedItem();
            ClientSocket.getInstance().setUserId(appointmentModel.getId());
            Stage stage = (Stage) btnEdit.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("EditAppointment.fxml"));
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Поле не выбрано");
            alert.showAndWait();
        }
    }

    public void OnDelete() throws IOException {
            Request requestModel = new Request();
            AppointmentModel appointmentModel = appointmentTable.getSelectionModel().getSelectedItem();
            requestModel.setRequestMessage(new Gson().toJson(new Appointment(appointmentModel.getId())));
            requestModel.setRequestType(Requests.DELETE_APPOINTMENT);
            ClientSocket.getInstance().getOut().println(new Gson().toJson(requestModel));
            ClientSocket.getInstance().getOut().flush();
            appointmentTable.getItems().remove(appointmentModel);
            btnEdit.setDisable(true);
            btnDelete.setDisable(true);
            ClientSocket.getInstance().getInStream().readLine();
    }
    public void onLogOut() throws IOException {
        Stage stage = (Stage) btnLogOut.getScene().getWindow();
        ClientSocket.getInstance().setUser(null);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Account.theme == 1){
            anchorPane.setStyle("-fx-background-image: url(images/newbackg.jpg)");
            btnAdd.setStyle(blackbutton);
            btnEdit.setStyle(blackbutton);
            btnDelete.setStyle(blackbutton);
            btnBack.setStyle(blackbutton);
            btnLogOut.setStyle(blackbutton);
        }

        if(ClientSocket.getInstance().getUser().getRole().equals(Roles.USER.toString())){
            btnDelete.setVisible(true); // отображение buttons;
            btnAdd.setVisible(true);
            btnEdit.setVisible(true);
        }
        end.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        start.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        service.setCellValueFactory(new PropertyValueFactory<>("service"));
        client.setCellValueFactory(new PropertyValueFactory<>("client"));
        GetService<Appointment> batchGetService = new GetService<>(Appointment.class);
        Type listType = new TypeToken<ArrayList<Appointment>>() {
        }.getType();
        List<Appointment> appointments = new Gson().fromJson(batchGetService.GetEntities(Requests.GET_ALL_APPOINTMENTS), listType);
        ObservableList<AppointmentModel> list = FXCollections.observableArrayList();
        if (appointments.size() != 0)
            for (Appointment appointment : appointments) {
                if(appointment.getClient().getUser().getId() == ClientSocket.getInstance().getUser().getId()) {
                    AppointmentModel tableAppointment = new AppointmentModel(appointment.getId(), appointment.getEndTime().toString(), appointment.getStarTime().toString(), appointment.getDate().toString(), appointment.getService().getService().getName(), appointment.getClient().getName());
                    list.add(tableAppointment);
                }
            }
        appointmentTable.setItems(list);
    }
}
