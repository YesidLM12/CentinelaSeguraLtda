package org.yesidl12.centinelaseguraltda.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String message
) {

}
