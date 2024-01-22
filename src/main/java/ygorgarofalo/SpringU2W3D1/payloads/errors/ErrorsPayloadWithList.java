package ygorgarofalo.SpringU2W3D1.payloads.errors;

import java.util.List;

public record ErrorsPayloadWithList(
        String message,

        List<String> errorsList
) {
}
