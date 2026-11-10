package org.yesidl12.centinelaseguraltda.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.yesidl12.centinelaseguraltda.dto.FiltroServicioDto;
import org.yesidl12.centinelaseguraltda.dto.ServicioCommand;
import org.yesidl12.centinelaseguraltda.dto.ServicioResponse;
import org.yesidl12.centinelaseguraltda.entity.ServicioEntity;
import org.yesidl12.centinelaseguraltda.enums.Medio;
import org.yesidl12.centinelaseguraltda.enums.Modalidad;
import org.yesidl12.centinelaseguraltda.exception.ServicioConflictException;
import org.yesidl12.centinelaseguraltda.repository.ServicioRepository;
import org.yesidl12.centinelaseguraltda.util.ServicioSpecs;

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

    public Page<ServicioResponse> listarServiciosconFiltros(int page, int size, FiltroServicioDto filtro) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ServicioEntity> servicios = servicioRepository.findAll(ServicioSpecs.filtrar(filtro), pageable);

        return servicios.map(s -> new ServicioResponse(
            s.getId(),
            s.getNombre(),
            s.getModalidad().name(),
            s.getMedio().name(),
            s.getDescripcion(),
            s.getUbicacion(),
            s.getTarifa().doubleValue(),
            s.getDuracion_dias(),
            s.isActivo()
        ));
    }

    public ServicioEntity modificarServicio(Long id, ServicioCommand command, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        
        boolean existeServicio = servicioRepository.findById(id).isPresent();
        
        if (!existeServicio) {
            log.warn("Intento de modificar servicio inexistente por usuario: {}", usuarioLogueado);
            throw new ServicioConflictException("El servicio no existe");
        }

        ServicioEntity servicio = servicioRepository.findById(id).get();
        servicio.setNombre(command.nombre() != null ? command.nombre() : servicio.getNombre());
        servicio.setModalidad(command.modalidad() != null ? Modalidad.valueOf(command.modalidad().toUpperCase()) : servicio.getModalidad());
        servicio.setMedio(command.medio() != null ? Medio.valueOf(command.medio().toUpperCase()) : servicio.getMedio());
        servicio.setDescripcion(command.descripcion() != null ? command.descripcion() : servicio.getDescripcion());
        servicio.setUbicacion(command.ubicacion() != null ? command.ubicacion() : servicio.getUbicacion());
        servicio.setTarifa(command.tarifa() != null ? BigDecimal.valueOf(command.tarifa()) : servicio.getTarifa());
        servicio.setDuracion_dias(command.duracionDias() != null ? command.duracionDias() : servicio.getDuracion_dias());
        servicio.setActivo(true);
        
        log.info("Modificando servicio: {} por usuario: {}", command.nombre(), usuarioLogueado);
        return servicioRepository.save(servicio);
    }

    public void eliminarServicio(Long id, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        
        boolean existeServicio = servicioRepository.findById(id).isPresent();
        
        if (!existeServicio) {
            log.warn("Intento de desactivar servicio inexistente por usuario: {}", usuarioLogueado);
            throw new ServicioConflictException("El servicio no existe");
        }

        ServicioEntity servicio = servicioRepository.findById(id).get();
        
        if (servicio.tieneContratacionesActivas()) {
            log.warn("Intento de desactivar servicio con contrataciones por usuario: {}", usuarioLogueado);
            desactivarServicio(id, authentication);
            throw new ServicioConflictException("El servicio tiene contrataciones asociadas");
        }

        servicioRepository.delete(servicio);
        
        log.info("Servicio eliminado por usuario: {}", usuarioLogueado);
    }

    public void desactivarServicio(Long id, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        
        ServicioEntity servicio = servicioRepository.findById(id).get();
        
        if (servicio == null) {
            log.warn("Intento de desactivar servicio inexistente por usuario: {}", usuarioLogueado);
            throw new ServicioConflictException("El servicio no existe");
        }
        
        servicio.setActivo(false);
        
        log.info("Desactivando servicio: {} por usuario: {}", servicio.getNombre(), usuarioLogueado);
        servicioRepository.save(servicio);
    }

    public ServicioEntity obtenerServicioPorId(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    public ServicioResponse obtenerServicioDetallado(Long id) {
        // TODO: Implementar lógica para obtener servicio detallado
        // Debe incluir: personal asignado, horarios, equipos utilizados
        return null;
    }

}
