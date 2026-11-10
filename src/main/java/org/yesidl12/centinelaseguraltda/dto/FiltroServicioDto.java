package org.yesidl12.centinelaseguraltda.dto;

import org.yesidl12.centinelaseguraltda.enums.Modalidad;

import java.math.BigDecimal;

public record FiltroServicioDto(
    Modalidad modalidad,
    String ubicacion,
    BigDecimal tarifaMin,
    BigDecimal tarifaMax,
    Integer duracionMin,
    Integer duracionMax
) {

}
