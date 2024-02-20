package it.epicode.Device.Management.repos;

import it.epicode.Device.Management.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepo extends JpaRepository<Device, Integer> {
}
