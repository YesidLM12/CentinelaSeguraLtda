package org.yesidl12.centinelaseguraltda.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
@Entity
@Table(name = "parametro")
public class ParametroEntity {
    @Id
    @Column ( name = "clave", nullable = false, length = 60)
    private String clave;
    
    @Column ( name = "valor", nullable = false, length = 200)
    private String valor;
    
    @Column ( name = "descripcion", length = 255)
    private String descripcion;

    @Column ( name = "modificado_en")
    private LocalDateTime modificadoEn;

    @ManyToOne
    @JoinColumn ( name = "modificado_por")
    private UsuarioEntity modificadoPor;
}