package userFXML;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.Requests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.ClientModel;
import models.TCP.Request;
import models.entities.Client;
import models.entities.User;
import utils.ClientSocket;
import utils.GetService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageAccount implements Initializable {
    private final String blackbutton = "-fx-text-fill: black;\n" +
            "    -fx-font-family: \"Arial Narrow\";\n" +
            "    -fx-font-weight: bold;\n" +
            "   -fx-border-color:black;\n" +
            "   -fx-background-color:null;";
    private final String blackfont = "-fx-text-fill:black;";
    public TableView<ClientModel> clientsTable;
    public TableColumn<ClientModel, String> FIO;
    public TableColumn<ClientModel, String> email;
    public TableColumn<ClientModel, String> phone;
    public Button btnAdd;
    public Button btnEdit;
    public Button btnDelete;
    public Button btnBack;
    public Button btnExit;
    public Button btnEditUser;
    public Button btnDeleteUser;
    public AnchorPane anchorPane;

    public Label text;

    public void OnMouseClicked() {
        if (clientsTable.getSelectionModel().getSelectedItem() != null) {
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
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("adminFXML/AddUpdateClient.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void OnEdit() throws IOException {
        ClientModel clientModel = clientsTable.getSelectionModel().getSelectedItem();
        if(clientModel == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Поле не выбрано.");
            alert.showAndWait();
            return;
        }
        else{
            ClientSocket.getInstance().setClientId(clientModel.getId());
            Stage stage = (Stage) btnEdit.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("adminFXML/AddUpdateClient.fxml"));
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
        }
    }

    public void OnDelete() throws IOException {
        Request requestModel = new Request();
        ClientModel clientModel = clientsTable.getSelectionModel().getSelectedItem();
        if(clientModel == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Поле не выбрано.");
            alert.showAndWait();
            return;
        }else{
            requestModel.setRequestMessage(new Gson().toJson(new Client(clientModel.getId())));
            requestModel.setRequestType(Requests.DELETE_CLIENT);
            ClientSocket.getInstance().getOut().println(new Gson().toJson(requestModel));
            ClientSocket.getInstance().getOut().flush();
            clientsTable.getItems().remove(clientModel);
            btnEdit.setDisable(true);
            btnDelete.setDisable(true);
            ClientSocket.getInstance().getInStream().readLine();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Account.theme == 1){
            anchorPane.setStyle("-fx-background-image: url(images/newbackg.jpg)");
            btnAdd.setStyle(blackbutton);
            btnEdit.setStyle(blackbutton);
            btnDelete.setStyle(blackbutton);
            btnBack.setStyle(blackbutton);
            btnExit.setStyle(blackbutton);
            btnEditUser.setStyle(blackbutton);
            btnDeleteUser.setStyle(blackbutton);
            text.setStyle(blackfont);
        }
        btnDelete.setVisible(true); // отображение buttons;
        btnAdd.setVisible(true);
        btnEdit.setVisible(true);
        FIO.setCellValueFactory(new PropertyValueFactory<>("FIO"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        GetService<Client> batchGetService = new GetService<>(Client.class);
        Type listType = new TypeToken<ArrayList<Client>>() {
        }.getType();
        System.out.println(ClientSocket.getInstance().getUser().getId());
        List<Client> clients = new Gson().fromJson(batchGetService.GetEntities(Requests.GET_ALL_CLIENTS), listType);
        ObservableList<ClientModel> list = FXCollections.observableArrayList();
        if (clients.size() != 0)
            for (Client client : clients) {
                if(client.getUser().getId() == ClientSocket.getInstance().getUser().getId()) {
                    ClientModel tableUser = new ClientModel(client.getId(), client.getName() + " " + client.getSurname(),
                            client.getEmail(), client.getTelephone());
                    list.add(tableUser);
                }
            }

        clientsTable.setItems(list);
    }

    public void onExit() throws IOException {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        ClientSocket.getInstance().setUser(null);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    public void deleteUserPressed() throws IOException {
        Request requestModel = new Request();
        requestModel.setRequestMessage(new Gson().toJson(new User(ClientSocket.getInstance().getUser().getId())));
        requestModel.setRequestType(Requests.DELETE_USER);
        ClientSocket.getInstance().getOut().println(new Gson().toJson(requestModel));
        ClientSocket.getInstance().getOut().flush();
        ClientSocket.getInstance().getInStream().readLine();
        ClientSocket.getInstance().setUser(null);
        Stage stage = (Stage) btnDeleteUser.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);

    }

    public void editUserPressed() throws IOException {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("editUser.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
