package org.yesidl12.centinelaseguraltda.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record EntrenamientoCommand(
    String nombre,
    Long instructor,
    String tipo,
    String descripcion,
    String contenido,
    Integer intensidadHoras,
    BigDecimal costo,
    Integer cupoMaximo,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    LocalTime horaInicio,
    LocalTime horaFin,
    String lugar,
    String estado
) {
}
