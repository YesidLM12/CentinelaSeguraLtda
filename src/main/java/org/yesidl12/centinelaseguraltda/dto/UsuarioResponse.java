package org.yesidl12.centinelaseguraltda.dto;

import java.time.LocalDateTime;

public record UsuarioResponse(
    String nombre,
    String documento,
    String correo,
    String rol,
    boolean activo,
    LocalDateTime ultimoAcceso
) {
}
