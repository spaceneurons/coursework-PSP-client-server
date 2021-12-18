package adminFXML;

import com.google.gson.Gson;
import enums.Requests;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.TCP.Request;
import models.TCP.Response;
import models.entities.Service;
import utils.ClientSocket;
import utils.GetService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddUpdateService implements Initializable {
    public TextField nameField;
    public TextField priceField;
    public TextField timeField;
    public Button btnBack;
    public Button btnSave;
    public Button btnLogOut;
    private int serviceId;
    Service service;

    public void onLogOut() throws IOException {
        Stage stage = (Stage) btnLogOut.getScene().getWindow();
        ClientSocket.getInstance().setUser(null);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ManageServices.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void onSave() {
        try {
            Integer.parseInt(timeField.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Введите корректное значение");
            alert.showAndWait();
            return;
        } try {
            Float.parseFloat(priceField.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Введите корректную стоимость");
            alert.showAndWait();
            return;
        }
        try {
            if (service == null) {
                service = new Service();
            }
            service.setName(nameField.getText());
            service.setTime(Integer.parseInt(timeField.getText()));
            service.setPrice(Float.parseFloat(priceField.getText()));
            Request request;
            if (ClientSocket.getInstance().getServiceId() != -1) {
                service.setId(serviceId);
                request = new Request(Requests.UPDATE_SERVICE, new Gson().toJson(service));
            } else
                request = new Request(Requests.ADD_SERVICE, new Gson().toJson(service));
            ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
            ClientSocket.getInstance().getOut().flush();
            Response response = new Gson().fromJson(ClientSocket.getInstance().getInStream().readLine(), Response.class);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(response.getResponseMessage());
            alert.showAndWait();
            ClientSocket.getInstance().setEmployeeId(-1);
            Thread.sleep(1500);
            Stage stage = (Stage) btnSave.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("ManageServices.fxml"));
            Scene newScene = new Scene(root);
            stage.setScene(newScene);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if (ClientSocket.getInstance().getServiceId() != -1) {
                GetService<Service> flightGetService = new GetService<>(Service.class);
                service = flightGetService.GetEntity(Requests.GET_SERVICE, new Service(ClientSocket.getInstance().getServiceId()));
                serviceId = service.getId();
                nameField.setText(service.getName());
                timeField.setText(String.valueOf(service.getTime()));
                priceField.setText(String.valueOf(service.getPrice()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
