package org.yesidl12.centinelaseguraltda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yesidl12.centinelaseguraltda.entity.EquipoEntity;

@Repository
public interface EquipoRepository extends JpaRepository<EquipoEntity, Long> {
}
