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
import models.EmployeeModel;
import models.ServiceModel;
import models.TCP.Request;
import models.TCP.Response;
import models.entities.Employee;
import models.entities.*;
import utils.ClientSocket;
import utils.GetService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {
    private final String blackbutton = "-fx-text-fill: black;\n" +
            "    -fx-font-family: \"Arial Narrow\";\n" +
            "    -fx-font-weight: bold;\n" +
            "   -fx-border-color:black;\n" +
            "   -fx-background-color:null;";
    private final String blackfont = "-fx-text-fill:black;";

    public ComboBox timeChoice;
    public DatePicker dateChoice;
    public AnchorPane anchorPane;
    ObservableList<String> tm = FXCollections.observableArrayList("09:00:00", "10:00:00", "11:00:00",
                                                                       "12:00:00", "13:00:00", "14:00:00",
                                                                       "15:00:00", "16:00:00", "17:00:00",
                                                                       "18:00:00");

    public TableView<EmployeeModel> employeeTable;
    public TableColumn<EmployeeModel, String> emplName;
    public TableColumn<EmployeeModel, String> surname;

    public TableView<ServiceModel> serviceTable;
    public TableColumn<ServiceModel, String> name;
    public TableColumn<ServiceModel, String> time;
    public TableColumn<ServiceModel, String> price;

    public TableView<ClientModel> clientsTable;
    public TableColumn<ClientModel, String> id;
    public TableColumn<ClientModel, String> FIO;

    public Button btnLogOut;
    public Button btnBack;
    public Button btnSave;

    public Label text1;
    public Label text2;
    public Label text3;
    public Label text4;
    public Label text5;

    Client client = new Client();
    models.entities.Employee employee = new Employee();
    Service service = new Service();

    public void OnMouseClickedEmployee() {
        EmployeeModel employeeModel = employeeTable.getSelectionModel().getSelectedItem();
        employee.setId(employeeModel.getId());
    }

    public void OnMouseClickedServices() throws IOException {
        int id = serviceTable.getSelectionModel().getSelectedItem().getId();
        service.setId(id);
        GetService<EmployeeService> batchGetService = new GetService<>(EmployeeService.class);
        Type listType = new TypeToken<ArrayList<EmployeeService>>() {
        }.getType();
        int servId = serviceTable.getSelectionModel().getSelectedItem().getId();
        List<EmployeeService> employees = new Gson().fromJson(batchGetService.GetEntities(Requests.GET_ALL_EMPLSERV), listType);
        ObservableList<EmployeeModel> list = FXCollections.observableArrayList();
        if (employees.size() != 0)
            for (EmployeeService employeeService : employees) {
                if(employeeService.getService().getId() == servId) {
                    EmployeeModel tableEmployee = new EmployeeModel(employeeService.getEmployee().getId(), employeeService.getEmployee().getName(), employeeService.getEmployee().getSurname(), employeeService.getEmployee().getServices().toString());
                    list.add(tableEmployee);
                }
            }

        employeeTable.setItems(list);
    }

    public void onLogOut() throws IOException {
        Stage stage = (Stage) btnLogOut.getScene().getWindow();
        ClientSocket.getInstance().setUser(null);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }


    public void onSave() throws ParseException, IOException, InterruptedException {
        GetService<EmployeeService> batchGetService = new GetService<>(EmployeeService.class);
        Type listType = new TypeToken<ArrayList<EmployeeService>>() {
        }.getType();
        try{
        int servId = serviceTable.getSelectionModel().getSelectedItem().getId();
        List<EmployeeService> employees = new Gson().fromJson(batchGetService.GetEntities(Requests.GET_ALL_EMPLSERV), listType);

        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Time timeValue = new Time(formatter.parse(timeChoice.getSelectionModel().getSelectedItem().toString()).getTime());
        LocalTime localEndTime = timeValue.toLocalTime().plusHours(serviceTable.getSelectionModel().getSelectedItem().getTime());
        Time endTime = new Time(localEndTime.getHour(), localEndTime.getMinute(), localEndTime.getSecond());


        EmployeeService employeeService = new EmployeeService();
        for(EmployeeService employeeService1 : employees){
            if(employeeService1.getEmployee().getId() == employee.getId() && employeeService1.getService().getId() == service.getId()){
                employeeService.setId(employeeService1.getId());
            }
        }



        Appointment appointment = new Appointment();
        appointment.setDate(java.sql.Date.valueOf(dateChoice.getValue().toString()));
        appointment.setStarTime(timeValue);
        appointment.setEndTime(endTime);
        appointment.setClient(client);
        appointment.setService(employeeService);

        Request request = new Request(Requests.ADD_APPOINTMENT, new Gson().toJson(appointment));
        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();
        Response response = new Gson().fromJson(ClientSocket.getInstance().getInStream().readLine(), Response.class);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(response.getResponseMessage());
        alert.showAndWait();
        Thread.sleep(1500);
        Stage stage = (Stage) btnSave.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("userFXML/account.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Заполните все поля.");
            alert.showAndWait();
        }
        finally {
            return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        GetService<Client> batchGetService2 = new GetService<>(Client.class);
        Type listType2 = new TypeToken<ArrayList<Client>>() {
        }.getType();
        List<Client> clients = new Gson().fromJson(batchGetService2.GetEntities(Requests.GET_ALL_CLIENTS), listType2);
        ObservableList<ClientModel> list2 = FXCollections.observableArrayList();
        if (clients.size() != 0) {
            for (Client client : clients) {
                if (client.getUser().getId() == ClientSocket.getInstance().getUser().getId()) {
                    ClientModel tableUser = new ClientModel(client.getId(), client.getName() + " " + client.getSurname(),
                            client.getEmail(), client.getTelephone());
                    list2.add(tableUser);
                }
            }
            if(list2.size() == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Необходимо создать клиента.");
                alert.showAndWait();
                Stage stage = (Stage) btnSave.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("userFXML/account.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene newScene = new Scene(root);
                stage.setScene(newScene);
            }
            else {
                if (Account.theme == 1){
                    anchorPane.setStyle("-fx-background-image: url(images/lighttipa.jpg)");
                    btnLogOut.setStyle(blackbutton);
                    btnBack.setStyle(blackbutton);
                    btnSave.setStyle(blackbutton);
                    text1.setStyle(blackfont);
                    text2.setStyle(blackfont);
                    text3.setStyle(blackfont);
                    text4.setStyle(blackfont);
                    text5.setStyle(blackfont);
                }
                timeChoice.setItems(tm);
                time.setCellValueFactory(new PropertyValueFactory<>("time"));
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

                FIO.setCellValueFactory(new PropertyValueFactory<>("FIO"));
                id.setCellValueFactory(new PropertyValueFactory<>("id"));

                clientsTable.setItems(list2);

                emplName.setCellValueFactory(new PropertyValueFactory<>("name"));
                surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
            }
        }

    }

    public void OnMouseClickedClients() {
        ClientModel clientModel = clientsTable.getSelectionModel().getSelectedItem();
        client.setId(clientModel.getId());
    }


    public void onBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("account.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
