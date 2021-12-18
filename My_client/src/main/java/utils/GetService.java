package utils;

import com.google.gson.Gson;
import enums.Requests;
import models.TCP.Request;
import models.TCP.Response;

import java.io.IOException;

public class GetService<T> {
    final Class<T> ClassType;

    public GetService(Class<T> classType) {
        ClassType = classType;
    }

    public String GetEntities(Requests requestType) {
        Request request = new Request();
        request.setRequestMessage("");
        request.setRequestType(requestType);
        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();
        String answer = null;
        try {
            answer = ClientSocket.getInstance().getInStream().readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response response = new Gson().fromJson(answer, Response.class);
        return response.getResponseData();
    }

    public T GetEntity(Requests requestType, T entity) throws IOException {
        Request request = new Request();

        request.setRequestMessage(new Gson().toJson(entity));
        request.setRequestType(requestType);
        ClientSocket.getInstance().getOut().println(new Gson().toJson(request));
        ClientSocket.getInstance().getOut().flush();
        String answer;
        answer = ClientSocket.getInstance().getInStream().readLine();
        Response response = new Gson().fromJson(answer, Response.class);
        return entity = new Gson().fromJson(response.getResponseData(), ClassType);
    }
}

