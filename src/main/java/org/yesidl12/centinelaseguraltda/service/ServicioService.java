package org.yesidl12.centinelaseguraltda.service;

import java.math.BigDecimal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.yesidl12.centinelaseguraltda.dto.ServicioCommand;
import org.yesidl12.centinelaseguraltda.entity.ServicioEntity;
import org.yesidl12.centinelaseguraltda.enums.Medio;
import org.yesidl12.centinelaseguraltda.enums.Modalidad;
import org.yesidl12.centinelaseguraltda.exception.ServicioConflictException;
import org.yesidl12.centinelaseguraltda.repository.ServicioRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServicioService {
    private final ServicioRepository servicioRepository;
    
    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public ServicioEntity crearServicio(ServicioCommand command, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        
        boolean existeServicio = servicioRepository.findByNombre(command.nombre()).isPresent();
        
        if (existeServicio) {
            log.warn("Intento de crear servicio duplicado por usuario: {}", usuarioLogueado);
            throw new ServicioConflictException("El servicio " + command.nombre() + " ya existe");
        }

        var servicioNuevo = ServicioEntity.builder()
            .nombre(command.nombre())
            .modalidad(Modalidad.valueOf(command.modalidad().toUpperCase()))
            .medio(Medio.valueOf(command.medio().toUpperCase()))
            .descripcion(command.descripcion())
            .ubicacion(command.ubicacion())
            .tarifa(BigDecimal.valueOf(command.tarifa()))
            .duracion_dias(command.duracionDias())
            .activo(true)
            .build();
        
        log.info("Creando servicio: {} por usuario: {}", command.nombre(), usuarioLogueado);
        return servicioRepository.save(servicioNuevo);
    }

}
