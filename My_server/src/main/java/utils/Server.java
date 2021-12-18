package utils;

import com.google.gson.Gson;
import enums.Responses;
import models.TCP.Request;
import models.TCP.Response;
import models.entities.*;
import services.*;
import services.EmployeeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Server implements Runnable {
    private Socket clientSocket;
    private Request request;
    private Response response;
    private Gson gson;
    private BufferedReader in;
    private PrintWriter out;
    private UserService userService = new UserService();
    private ClientService clientService = new ClientService();
    private EmployeeService employeeService = new EmployeeService();
    private ServiceService serviceService = new ServiceService();
    private EmployeeServService employeeServService = new EmployeeServService();
    private AppointmentService appointmentService = new AppointmentService();

    public Server(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        response = new Response();
        request = new Request();
        gson = new Gson();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                String message = in.readLine();
                request = gson.fromJson(message, Request.class);
                switch (request.getRequestType()) {
                    case SIGNUP: {
                        User user = gson.fromJson(request.getRequestMessage(), User.class);
                        if (userService.findAllEntities().stream().noneMatch(x -> x.getLogin().equalsIgnoreCase(user.getLogin()))) {
                            userService.saveEntity(user);
                            userService.findAllEntities();
                            User returnUser;
                            returnUser = userService.findEntity(user.getId());
                            response = new Response(Responses.OK, "Регистрация прошла успешно.", gson.toJson(returnUser));
                        } else {
                            response = new Response(Responses.ERROR, "Пользователь с таким логином существует.", "");
                        }
                        break;
                    }
                    case LOGIN: {
                        User requestUser = gson.fromJson(request.getRequestMessage(), User.class);
                        if (userService.findAllEntities().stream().anyMatch(x -> x.getLogin().equalsIgnoreCase(requestUser.getLogin())) && userService.findAllEntities().stream().anyMatch(x -> x.getPassword().equals(requestUser.getPassword()))) {
                            User user = userService.findAllEntities().stream().filter(x -> x.getLogin().equalsIgnoreCase(requestUser.getLogin())).findFirst().get();
                            User responseUser = userService.findEntity(user.getId());
                            if (responseUser.getRole().equalsIgnoreCase("user")) {
                                response = new Response(Responses.OK, "Готово!", gson.toJson(responseUser));
                            }
                            if (responseUser.getRole().equalsIgnoreCase("admin")) {
                                response = new Response(Responses.ADMIN_OK, "Готово!", gson.toJson(responseUser));
                            }
                        } else {
                            response = new Response(Responses.ERROR, "Такого пользователя не существует или неправильный пароль!", "");
                        }
                        break;
                    }
                    case GET_ALL_CLIENTS: {
                        List<Client> clients;
                        clients = clientService.findAllEntities();
                        response = new Response(Responses.OK, "Готово!", gson.toJson(clients));
                        break;
                    }
                    case GET_ALL_EMPLOYEES: {
                        List<Employee> employees;
                        employees = employeeService.findAllEntities();
                        response = new Response(Responses.OK, "Готово!", gson.toJson(employees));
                        break;
                    }
                    case GET_EMPLOYEES:{
                        break;
                    }
                    case GET_ALL_EMPLSERV: {
                        List<models.entities.EmployeeService> employeeServices;
                        employeeServices = employeeServService.findAllEntities();
                        response = new Response(Responses.OK, "Готово!", gson.toJson(employeeServices));
                        break;
                    }
                    case GET_ALL_SERVICES: {
                        List<Service> services;
                        services = serviceService.findAllEntities();
                        response = new Response(Responses.OK, "Готово!", gson.toJson(services));
                        break;
                    }
                    case GET_SERVICE:{
                        Service service = gson.fromJson(request.getRequestMessage(), Service.class);
                        service = serviceService.findEntity(service.getId());
                        response = new Response(Responses.OK, "Готово!", gson.toJson(service));
                        break;
                    }
                    case ADD_SERVICE:{
                        Service service = gson.fromJson(request.getRequestMessage(), Service.class);
                        serviceService.saveEntity(service);
                        response = new Response(Responses.OK, "Готово!", "");
                        break;
                    }
                    case ADD_EMPLSERV:{
                        models.entities.EmployeeService employeeServ = gson.fromJson(request.getRequestMessage(), models.entities.EmployeeService.class);
                        employeeServ.setService(serviceService.findEntity(employeeServ.getService().getId()));
                        employeeServ.setEmployee(employeeService.findEntity(employeeServ.getEmployee().getId()));
                        if(employeeServService.findAllEntities().stream().anyMatch(x -> x.getService().equals(employeeServ.getService())) &&
                           employeeServService.findAllEntities().stream().anyMatch(x -> x.getEmployee().equals(employeeServ.getEmployee())))
                        {
                            response = new Response(Responses.OK, "Данная позтция уже существует.", "");
                        }
                        else {
                            employeeServService.saveEntity(employeeServ);
                            response = new Response(Responses.OK, "Готово!", "");
                        }
                        break;
                    }
                    case ADD_APPOINTMENT:{
                        Appointment appointment = gson.fromJson(request.getRequestMessage(), Appointment.class);
                        Appointment newAppointment = new Appointment();
                        Client client = clientService.findEntity(appointment.getClient().getId());
                        models.entities.EmployeeService employeeService = employeeServService.findEntity(appointment.getService().getId());
                        newAppointment.setDate(appointment.getDate());
                        newAppointment.setStarTime(appointment.getStarTime());
                        newAppointment.setEndTime(appointment.getEndTime());
                        newAppointment.setClient(client);
                        newAppointment.setService(employeeService);
                        appointmentService.saveEntity(newAppointment);
                        response = new Response(Responses.OK, "Готово!", "");
                        break;
                    }
                    case GET_ALL_APPOINTMENTS: {
                        List<Appointment> appointments;
                        appointments = appointmentService.findAllEntities();
                        response = new Response(Responses.OK, "Готово!", gson.toJson(appointments));
                        break;
                    }
                    case GET_ALL_USERS:{
                        List<User> users;
                        users = userService.findAllEntities();
                        response = new Response(Responses.OK, "Готово!", gson.toJson(users));
                        break;
                    }
                    case GET_CLIENT:{
                        Client client = gson.fromJson(request.getRequestMessage(), Client.class);
                        client = clientService.findEntity(client.getId());
                        response = new Response(Responses.OK, "Готово!", gson.toJson(client));
                        break;
                    }
                    case GET_USER:{
                        User user = gson.fromJson(request.getRequestMessage(), User.class);
                        user = userService.findEntity(user.getId());
                        response = new Response(Responses.OK, "Готово!", gson.toJson(user));
                        break;
                    }
                    case GET_EMPLOYEE:{
                        Employee employee = gson.fromJson(request.getRequestMessage(), Employee.class);
                        employee = employeeService.findEntity(employee.getId());
                        response = new Response(Responses.OK, "Готово!", gson.toJson(employee));
                        break;
                    }
                    case ADD_EMPLOYEE:{
                        Employee employee = gson.fromJson(request.getRequestMessage(), Employee.class);
                        employeeService.saveEntity(employee);
                        response = new Response(Responses.OK, "Готово!", "");
                        break;
                    }
                    case DELETE_CLIENT: {
                        Client client = gson.fromJson(request.getRequestMessage(), Client.class);
                        client = clientService.findEntity(client.getId());
                        for(Appointment appointment: client.getAppointments())
                        {
                            appointmentService.deleteEntity(appointment);
                        }
                        userService.findEntity(client.getUser().getId()).getClients().remove(client);
                        client.getAppointments().clear();
                        clientService.deleteEntity(client);
                        response = new Response(Responses.OK, "Готово!", "");
                        break;
                    }
                    case DELETE_EMPLOYEE:{
                        Employee employee = gson.fromJson(request.getRequestMessage(), Employee.class);
                        employee = employeeService.findEntity(employee.getId());
                        for(models.entities.EmployeeService employeeService: employee.getServices())
                        {
                            employeeServService.deleteEntity(employeeService);
                        }
                        employee.getServices().clear();
                        employeeService.deleteEntity(employee);
                        response = new Response(Responses.OK, "готово", "");
                        break;
                    }
                    case DELETE_SERVICE:{
                        Service service = gson.fromJson(request.getRequestMessage(), Service.class);
                        service = serviceService.findEntity(service.getId());
                        for(models.entities.EmployeeService employeeService: service.getEmployees())
                        {
                            employeeServService.deleteEntity(employeeService);
                        }
                        service.getEmployees().clear();
                        serviceService.deleteEntity(service);
                        response = new Response(Responses.OK, "готово", "");
                        break;
                    }
                    case DELETE_EMPL_SERV:{
                        break;
                    }
                    case DELETE_APPOINTMENT: {
                        Appointment appointment = gson.fromJson(request.getRequestMessage(), Appointment.class);
                        appointment = appointmentService.findEntity(appointment.getId());
                        clientService.findEntity(appointment.getClient().getId()).getAppointments().remove(appointment);
                        appointmentService.deleteEntity(appointment);
                        response = new Response(Responses.OK, "Готово!", "");
                        break;
                    }
                    case ADD_CLIENT:{
                        Client client = gson.fromJson(request.getRequestMessage(), Client.class);
                        client.setUser(userService.findEntity(client.getUser().getId()));
                            clientService.saveEntity(client);
                            response = new Response(Responses.OK, "Готово!", "");
                        break;
                    }
                    case UPDATE_CLIENT:{
                        Client client = gson.fromJson(request.getRequestMessage(), Client.class);
                        client.setUser(userService.findEntity(client.getUser().getId()));
                        userService.updateEntity(client.getUser());
                        clientService.updateEntity(client);
                        response = new Response(Responses.OK, "Готово!", "");
                        break;
                    }
                    case UPDATE_USER:
                    {
                        User user = gson.fromJson(request.getRequestMessage(), User.class);
                        userService.updateEntity(user);
                        response = new Response(Responses.OK, "Готово!", "");
                        break;
                    }
                    case UPDATE_EMPLOYEE:{
                        Employee employee = gson.fromJson(request.getRequestMessage(), Employee.class);
                        employeeService.updateEntity(employee);
                        response = new Response(Responses.OK, "Готово!", "");
                        break;
                    }
                    case UPDATE_SERVICE:{
                        Service service = gson.fromJson(request.getRequestMessage(), Service.class);
                        serviceService.updateEntity(service);
                        response = new Response(Responses.OK, "Готово!", "");
                        break;
                    }
                    case DELETE_USER:{
                        User user = gson.fromJson(request.getRequestMessage(), User.class);
                        user = userService.findEntity(user.getId());
                        for(Client client: user.getClients())
                        {
                            clientService.deleteEntity(client);
                        }
                        user.getClients().clear();
                        userService.deleteEntity(user);
                        response = new Response(Responses.OK, "готово", "");
                        break;
                    }
                    default:
                        throw new IllegalStateException("Unexpected value: " + request.getRequestType());
                }
                out.println(gson.toJson(response));
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Клиент " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " закрыл соединение.");
            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
