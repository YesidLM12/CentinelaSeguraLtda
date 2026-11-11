package org.yesidl12.centinelaseguraltda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yesidl12.centinelaseguraltda.entity.EmpleadoEntity;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {

    EmpleadoEntity findByDocumento(String documento);
}
