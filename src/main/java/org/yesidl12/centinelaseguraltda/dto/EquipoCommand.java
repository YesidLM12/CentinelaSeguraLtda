package org.yesidl12.centinelaseguraltda.dto;

public record EquipoCommand(
    String tipo,
    String descripcion,
    String marca,
    String numeroPermiso,
    String estado,
    String ubicacion,
    String fechaAdquision
) {
}
