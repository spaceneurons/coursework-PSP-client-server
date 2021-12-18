package adminFXML;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.Requests;
import enums.Roles;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.TCP.Request;
import models.UserModel;
import models.entities.User;
import utils.ClientSocket;
import utils.GetService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Users implements Initializable {
    public TableView<UserModel> usersTable;
    public TableColumn<UserModel, String> id;
    public TableColumn<UserModel, String> login;
    public TableColumn<UserModel, String> password;
    public TableColumn<UserModel, String> clientsIds;
    public Button btnAdd;
    public Button btnEdit;
    public Button btnDelete;
    public Button btnBack;
    public Button btnLogOut;


    public void OnMouseClicked() {
        if (usersTable.getSelectionModel().getSelectedItem() != null) {
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
        Parent root = FXMLLoader.load(getClass().getResource("AddUpdateUser.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void OnEdit() throws IOException {
        UserModel userModel = usersTable.getSelectionModel().getSelectedItem();
        ClientSocket.getInstance().setUserId(userModel.getId());
        Stage stage = (Stage) btnEdit.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddUpdateUser.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void OnDelete() throws IOException {
        Request requestModel = new Request();
        UserModel userModel = usersTable.getSelectionModel().getSelectedItem();
        requestModel.setRequestMessage(new Gson().toJson(new User(userModel.getId())));
        requestModel.setRequestType(Requests.DELETE_USER);
        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestModel));
        ClientSocket.getInstance().getOut().flush();
        usersTable.getItems().remove(userModel);
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
        if(ClientSocket.getInstance().getUser().getRole().equals(Roles.ADMIN.toString())){
            btnDelete.setVisible(true); // отображение buttons;
            btnEdit.setVisible(true);
        }
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        clientsIds.setCellValueFactory(new PropertyValueFactory<>("ids"));
        GetService<User> batchGetService = new GetService<>(User.class);
        Type listType = new TypeToken<ArrayList<User>>() {
        }.getType();
        List<User> users = new Gson().fromJson(batchGetService.GetEntities(Requests.GET_ALL_USERS), listType);
        ObservableList<UserModel> list = FXCollections.observableArrayList();
        if (users.size() != 0)
            for (User user : users) {
                UserModel tableUser = new UserModel(user.getId(), user.getLogin(), user.getPassword(), user.getClientsIds(user));
                list.add(tableUser);
            }

        usersTable.setItems(list);
    }
}
