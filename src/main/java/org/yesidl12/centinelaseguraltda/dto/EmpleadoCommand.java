package org.yesidl12.centinelaseguraltda.dto;

import java.time.LocalDate;

public record EmpleadoCommand(
    String documento,
    String nombre,
    String cargo,
    String numeroCredencial,
    LocalDate venceCredencial,
    String telefono,
    LocalDate fechaIngreso
) {

    public EmpleadoCommand(Object object, Object object2, Object object3, Object object4, Object object5,
            Object object6) {
        this(
            (String) object,
            (String) object2,
            (String) object3,
            (String) object4,
            (LocalDate) object5,
            (String) object6,
            null
        );
    }
}
