package models.entities;

import enums.Roles;

import java.util.HashSet;
import java.util.Set;

public class User {
    private int id;
    private String login;
    private String password;
    private String role;
    private Set<Client> clients = new HashSet<>();

    public User(){}
    public User(int id, String login, String password, String role, Set<Client> clients){
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.clients = clients;
    }

    public User(int id) {
        this.id = id;
    }

    public String getRole() { return role; }
    public void setRole(Roles role) {
        this.role = role.toString();
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Set<Client> getClients(){ return clients; }
    public void setClients(Set<Client> clients) { this.clients = clients; }
    
    public String getClientsIds(User user)
    {
        String ids = null;
        for(int i = 0; i < user.getClients().size(); i++){
            ids = user.getClients().stream().iterator().next().getId() + " ";
        }
        return ids;
    }
}
