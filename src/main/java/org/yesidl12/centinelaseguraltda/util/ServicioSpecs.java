package org.yesidl12.centinelaseguraltda.util;

import org.springframework.data.jpa.domain.Specification;
import org.yesidl12.centinelaseguraltda.dto.FiltroServicioDto;
import org.yesidl12.centinelaseguraltda.entity.ServicioEntity;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class ServicioSpecs {

    public static Specification<ServicioEntity> filtrar(FiltroServicioDto filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtro.modalidad() != null) {
                predicates.add(criteriaBuilder.equal(root.get("modalidad"), filtro.modalidad()));
            }
            
            if (filtro.ubicacion() != null && !filtro.ubicacion().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("ubicacion")), 
                    "%" + filtro.ubicacion().toLowerCase() + "%"
                ));
            }

            if (filtro.tarifaMin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("tarifa"), filtro.tarifaMin()));
            }
            
            if (filtro.tarifaMax() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("tarifa"), filtro.tarifaMax()));
            }

            if (filtro.duracionMin() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("duracion_dias"), filtro.duracionMin()));
            }
            
            if (filtro.duracionMax() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("duracion_dias"), filtro.duracionMax()));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
