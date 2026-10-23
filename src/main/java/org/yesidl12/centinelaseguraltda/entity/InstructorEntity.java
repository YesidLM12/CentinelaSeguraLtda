package org.yesidl12.centinelaseguraltda.entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity 
@Table(name = "instructor")
public class InstructorEntity extends AuditableEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "instructor_id")
    private Long id;
    
    @Column (
        name = "documento", 
        length = 15, 
        nullable = false, 
        unique = true,
        check = @CheckConstraint(
            name = "CK_documento_numerico",
            constraint = "documento ~ '^[0-9]+$'"))
    private String documento;
    
    @Column (name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    @Column (name = "especialidad", length = 100)
    private String especialidad;
    
    @Column (name = "correo", length = 120)
    private String correo;
    
    @Builder.Default
    @Column (name = "activo", nullable = false)
    private Boolean activo = true;
}
