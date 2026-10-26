package org.yesidl12.centinelaseguraltda.dto;

public record UsuarioResponse(
    String documento,
    String nombre,
    String correo,
    String rol,
    boolean activo
) {
}
