package org.yesidl12.centinelaseguraltda.dto;

import java.time.LocalDate;

public record EmpleadoCommand(
    String documento,
    String nombre,
    String correo,
    String cargo,
    String numeroCredencial,
    LocalDate venceCredencial,
    String telefono,
    LocalDate fechaIngreso
) {

    public EmpleadoCommand(Object object, Object object2, Object object3, Object object4, Object object5,
            Object object6, Object object7, Object object8) {
        this( null, null, null, null, null,null,null,null);
    }
}
