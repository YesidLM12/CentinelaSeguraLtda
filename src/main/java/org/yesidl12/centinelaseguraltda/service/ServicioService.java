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

    public void modificarServicio(Long id, ServicioCommand command, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        
        boolean existeServicio = servicioRepository.findById(id).isPresent();
        
        if (!existeServicio) {
            log.warn("Intento de modificar servicio inexistente por usuario: {}", usuarioLogueado);
            throw new ServicioConflictException("El servicio no existe");
        }

        ServicioEntity servicio = servicioRepository.findById(id).get();
        servicio.setNombre(command.nombre());
        servicio.setModalidad(Modalidad.valueOf(command.modalidad().toUpperCase()));
        servicio.setMedio(Medio.valueOf(command.medio().toUpperCase()));
        servicio.setDescripcion(command.descripcion());
        servicio.setUbicacion(command.ubicacion());
        servicio.setTarifa(BigDecimal.valueOf(command.tarifa()));
        servicio.setDuracion_dias(command.duracionDias());
        servicio.setActivo(true);
        
        log.info("Modificando servicio: {} por usuario: {}", command.nombre(), usuarioLogueado);
        servicioRepository.save(servicio);
    }

    // TODO => Restringir que se puede eliminar un servicio si tiene contratos asociados
    public void eliminarServicio(Long id, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        
        boolean existeServicio = servicioRepository.findById(id).isPresent();
        
        if (!existeServicio) {
            log.warn("Intento de desactivar servicio inexistente por usuario: {}", usuarioLogueado);
            throw new ServicioConflictException("El servicio no existe");
        }

        ServicioEntity servicio = servicioRepository.findById(id).get();
        servicio.setActivo(false);
        servicioRepository.save(servicio);
        
        log.info("Servicio desactivado por usuario: {}", usuarioLogueado);
    }

    public ServicioResponse obtenerServicioDetallado(Long id) {
        // TODO: Implementar lógica para obtener servicio detallado
        // Debe incluir: personal asignado, horarios, equipos utilizados
        return null;
    }

}
