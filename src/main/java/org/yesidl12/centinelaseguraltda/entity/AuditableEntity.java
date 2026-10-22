package org.yesidl12.centinelaseguraltda.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {
   @CreatedDate
   @Column(nullable = false, updatable = false)
   private LocalDateTime creado_en;

   @CreatedBy
   @Column(updatable = false)
   private String creado_por;

   @LastModifiedDate
   private LocalDateTime modifica_en;

   @LastModifiedBy
   private LocalDateTime modifica_por;
}
