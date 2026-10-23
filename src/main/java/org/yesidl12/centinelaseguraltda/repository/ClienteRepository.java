package org.yesidl12.centinelaseguraltda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yesidl12.centinelaseguraltda.entity.ClienteEntity;


public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
}
