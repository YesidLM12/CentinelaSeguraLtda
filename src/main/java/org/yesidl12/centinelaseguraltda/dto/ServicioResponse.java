package org.yesidl12.centinelaseguraltda.dto;

public record ServicioResponse(
    Long id,
    String nombre,
    String modalidad,
    String medio,
    String descripcion,
    String ubicacion,
    double tarifa,
    int duracionDias,
    boolean activo
) {

}
