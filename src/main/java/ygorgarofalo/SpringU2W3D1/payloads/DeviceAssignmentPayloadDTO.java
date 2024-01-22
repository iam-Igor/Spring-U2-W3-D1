package ygorgarofalo.SpringU2W3D1.payloads;

import jakarta.validation.constraints.NotEmpty;

public record DeviceAssignmentPayloadDTO(

        @NotEmpty(message = "L'id del device deve essere in un formato numerico")
        long deviceId,
        @NotEmpty(message = "L'id dello user deve essere in un formato numerico")
        long userId) {
}
