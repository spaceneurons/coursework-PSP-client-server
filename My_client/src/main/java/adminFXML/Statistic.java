package adminFXML;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.Requests;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.entities.Appointment;
import utils.GetService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Statistic implements Initializable {
    public javafx.scene.chart.BarChart BarChart;
    public CategoryAxis axisX;
    public NumberAxis axisY;
    public Button btnBack;

    public void onBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("ManageAppointments.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GetService<Appointment> batchGetService = new GetService<>(Appointment.class);
        Type listType = new TypeToken<ArrayList<Appointment>>() {
        }.getType();
        List<Appointment> appointments = new Gson().fromJson(batchGetService.GetEntities(Requests.GET_ALL_APPOINTMENTS), listType);
        int January = 0, February = 0, March = 0, April = 0, May = 0, June = 0, July = 0, August = 0, September = 0, October = 0, November = 0, December = 0;
        for(Appointment appointment : appointments)
        {
            if(appointment.getDate().getYear() == 120){
                switch (appointment.getDate().getMonth()){
                    case 0: January++; break;
                    case 1: February++; break;
                    case 2: March++; break;
                    case 3: April++; break;
                    case 4: May++; break;
                    case 5: June++; break;
                    case 6: July++; break;
                    case 7: August++; break;
                    case 8: September++; break;
                    case 9: October++; break;
                    case 10: November++; break;
                    case 11: December++; break;
                }
            }
        }
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
        dataSeries1.setName("2020");
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Январь", January));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Февраль", February));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Март", March));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Апрель", April));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Май", May));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Июнь", June));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Июль", July));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Август", August));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Сентябрь", September));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Октябрь", October));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Ноябрь", November));
        dataSeries1.getData().add(new XYChart.Data<String, Number>("Декабрь", December));
        BarChart.getData().add(dataSeries1);
    }
}
