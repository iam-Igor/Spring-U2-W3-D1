package ygorgarofalo.SpringU2W3D1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ygorgarofalo.SpringU2W3D1.entities.Device;
import ygorgarofalo.SpringU2W3D1.exceptions.BadRequestExc;
import ygorgarofalo.SpringU2W3D1.payloads.DeviceAssignmentPayloadDTO;
import ygorgarofalo.SpringU2W3D1.payloads.DevicePayloadDTO;
import ygorgarofalo.SpringU2W3D1.responses.DeviceResponse;
import ygorgarofalo.SpringU2W3D1.services.DeviceService;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;


    // GET di tutti i devices sul db
    @GetMapping
    public Page<Device> getDevices(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String order) {
        return deviceService.getAllDevices(page, size, order);
    }


    //GET di un singolo device

    @GetMapping("/{id}")
    public Device getSingleDevice(@PathVariable long id) {

        return deviceService.findById(id);
    }


    //POST di un device, di default si crea il device senza associazione ad uno user,
    // successivamente se si vuole associarne uno ad uno user si usa il metodo put con diverso endpoint
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponse saveDevice(@RequestBody DevicePayloadDTO payload, BindingResult bindingResult) {

        // non avendo usato uno enum devo accertarmi che i valori di status e device typoe siano scritte correttamente

        if (bindingResult.hasErrors()) {
            throw new BadRequestExc(bindingResult.getAllErrors());
        } else if (!payload.status().equals("DISPONIBILE") &&
                !payload.status().equals("ASSEGNATO") &&
                !payload.status().equals("IN_MANUTENZIONE") &&
                !payload.status().equals("DISMESSO")) {
            throw new BadRequestExc("Errore nella sintassi dello status; Diciture diponibili: DISPONIBILE, ASSEGNATO, IN_MANUTENZIONE, DISMESSO");
        } else if (!payload.deviceType().equals("SMARTPHONE") &&
                !payload.deviceType().equals("TABLET") &&
                !payload.deviceType().equals("LAPTOP")) {
            throw new BadRequestExc("Errore nella sintassi del tipo di dispositivo; Diciture disponibili: SMARTPHONE, TABLET, LAPTOP");
        } else {
            Device newDevice = deviceService.saveDevice(payload);
            return new DeviceResponse(newDevice.getId());
        }


    }


    // PUT per associare un dispositivo ad uno user, passo nela payload id utente e id device
    //la funzione assignUserToDevice del service assegnerà lo user al device corretto
    // per problemi legati alla infinita ricursione stackoverflow, ho aggiunto @JsonIgnore alla lista di devices
    // nella classe user

    @PutMapping("/assign")
    @ResponseStatus(HttpStatus.CREATED)
    public Device assignDevice(@RequestBody DeviceAssignmentPayloadDTO payload, BindingResult bindingResult) {

        Device found = deviceService.findById(payload.deviceId());

        if (bindingResult.hasErrors()) {
            throw new BadRequestExc("Errori nel payload della richiesta");
        } else if (!found.getStatus().equals("DISPONIBILE")) {
            throw new BadRequestExc("Errore nell'associazione del device in quanto risulta in stato: " + found.getStatus());
        } else {
            deviceService.assignUserToDevice(payload);
            return deviceService.findById(payload.deviceId());
        }

    }


    //METODO PUT SPECIFICANDO SOLO PARAMETRO ID, SOLO PER AGGIORNARE LE PROPRIETA' DI UN DEVICE
    // CHE NON SIANO QUELLE LEGATE AD UN UTENTE
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Device updateDevice(@PathVariable long id, @RequestBody DevicePayloadDTO payload, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            throw new BadRequestExc(bindingResult.getAllErrors());
        } else if (!payload.status().equals("DISPONIBILE") &&
                !payload.status().equals("ASSEGNATO") &&
                !payload.status().equals("IN_MANUTENZIONE") &&
                !payload.status().equals("DISMESSO")) {
            throw new BadRequestExc("Errore nella sintassi dello status; Diciture diponibili: DISPONIBILE, ASSEGNATO, IN_MANUTENZIONE, DISMESSO");
        } else if (!payload.deviceType().equals("SMARTPHONE") &&
                !payload.deviceType().equals("TABLET") &&
                !payload.deviceType().equals("LAPTOP")) {
            throw new BadRequestExc("Errore nella sintassi del tipo di dispositivo; Diciture disponibili: SMARTPHONE, TABLET, LAPTOP");
        } else {
            return deviceService.findByIdAndUpdate(id, payload);
        }


    }


    // DELETE di un device
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        deviceService.findByIdAndDelete(id);
    }


}
