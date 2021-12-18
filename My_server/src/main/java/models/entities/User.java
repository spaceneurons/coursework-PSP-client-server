package models.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", schema = "public")
public class User {
    private int id;
    private String login;
    private String password;
    private String role;
    private transient Set<Client> clients = new HashSet<>();

    public User(){}
    public User(int id, String login, String password, String role, Set<Client> clients){
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.clients = clients;
    }

    @Column(name="role",length = 45)
    public String getRole() { return role; }
    public void setRole(String role) {
        this.role = role;
    }

    @Column(name="password",length = 45)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="login",length = 45)
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = {CascadeType.ALL})
    public Set<Client> getClients(){ return clients; }
    public void setClients(Set<Client> clients) { this.clients = clients; }
}
