package ygorgarofalo.SpringU2W3D1.payloads;

import jakarta.validation.constraints.NotEmpty;

public record DevicePayloadDTO(


        //Payload per la creazione POST di un device
        @NotEmpty(message = "Errore nella sintassi dello status; Diciture diponibili: DISPONIBILE, ASSEGNATO, IN_MANUTENZIONE, DISMESSO")
        String status,

        @NotEmpty(message = "Errore nella sintassi del tipo di dispositivo; Diciture disponibili: SMARTPHONE, TABLET, LAPTOP")
        String deviceType) {
}
