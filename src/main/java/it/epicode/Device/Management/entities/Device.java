package it.epicode.Device.Management.entities;

import it.epicode.Device.Management.enums.StatusDevices;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String type;
    @Enumerated(EnumType.STRING)
    private StatusDevices statusDevices;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    public Device(String type, StatusDevices statusDevices) {
        this.type = type;
        this.statusDevices = StatusDevices.DISPONIBILE;
    }
}