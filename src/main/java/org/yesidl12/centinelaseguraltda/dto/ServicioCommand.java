package org.yesidl12.centinelaseguraltda.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record ServicioCommand(
    @NotEmpty(message = "El nombre es requerido")
    String nombre,
    @NotEmpty(message = "La modalidad es requerida")
    String modalidad,
    @NotEmpty(message = "El medio es requerido")
    String medio,
    @NotEmpty(message = "La descripcion es requerida")
    String descripcion,
    @NotEmpty(message = "La ubicacion es requerida")
    String ubicacion,
    @NotEmpty(message = "La tarifa es requerida")
    @Min(value = 0, message = "La tarifa debe ser mayor o igual a 0")
    Double tarifa,
    @NotEmpty(message = "La duracion es requerida")
    @Min(value = 1, message = "La duracion debe ser mayor o igual a 1")
    Integer duracionDias
) {

}
