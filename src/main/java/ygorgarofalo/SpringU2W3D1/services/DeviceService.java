package ygorgarofalo.SpringU2W3D1.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ygorgarofalo.SpringU2W3D1.entities.Device;
import ygorgarofalo.SpringU2W3D1.entities.User;
import ygorgarofalo.SpringU2W3D1.exceptions.NotFoundExc;
import ygorgarofalo.SpringU2W3D1.payloads.DeviceAssignmentPayloadDTO;
import ygorgarofalo.SpringU2W3D1.payloads.DevicePayloadDTO;
import ygorgarofalo.SpringU2W3D1.repositories.DeviceDAO;
import ygorgarofalo.SpringU2W3D1.repositories.UserDAO;

@Service
public class DeviceService {


    @Autowired
    private DeviceDAO deviceDAO;


    @Autowired
    private UserDAO userDAO;


    // metodo associato al get /devices
    public Page<Device> getAllDevices(int page, int size, String order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        return deviceDAO.findAll(pageable);
    }


    // metodo associato alla get /devices/deviceId
    public Device findById(long id) {
        return deviceDAO.findById(id).orElseThrow(() -> new NotFoundExc(id));
    }


    // metodo associato alla POST /devices

    public Device saveDevice(DevicePayloadDTO payload) {

        Device newDevice = new Device(payload.status(), payload.deviceType());
        return deviceDAO.save(newDevice);

    }


    // metodo associato alla delete /devices/deviceId
    public void findByIdAndDelete(long id) {

        Device found = this.findById(id);

        deviceDAO.delete(found);
    }


    // metodo che aggiorna solo i campi relativi allo stato ed al tipo di device tramite PUT su
    // /devices/deviceId con payload

    public Device findByIdAndUpdate(long id, DevicePayloadDTO payload) {

        Device found = this.findById(id);

        found.setDeviceType(payload.deviceType());
        found.setStatus(payload.status());

        return deviceDAO.save(found);

    }


    // metodo associato al PUT su /devices/assign con payload per assegnare un dispositivo ad un utente
    //il payload contiene un id utente e un id device

    public Device assignUserToDevice(DeviceAssignmentPayloadDTO payload) {

        //cerco i riferimenti a user e device nel db tramite i dati arrivati dal payload
        User userFound = userDAO.findById(payload.userId()).orElseThrow(() -> new NotFoundExc(payload.userId()));
        Device deviceFound = this.findById(payload.deviceId());

        // aggiungo il device alla lista di device dello user e salvo tramite il suo DAO
        userFound.getDeviceList().add(deviceFound);
        userDAO.save(userFound);

        //associo lo user al device, aggiorno il suo status e salvo
        deviceFound.setUser(userFound);
        deviceFound.setStatus("ASSEGNATO");

        return deviceDAO.save(deviceFound);

    }


}
