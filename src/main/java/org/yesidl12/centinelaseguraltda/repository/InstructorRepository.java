package org.yesidl12.centinelaseguraltda.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yesidl12.centinelaseguraltda.entity.InstructorEntity;

@Repository
public interface InstructorRepository extends JpaRepository<InstructorEntity, Long> {

}
