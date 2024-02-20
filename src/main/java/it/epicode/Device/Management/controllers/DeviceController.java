package it.epicode.Device.Management.controllers;

import it.epicode.Device.Management.entities.Device;
import it.epicode.Device.Management.exceptions.BadRequestException;
import it.epicode.Device.Management.payloads.NewDeviceDTO;
import it.epicode.Device.Management.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping("")
    public Page<Device> getDevices(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return deviceService.getDevices(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Device findById(@PathVariable Integer id) {
        return deviceService.findByID(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Device saveNewDevice(@RequestBody @Validated NewDeviceDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException();
        } else {
            return deviceService.save(body);
        }
    }

    @PutMapping("/{id}")
    public Device findByIDAndUpdate(@PathVariable int id, @RequestBody Device body) {
        return deviceService.findByIDAndUpdate(id, body);
    }

    @PutMapping("/{id}/{userid}")
    public Device findByIDAndAssignToUser(@PathVariable int id, @RequestBody Device body, @RequestParam("userID") UUID uuid) {
        return deviceService.findByIDAndAssign(id, body, uuid);
    }

    @DeleteMapping("/{id}")
    public void findByIDAndDelete(@PathVariable int id) {
        deviceService.findByIDAndDelete(id);
    }
}
