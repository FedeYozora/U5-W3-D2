package it.epicode.Device.Management.services;

import it.epicode.Device.Management.entities.Device;
import it.epicode.Device.Management.entities.User;
import it.epicode.Device.Management.enums.StatusDevices;
import it.epicode.Device.Management.exceptions.NotFoundException;
import it.epicode.Device.Management.payloads.NewDeviceDTO;
import it.epicode.Device.Management.repos.DeviceRepo;
import it.epicode.Device.Management.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepo deviceRepo;
    @Autowired
    private UserRepo userRepo;

    public Page<Device> getDevices(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return deviceRepo.findAll(pageable);
    }

    public Device findByID(Integer id) {
        return deviceRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Device save(@RequestBody NewDeviceDTO body) {
        Device newDevice = new Device();
        newDevice.setType(body.type());
        newDevice.setStatusDevices(body.statusDevices());

        return deviceRepo.save(newDevice);
    }

    public Device findByIDAndUpdate(Integer id, Device body) {
        Device device = deviceRepo.findById(id).orElseThrow(() -> new NotFoundException(id));

        device.setType(body.getType());
        device.setStatusDevices(body.getStatusDevices());
        return deviceRepo.save(device);
    }

    public Device findByIDAndAssign(Integer id, Device body, UUID uuid) {
        Device device = deviceRepo.findById(id).orElseThrow(() -> new NotFoundException(id));

        device.setType(body.getType());
        device.setStatusDevices(StatusDevices.ASSEGNATO);

        User user = userRepo.findById(uuid).orElseThrow(() -> new NotFoundException(id));
        device.setUser(user);
        return deviceRepo.save(device);
    }

    public void findByIDAndDelete(Integer id) {
        Device device = this.findByID(id);
        deviceRepo.delete(device);
    }
}
