package org.yesidl12.centinelaseguraltda.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.yesidl12.centinelaseguraltda.enums.Rol;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
	name = "usuario",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "UQ_usuario_tipo_numero_documento",
			columnNames = {"tipo_documento", "documento"}),
		@UniqueConstraint(
			name = "UQ_usuario_correo",
			columnNames = "correo"
		)},
	check = {
		@CheckConstraint(
			name = "CK_usuario_correo",
			constraint = "correo REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[.][A-Za-z]{2,}$"
		),
		@CheckConstraint(
			name = "CK_usuario_documento",
			constraint = "documento REGEXP '^[0-9]+$'"
		)})
public class UsuarioEntity extends AuditableEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "usuario_id", nullable = false, updatable = false)
   private Long id;

   @Column(name = "nombre", nullable = false)
   private String nombre;

   @Column(name = "documento", nullable = false)
   private String documento;

   @Email
   @Column(name = "correo", nullable = false)
   private String correo;

   @Column(name = "password_hash", nullable = false)
   private String passwordHash;

   @Enumerated(EnumType.STRING)
   @Column(name = "rol", nullable = false)
   private Rol rol;

   @Column(name = "activo", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
   private boolean activo;

   @Column(name = "debe_cambiar_password", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
   private boolean debeCambiarPassword;

   @Column(name = "intentos_fallidos", nullable = false, columnDefinition = "INT DEFAULT 0")
   private int  intentosFallidos;

   @Column(name = "bloqueado_hasta")
   private LocalDateTime bloqueadoHasta;

   @Column(name = "ultimo_acceso")
   private LocalDateTime ultimoAcceso;
}
