package adminFXML;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.Requests;
import enums.Roles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.EmployeeModel;
import models.ServiceModel;
import models.TCP.Request;
import models.entities.Client;
import models.entities.Employee;
import models.entities.Service;
import utils.ClientSocket;
import utils.GetService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageServices implements Initializable {
    public TableView<ServiceModel> serviceTable;
    public TableColumn<ServiceModel, String> id;
    public TableColumn<ServiceModel, String> name;
    public TableColumn<ServiceModel, String> time;
    public TableColumn<ServiceModel, String> price;
    public Button btnAdd;
    public Button btnEdit;
    public Button btnDelete;
    public Button btnBack;
    public Button btnLogOut;

    public void onLogOut() throws IOException {
        Stage stage = (Stage) btnLogOut.getScene().getWindow();
        ClientSocket.getInstance().setUser(null);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void OnMouseClicked() {
        if (serviceTable.getSelectionModel().getSelectedItem() != null) {
            btnDelete.setDisable(false);
            btnEdit.setDisable(false);
        } else {
            btnDelete.setDisable(true);
            btnEdit.setDisable(true);
        }
    }

    public void OnBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("adminAccount.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void OnAdd() throws IOException {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddUpdateService.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void OnEdit() throws IOException {
        ServiceModel serviceModel = serviceTable.getSelectionModel().getSelectedItem();
        ClientSocket.getInstance().setServiceId(serviceModel.getId());
        Stage stage = (Stage) btnEdit.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddUpdateService.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void OnDelete() throws IOException {
        Request requestModel = new Request();
        ServiceModel serviceModel = serviceTable.getSelectionModel().getSelectedItem();
        requestModel.setRequestMessage(new Gson().toJson(new Service(serviceModel.getId())));
        requestModel.setRequestType(Requests.DELETE_SERVICE);
        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestModel));
        ClientSocket.getInstance().getOut().flush();
        serviceTable.getItems().remove(serviceTable);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
        ClientSocket.getInstance().getInStream().readLine();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(ClientSocket.getInstance().getUser().getRole().equals(Roles.ADMIN.toString())){
            btnDelete.setVisible(true); // отображение buttons;
            btnAdd.setVisible(true);
            btnEdit.setVisible(true);
        }
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        GetService<Service> batchGetService = new GetService<>(Service.class);
        Type listType = new TypeToken<ArrayList<Service>>() {
        }.getType();
        List<Service> services = new Gson().fromJson(batchGetService.GetEntities(Requests.GET_ALL_SERVICES), listType);
        ObservableList<ServiceModel> list = FXCollections.observableArrayList();
        if (services.size() != 0)
            for (Service service : services) {
                ServiceModel tableService = new ServiceModel(service.getId(), service.getName(),
                        service.getTime(), service.getPrice());
                list.add(tableService);
            }

        serviceTable.setItems(list);
    }
}
