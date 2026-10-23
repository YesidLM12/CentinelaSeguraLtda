package org.yesidl12.centinelaseguraltda.entity;

import jakarta.persistence.*;
import lombok.*;
import org.yesidl12.centinelaseguraltda.enums.Tipo_Cliente;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
	name = "cliente",
	indexes = {
		@Index(
			name = "IDX_cliente_razon_social",
			columnList = "nombre_razon_social"
		)},
	uniqueConstraints = {
		@UniqueConstraint(
			name = "UQ_cliente_tipo_numero_documento",
			columnNames = {"tipo", "documento"}
		)
	}
)
public class ClienteEntity extends AuditableEntity{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "cliente_id", nullable = false)
   private Long id;

   @Enumerated(EnumType.STRING)
   @Column(
	   name = "tipo",
	   nullable = false,
	   check = @CheckConstraint(
		   name = "CK_cliente_tipo",
		   constraint = "tipo = 'PERSONA' OR representante_legal IS NOT NUL"))
   private Tipo_Cliente tipo;

   @Column(
	   name = "documento",
	   nullable = false,
	   length = 10,
	   check = @CheckConstraint(
		   name = "CK_cliente_documento",
		   constraint = "documento REGEXP '^[0-9]+$'"))
   private String documento;

   @Column(name = "nombre_razon_social", nullable = false, length = 150)
   private String nombreRazonSocial;

   @Column(name = "representante_legal", length = 100)
   private String representanteLegal;

   @Column(
	   name = "correo",
	   check = @CheckConstraint(
		   name = "CK_cliente_correo",
		   constraint = "correo REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[.][A-Za-z]{2,}$'"))
   private String correo;

   @Column(
	   name = "telefono",
	   nullable = false,
	   length = 15,
	   check = @CheckConstraint(
		   name = "CK_cliente_telefono",
		   constraint = "telefono REGEXP '^[0-9]+$'"))
   private String telefono;

   @Column(name = "direccion")
   private String direccion;

   @Column(name = "ciudad", length = 60)
   private String ciudad;

   @Column(name = "observaciones", length = 300)
   private String observaciones;

   @Column(name = "activo", nullable = false)
   private Boolean activo = true;
}
