import com.google.gson.Gson;
import enums.Requests;
import enums.Responses;
import enums.Roles;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.TCP.Request;
import models.TCP.Response;
import models.entities.User;
import utils.ClientSocket;

import java.io.IOException;

public class SignUp {
    public PasswordField passwordfieldPassword;
    public PasswordField passwordfieldConfirmPassword;
    public Button buttonSignUp;
    public Button buttonBack;
    public TextField textfieldLogin;
    public Label labelMessage;

    public void Signup_Pressed() throws IOException {
        labelMessage.setVisible(false);
        User user = new User();
        user.setLogin(textfieldLogin.getText());
        user.setPassword(passwordfieldPassword.getText());
        user.setRole(Roles.USER);
        if (!passwordfieldPassword.getText().equals(passwordfieldConfirmPassword.getText())) {
            labelMessage.setText("Пароли не совпадают");
            labelMessage.setVisible(true);
            return;
        }
        if (textfieldLogin.equals("") || passwordfieldPassword.equals("") || passwordfieldConfirmPassword.equals("")) {
            labelMessage.setText("Не все поля заполнены.");
            labelMessage.setVisible(true);
            return;
        }
        Request request = new Request();
        request.setRequestMessage(new Gson().toJson(user));
        request.setRequestType(Requests.SIGNUP);
        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();
        String answer = ClientSocket.getInstance().getInStream().readLine();
        Response response = new Gson().fromJson(answer, Response.class);
        if (response.getResponseStatus() == Responses.OK) {
            labelMessage.setVisible(false);
            ClientSocket.getInstance().setUser(new Gson().fromJson(response.getResponseData(), User.class));
            Stage stage = (Stage) buttonBack.getScene().getWindow();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("userFXML/account.fxml"));
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
        } else {
            labelMessage.setText("Пользователь с таким логином уже существует.");
            labelMessage.setVisible(true);
        }
    }

    public void Back_Pressed() throws IOException {
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
    }
}
