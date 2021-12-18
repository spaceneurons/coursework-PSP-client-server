package utils;

import models.entities.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
    private static final ClientSocket SINGLE_INSTANCE = new ClientSocket();

    private ClientSocket() {
        try {
            socket = new Socket("localhost", 5556);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
        }
    }

    private int ClientId = -1;
    private int UserId = -1;
    private int EmployeeId = -1;
    private int ServiceId = -1;

    public static ClientSocket getInstance() {
        return SINGLE_INSTANCE;
    }

    private User user;
    private static Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Socket getSocket() {
        return socket;
    }


    public BufferedReader getInStream() {
        return in;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getClientId() {
        return ClientId;
    }

    public void setClientId(int clientId) {
        ClientId = clientId;
    }
    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public void setEmployeeId(int id) {
        EmployeeId = id;
    }


    public int getEmployeeId() {
        return EmployeeId;
    }

    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int serviceId) {
        ServiceId = serviceId;
    }
}

