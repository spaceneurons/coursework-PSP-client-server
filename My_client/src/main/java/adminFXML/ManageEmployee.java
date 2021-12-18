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
import models.AppointmentModel;
import models.ClientModel;
import models.EmployeeModel;
import models.TCP.Request;
import models.entities.Client;
import models.entities.Employee;
import utils.ClientSocket;
import utils.GetService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageEmployee implements Initializable {
    public TableView<EmployeeModel> employeeTable;
    public TableColumn<EmployeeModel, String> id;
    public TableColumn<EmployeeModel, String> name;
    public TableColumn<EmployeeModel, String> surname;
    public TableColumn<EmployeeModel, String> services;

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
        if (employeeTable.getSelectionModel().getSelectedItem() != null) {
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
        Parent root = FXMLLoader.load(getClass().getResource("AddUpdateEmployee.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void OnEdit() throws IOException {
        EmployeeModel employeeModel = employeeTable.getSelectionModel().getSelectedItem();
        ClientSocket.getInstance().setEmployeeId(employeeModel.getId());
        Stage stage = (Stage) btnEdit.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddUpdateEmployee.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void OnDelete() throws IOException {
        Request requestModel = new Request();
        EmployeeModel employeeModel = employeeTable.getSelectionModel().getSelectedItem();
        requestModel.setRequestMessage(new Gson().toJson(new Employee(employeeModel.getId())));
        requestModel.setRequestType(Requests.DELETE_EMPLOYEE);
        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestModel));
        ClientSocket.getInstance().getOut().flush();
        employeeTable.getItems().remove(employeeModel);
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
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        services.setCellValueFactory(new PropertyValueFactory<>("services"));
        GetService<Employee> batchGetService = new GetService<>(Employee.class);
        Type listType = new TypeToken<ArrayList<Employee>>() {
        }.getType();
        List<Employee> employees = new Gson().fromJson(batchGetService.GetEntities(Requests.GET_ALL_EMPLOYEES), listType);
        ObservableList<EmployeeModel> list = FXCollections.observableArrayList();
        if (employees.size() != 0)
            for (Employee employee : employees) {
                EmployeeModel tableEmployee = new EmployeeModel(employee.getId(), employee.getName(),
                        employee.getSurname(), employee.getServices().toString());
                list.add(tableEmployee);
            }

        employeeTable.setItems(list);
    }
}
