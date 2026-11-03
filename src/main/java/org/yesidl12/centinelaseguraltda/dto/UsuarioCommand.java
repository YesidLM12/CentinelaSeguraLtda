package org.yesidl12.centinelaseguraltda.dto;

import jakarta.validation.constraints.NotBlank;


public record UsuarioCommand(
    @NotBlank(message = "El nombre es obligatorio") String nombre,
    @NotBlank(message = "El apellido es obligatorio") String apellido,
    @NotBlank(message = "El documento es obligatorio") String documento,
    @NotBlank(message = "El email es obligatorio") String email,
    @NotBlank(message = "La contraseña es obligatoria") String password,
    @NotBlank(message = "El rol es obligatorio") String rol
) {

}
