package org.yesidl12.centinelaseguraltda.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yesidl12.centinelaseguraltda.entity.EntrenamientoEntity;

import java.util.Optional;

@Repository
public interface EntrenamientoRepository extends JpaRepository<EntrenamientoEntity, Long> {

   Optional<EntrenamientoEntity> findByNombre(String nombre);

}
