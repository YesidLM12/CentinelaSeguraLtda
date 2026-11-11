package org.yesidl12.centinelaseguraltda.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.yesidl12.centinelaseguraltda.dto.EntrenamientoCommand;
import org.yesidl12.centinelaseguraltda.entity.EmpleadoEntity;
import org.yesidl12.centinelaseguraltda.entity.EntrenamientoEntity;
import org.yesidl12.centinelaseguraltda.entity.InstructorEntity;
import org.yesidl12.centinelaseguraltda.enums.Estado_Entrenamiento;
import org.yesidl12.centinelaseguraltda.enums.Tipo_Entrenamiento;
import org.yesidl12.centinelaseguraltda.repository.EntrenamientoRepository;
import org.yesidl12.centinelaseguraltda.repository.EmpleadoRepository;
import org.yesidl12.centinelaseguraltda.repository.InstructorRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EntrenamientoService {
    private final EntrenamientoRepository entrenamientoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final InstructorRepository instructorRepository;

    public EntrenamientoService(EntrenamientoRepository entrenamientoRepository, EmpleadoRepository empleadoRepository, InstructorRepository instructorRepository) {
        this.entrenamientoRepository = entrenamientoRepository;
        this.empleadoRepository = empleadoRepository;
        this.instructorRepository = instructorRepository;
    }

    public void crearEntrenamiento(EntrenamientoCommand entrenamientoCommand, Authentication authentication) {
        Optional<EntrenamientoEntity> entrenamientoEntity = entrenamientoRepository.findByNombre(entrenamientoCommand.nombre());
        
        if (entrenamientoEntity.isPresent()) {
            throw new RuntimeException("El entrenamiento con nombre " + entrenamientoCommand.nombre() + " ya existe");
        }
        
       Optional<InstructorEntity> instructorEntity = instructorRepository.findById(entrenamientoCommand.instructor());
       if (instructorEntity.isEmpty()) {
           throw new RuntimeException("El instructor con id " + entrenamientoCommand.instructor() + " no existe");
       }

        EntrenamientoEntity entrenamiento = new EntrenamientoEntity();
        entrenamiento.setNombre(entrenamientoCommand.nombre());
        entrenamiento.setTipo(Tipo_Entrenamiento.valueOf(entrenamientoCommand.tipo()));
        entrenamiento.setDescripcion(entrenamientoCommand.descripcion());
        entrenamiento.setContenido(entrenamientoCommand.contenido());
        entrenamiento.setIntensidadHoras(entrenamientoCommand.intensidadHoras());
        entrenamiento.setCosto(entrenamientoCommand.costo());
        entrenamiento.setCupoMaximo(entrenamientoCommand.cupoMaximo());
        entrenamiento.setFechaInicio(entrenamientoCommand.fechaInicio());
        entrenamiento.setFechaFin(entrenamientoCommand.fechaFin());
        entrenamiento.setHoraInicio(entrenamientoCommand.horaInicio());
        entrenamiento.setHoraFin(entrenamientoCommand.horaFin());
        entrenamiento.setLugar(entrenamientoCommand.lugar());
        entrenamiento.setEstado(Estado_Entrenamiento.valueOf(entrenamientoCommand.estado()));
        entrenamiento.setInstructor(instructorEntity.get());
        
        entrenamientoRepository.save(entrenamiento);
        
        log.info("Entrenamiento creado exitosamente por {}: {}", authentication.getName(), entrenamientoCommand.nombre());
    }

    public void actualizarEntrenamiento(EntrenamientoCommand entrenamientoCommand, Authentication authentication) {
        Optional<EntrenamientoEntity> entrenamientoEntity = entrenamientoRepository.findByNombre(entrenamientoCommand.nombre());
        
        if (entrenamientoEntity.isEmpty()) {
            throw new RuntimeException("El entrenamiento con nombre " + entrenamientoCommand.nombre() + " no existe");
        }

        EntrenamientoEntity entrenamiento = entrenamientoEntity.get();
        entrenamiento.setNombre(entrenamientoCommand.nombre() != null ? entrenamientoCommand.nombre() : entrenamiento.getNombre());
        entrenamiento.setTipo(entrenamientoCommand.tipo() != null ? Tipo_Entrenamiento.valueOf(entrenamientoCommand.tipo()) : entrenamiento.getTipo());
        entrenamiento.setDescripcion(entrenamientoCommand.descripcion() != null ? entrenamientoCommand.descripcion() : entrenamiento.getDescripcion());
        entrenamiento.setContenido(entrenamientoCommand.contenido() != null ? entrenamientoCommand.contenido() : entrenamiento.getContenido());
        entrenamiento.setIntensidadHoras(entrenamientoCommand.intensidadHoras() != null ? entrenamientoCommand.intensidadHoras() : entrenamiento.getIntensidadHoras());
        entrenamiento.setCosto(entrenamientoCommand.costo() != null ? entrenamientoCommand.costo() : entrenamiento.getCosto());
        entrenamiento.setCupoMaximo(entrenamientoCommand.cupoMaximo() != null ? entrenamientoCommand.cupoMaximo() : entrenamiento.getCupoMaximo());
        entrenamiento.setFechaInicio(entrenamientoCommand.fechaInicio() != null ? entrenamientoCommand.fechaInicio() : entrenamiento.getFechaInicio());
        entrenamiento.setFechaFin(entrenamientoCommand.fechaFin() != null ? entrenamientoCommand.fechaFin() : entrenamiento.getFechaFin());
        entrenamiento.setHoraInicio(entrenamientoCommand.horaInicio() != null ? entrenamientoCommand.horaInicio() : entrenamiento.getHoraInicio());
        entrenamiento.setHoraFin(entrenamientoCommand.horaFin() != null ? entrenamientoCommand.horaFin() : entrenamiento.getHoraFin());
        entrenamiento.setLugar(entrenamientoCommand.lugar() != null ? entrenamientoCommand.lugar() : entrenamiento.getLugar());
        entrenamiento.setEstado(entrenamientoCommand.estado() != null ? Estado_Entrenamiento.valueOf(entrenamientoCommand.estado()) : entrenamiento.getEstado());
        
        entrenamientoRepository.save(entrenamiento);
        
        log.info("Entrenamiento actualizado exitosamente por {}: {}", authentication.getName(), entrenamientoCommand.nombre());
    }

    public void eliminarEntrenamiento(Long id, Authentication authentication) {
        Optional<EntrenamientoEntity> entrenamientoEntity = entrenamientoRepository.findById(id);
        
        if (entrenamientoEntity.isEmpty()) {
            throw new RuntimeException("El entrenamiento con id " + id + " no existe");
        }
        
        entrenamientoRepository.delete(entrenamientoEntity.get());
        
        log.info("Entrenamiento eliminado exitosamente por {}: {}", authentication.getName(), id);
    }

    public EntrenamientoEntity obtenerEntrenamientoPorNombre(String nombre) {
        return entrenamientoRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("El entrenamiento con nombre " + nombre + " no existe"));
    }

    public Page<EntrenamientoEntity> obtenerEntrenamientos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return entrenamientoRepository.findAll(pageable);
    }

    public EntrenamientoEntity obtenerEntrenamientoPorId(Long id) {
        return entrenamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El entrenamiento con id " + id + " no existe"));
    }

}
