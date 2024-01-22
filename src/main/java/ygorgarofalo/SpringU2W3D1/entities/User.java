package ygorgarofalo.SpringU2W3D1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String surname;

    private String email;

    private String username;

    private String avatarUrl;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Device> deviceList;


    public User(String name, String surname, String email, String username) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.avatarUrl = null;
    }
}
