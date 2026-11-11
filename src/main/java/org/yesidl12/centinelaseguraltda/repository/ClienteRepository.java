package org.yesidl12.centinelaseguraltda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yesidl12.centinelaseguraltda.entity.ClienteEntity;


@Repository 
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    boolean existsByDocumentoIgnoreCase(String documento);

    boolean existsByDocumentoIgnoreCaseAndIdNot(String documento, Long id);
}
