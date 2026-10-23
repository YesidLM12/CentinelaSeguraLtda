package org.yesidl12.centinelaseguraltda.entity;

import jakarta.persistence.*;
import lombok.*;
import org.yesidl12.centinelaseguraltda.enums.Medio;
import org.yesidl12.centinelaseguraltda.enums.Modalidad;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
	name = "servicio",
	indexes = {@Index(
		name = "IDX_servicio_modalidad",
		columnList = "modalidad")})
public class ServicioEntity extends AuditableEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "servicio_id", nullable = false, updatable = false)
   private Long id;

   @Column(name = "nombre", nullable = false, length = 120)
   private String nombre;

   @Enumerated(EnumType.STRING)
   @Column(name = "modalidad", nullable = false, length = 20)
   private Modalidad modalidad;

   @Enumerated(EnumType.STRING)
   @Column(name = "medio", nullable = false, length = 15)
   private Medio medio;

   @Column(name = "descripcion", nullable = false, length = 500)
   private String descripcion;

   @Column(name = "ubicacion", nullable = false, length = 150)
   private String ubicacion;

   @Column(
	   name = "tarifa",
	   nullable = false,
	   precision = 14,
	   scale = 2,
	   check = @CheckConstraint(
		   name = "CK_servicio_tarifa",
		   constraint = "tarifa > 0"))
   private BigDecimal tarifa;

   @Column(
	   name = "duracion_dias",
	   nullable = false,
	   check = @CheckConstraint(
		   name = "CK_servicio_duracion_dias",
		   constraint = "duracion_dias > 0"))
   private int duracion_dias;

   @Column(name = "activo", nullable = false)
   private Boolean activo = true;
}
