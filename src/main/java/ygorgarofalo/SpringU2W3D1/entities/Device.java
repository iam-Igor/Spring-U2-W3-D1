package ygorgarofalo.SpringU2W3D1.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue
    private long id;


    // Stati possibili del device: DISPONIBILE, ASSEGNATO, IN_MANUTENZIONE, DISMESSO
    private String status;


    // Tipi di device: SMARTPHONE, TABLET, LAPTOP
    private String deviceType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Device(String status, String deviceType) {
        this.status = status;
        this.deviceType = deviceType;
    }


}
