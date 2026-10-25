package org.yesidl12.centinelaseguraltda.dto;

import java.time.LocalDateTime;

public record UsuarioResponse(
    String documento,
    String nombre,
    String correo,
    String rol,
    boolean activo,
    LocalDateTime ultimoAcceso
) {
}
