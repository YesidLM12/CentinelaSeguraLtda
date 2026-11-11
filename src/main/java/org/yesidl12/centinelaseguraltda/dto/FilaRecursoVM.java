package org.yesidl12.centinelaseguraltda.dto;

public record FilaRecursoVM(
     String tipo,          // "PERSONAL" o "EQUIPO"
        String identificador, // documento del empleado o ID del equipo
        String nombre,        // nombre del empleado o descripción del equipo
        String detalle,       // cargo o marca/tipo de equipo
        String ubicacion,     // solo aplica a equipos
        String estado,        // activo/inactivo o estado del equipo
        String vencimiento 
) {

}
