package org.yesidl12.centinelaseguraltda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yesidl12.centinelaseguraltda.entity.ServicioEntity;

import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioEntity, Long> {

    Optional<ServicioEntity> findByNombre(String nombre);
}
