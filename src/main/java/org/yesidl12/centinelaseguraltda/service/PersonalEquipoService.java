package org.yesidl12.centinelaseguraltda.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.yesidl12.centinelaseguraltda.repository.EquipoRepository;

import lombok.extern.slf4j.Slf4j;

import org.yesidl12.centinelaseguraltda.dto.EmpleadoCommand;
import org.yesidl12.centinelaseguraltda.dto.EquipoCommand;
import org.yesidl12.centinelaseguraltda.entity.EmpleadoEntity;
import org.yesidl12.centinelaseguraltda.entity.EquipoEntity;
import org.yesidl12.centinelaseguraltda.entity.InstructorEntity;
import org.yesidl12.centinelaseguraltda.entity.ServicioEntity;
import org.yesidl12.centinelaseguraltda.enums.Cargo;
import org.yesidl12.centinelaseguraltda.enums.Estado_Equipo;
import org.yesidl12.centinelaseguraltda.enums.Modalidad;
import org.yesidl12.centinelaseguraltda.enums.Tipo_Equipo;
import org.yesidl12.centinelaseguraltda.repository.EmpleadoRepository;
import org.yesidl12.centinelaseguraltda.repository.InstructorRepository;
import org.yesidl12.centinelaseguraltda.repository.ServicioRepository;

import jakarta.transaction.Transactional;

@Service
@Slf4j
public class PersonalEquipoService {
    private final EquipoRepository equipoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final ServicioRepository servicioRepository;
    private final InstructorRepository instructorRepository;

    public PersonalEquipoService(EquipoRepository equipoRepository, EmpleadoRepository empleadoRepository,
            ServicioRepository servicioRepository, InstructorRepository instructorRepository) {
        this.equipoRepository = equipoRepository;
        this.empleadoRepository = empleadoRepository;
        this.servicioRepository = servicioRepository;
        this.instructorRepository = instructorRepository;
    }

    public void crearPersonal(EmpleadoCommand command, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        log.info("Creando personal por usuario: {}", usuarioLogueado);


        if (empleadoRepository.findByDocumento(command.documento()) != null) {
            throw new IllegalArgumentException("Ya existe un empleado con documento " + command.documento());
        }
        if (command.venceCredencial() == null) {
            throw new IllegalArgumentException("Debe indicar la fecha de vencimiento de la credencial");
        }
        if (command.venceCredencial().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La credencial no puede tener una fecha de vencimiento pasada");
        }
        
        if (command.cargo().equals("INSTRUCTOR")) {
            if (instructorRepository.findByDocumento(command.documento()) != null) {
                throw new IllegalArgumentException("Ya existe un instructor con documento " + command.documento());
            }

            InstructorEntity instructor = new InstructorEntity();
            instructor.setDocumento(command.documento());
            instructor.setNombre(command.nombre());
            instructor.setEspecialidad("N/A");
            instructor.setCorreo(command.correo());
            instructor.setActivo(true);
            instructorRepository.save(instructor);
        }

        EmpleadoEntity nuevoEmpleado = new EmpleadoEntity();
        nuevoEmpleado.setDocumento(command.documento());
        nuevoEmpleado.setNombre(command.nombre());
        nuevoEmpleado.setTelefono(command.telefono());
        nuevoEmpleado.setCargo(Cargo.valueOf(command.cargo()));
        nuevoEmpleado.setNumero_credencial(command.numeroCredencial());
        nuevoEmpleado.setVence_credencial(command.venceCredencial());
        nuevoEmpleado.setFecha_ingreso(command.fechaIngreso());
        nuevoEmpleado.setActivo(true);

        empleadoRepository.save(nuevoEmpleado);

        log.info("Personal creado exitosamente con documento: {}", command.documento());
    }

    public Page<EmpleadoEntity> listarPersonal(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return empleadoRepository.findAll(pageable);
    }

    public void actualizarPersonal(EmpleadoCommand command, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        log.info("Actualizando personal por usuario: {}", usuarioLogueado);

        EmpleadoEntity empleado = empleadoRepository.findByDocumento(command.documento());

        if (empleado == null) {
            log.warn("El empleado con documento {} no existe", command.documento());
            return;
        }

        if (command.cargo().equals("INSTRUCTOR")) {
            InstructorEntity instructor = instructorRepository.findByDocumento(command.documento());
            if (instructor == null) {
                throw new IllegalArgumentException("No existe un instructor con documento " + command.documento());
            }
            instructor.setDocumento(command.documento() != null ? command.documento() : instructor.getDocumento());
            instructor.setNombre(command.nombre() != null ? command.nombre() : instructor.getNombre());
            instructor.setEspecialidad("N/A");
            instructor.setCorreo(command.correo() != null ? command.correo() : instructor.getCorreo());
        
            instructorRepository.save(instructor);
        }

        empleado.setNombre(command.nombre() != null ? command.nombre() : empleado.getNombre());
        empleado.setTelefono(command.telefono() != null ? command.telefono() : empleado.getTelefono());
        empleado.setCargo(command.cargo() != null ? Cargo.valueOf(command.cargo()) : empleado.getCargo());
        empleado.setNumero_credencial(
                command.numeroCredencial() != null ? command.numeroCredencial() : empleado.getNumero_credencial());
        empleado.setVence_credencial(
                command.venceCredencial() != null ? command.venceCredencial() : empleado.getVence_credencial());
        empleado.setFecha_ingreso(
                command.fechaIngreso() != null ? command.fechaIngreso() : empleado.getFecha_ingreso());
        empleado.setActivo(true);

        empleadoRepository.save(empleado);

        log.info("Personal actualizado exitosamente con documento: {}", command.documento());
    }

    public void eliminarPersonal(String documento, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        log.info("Eliminando personal por usuario: {}", usuarioLogueado);

        EmpleadoEntity empleado = empleadoRepository.findByDocumento(documento);

        if (empleado == null) {
            log.warn("El empleado con documento {} no existe", documento);
            return;
        }

        if (servicioRepository.existsById(empleado.getId())) {
            throw new IllegalArgumentException("No se puede eliminar el personal porque tiene servicios asignados");
        }

        empleadoRepository.delete(empleado);

        log.info("Personal eliminado exitosamente con documento: {}", documento);
    }

    public void activarPersonal(String documento, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        log.info("Activando personal por usuario: {}", usuarioLogueado);

        EmpleadoEntity empleado = empleadoRepository.findByDocumento(documento);

        if (empleado == null) {
            log.warn("El empleado con documento {} no existe", documento);
            return;
        }

        empleado.setActivo(true);

        empleadoRepository.save(empleado);

        log.info("Personal activado exitosamente con documento: {}", documento);
    }

    public void desactivarPersonal(String documento, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        log.info("Desactivando personal por usuario: {}", usuarioLogueado);

        EmpleadoEntity empleado = empleadoRepository.findByDocumento(documento);

        if (empleado == null) {
            log.warn("El empleado con documento {} no existe", documento);
            return;
        }

        empleado.setActivo(false);

        empleadoRepository.save(empleado);

        log.info("Personal desactivado exitosamente con documento: {}", documento);
    }

    public void crearEquipo(EquipoCommand command, Authentication authentication) {
        String usuarioLogueado = authentication.getName();

        log.info("Creando equipo por usuario: {}", usuarioLogueado);

        // Crear nuevo equipo
        EquipoEntity equipo = new EquipoEntity();
        equipo.setTipo(Tipo_Equipo.valueOf(command.tipo()));
        equipo.setDescripcion(command.descripcion());
        equipo.setMarca(command.marca());
        equipo.setNumeroPermiso(command.numeroPermiso());
        equipo.setEstado(Estado_Equipo.valueOf(command.estado()));
        equipo.setUbicacion(command.ubicacion());
        equipo.setFechaAdquision(LocalDate.parse(command.fechaAdquision()));

        equipoRepository.save(equipo);
    }

    public void actualizarEquipo(Long id, EquipoCommand command, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        log.info("Actualizando equipo por usuario: {}", usuarioLogueado);

        Optional<EquipoEntity> equipoExistente = equipoRepository.findById(id);

        if (equipoExistente.isEmpty()) {
            log.warn("El equipo con ID {} no existe", id);
            return;
        }

        // Actualizar equipo
        EquipoEntity equipo = equipoExistente.get();
        equipo.setTipo(Tipo_Equipo.valueOf(command.tipo()));
        equipo.setDescripcion(command.descripcion());
        equipo.setMarca(command.marca());
        equipo.setNumeroPermiso(command.numeroPermiso());
        equipo.setEstado(Estado_Equipo.valueOf(command.estado()));
        equipo.setUbicacion(command.ubicacion());
        equipo.setFechaAdquision(LocalDate.parse(command.fechaAdquision()));

        equipoRepository.save(equipo);

        log.info("Equipo actualizado exitosamente con ID: {}", id);
    }

    public void eliminarEquipo(Long id, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        log.info("Eliminando equipo por usuario: {}", usuarioLogueado);

        Optional<EquipoEntity> equipoExistente = equipoRepository.findById(id);

        if (equipoExistente.isEmpty()) {
            log.warn("El equipo con ID {} no existe", id);
            return;
        }

        equipoRepository.delete(equipoExistente.get());

        log.info("Equipo eliminado exitosamente con ID: {}", id);
    }

    public Page<EquipoEntity> listarEquipos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return equipoRepository.findAll(pageable);
    }

    @Transactional
    public void asignarPersonalEquipoAServicio(List<Long> idEquipos, List<Long> idPersonal, Long idServicio,
            Authentication authentication) {

        String usuarioLogueado = authentication.getName();
        log.info("Usuario {} asigna personal y equipos al servicio {}", usuarioLogueado, idServicio);

        // 1. Validar que el servicio exista
        ServicioEntity servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new IllegalArgumentException("El servicio con ID " + idServicio + " no existe"));

        // 2. Validar que se envíen datos
        if (idPersonal == null || idPersonal.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos un empleado");
        }

        // 3. Obtener la regla de la modalidad del servicio
        ReglaModalidad regla = reglaPara(servicio.getModalidad());

        if (regla.requiereEquipo() && (idEquipos == null || idEquipos.isEmpty())) {
            throw new IllegalArgumentException(
                    "El servicio " + servicio.getModalidad() + " requiere al menos un equipo");
        }

        List<Long> equiposUnicos = idEquipos == null ? List.of() : idEquipos.stream().distinct().toList();
        List<Long> personalUnico = idPersonal.stream().distinct().toList();

        // 4. Validar la cantidad de personal según la modalidad
        if (personalUnico.size() < regla.minPersonal() || personalUnico.size() > regla.maxPersonal()) {
            throw new IllegalArgumentException("El servicio " + servicio.getModalidad()
                    + " requiere entre " + regla.minPersonal() + " y " + regla.maxPersonal() + " empleados");
        }

        // 5. Validar equipos (sin modificar nada todavía)
        List<EquipoEntity> equiposAAsignar = new ArrayList<>();
        for (Long idEquipo : equiposUnicos) {
            EquipoEntity equipo = equipoRepository.findById(idEquipo)
                    .orElseThrow(() -> new IllegalArgumentException("El equipo con ID " + idEquipo + " no existe"));

            if (!esTipoAsignable(equipo.getTipo())) {
                throw new IllegalArgumentException("El equipo " + idEquipo + " no es asignable a un servicio");
            }
            if (servicio.getEquipos().contains(equipo)) {
                throw new IllegalArgumentException("El equipo " + idEquipo + " ya está asignado a este servicio");
            }
            if (equipo.getEstado() != Estado_Equipo.DISPONIBLE) {
                throw new IllegalArgumentException("El equipo " + idEquipo + " no está disponible");
            }
            equiposAAsignar.add(equipo);
        }

        // 6. Validar empleados (sin modificar nada todavía)
        List<EmpleadoEntity> empleadosAAsignar = new ArrayList<>();
        for (Long idPer : personalUnico) {
            EmpleadoEntity empleado = empleadoRepository.findById(idPer)
                    .orElseThrow(() -> new IllegalArgumentException("El empleado con ID " + idPer + " no existe"));

            if (servicio.getEmpleados().contains(empleado)) {
                throw new IllegalArgumentException("El empleado " + idPer + " ya está asignado a este servicio");
            }
            if (!Boolean.TRUE.equals(empleado.getActivo())) {
                throw new IllegalArgumentException("El empleado " + idPer + " no está activo");
            }
            if (empleado.getVence_credencial() == null || empleado.getVence_credencial().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("El empleado " + idPer + " tiene la credencial vencida");
            }
            if (!regla.cargosPermitidos().contains(empleado.getCargo())) {
                throw new IllegalArgumentException("El empleado " + idPer + " con cargo " + empleado.getCargo()
                        + " no puede asignarse a un servicio de tipo " + servicio.getModalidad());
            }
            empleadosAAsignar.add(empleado);
        }

        // 7. Aplicar cambios (todo ya validado)
        for (EquipoEntity equipo : equiposAAsignar) {
            equipo.setEstado(Estado_Equipo.OCUPADO);
            servicio.getEquipos().add(equipo);
        }
        servicio.getEmpleados().addAll(empleadosAAsignar);

        equipoRepository.saveAll(equiposAAsignar);
        servicioRepository.save(servicio);

        log.info("Asignación completada para el servicio {}", idServicio);
    }

    /* ==================== Reglas por modalidad ==================== */

    private record ReglaModalidad(int minPersonal, int maxPersonal,
            Set<Cargo> cargosPermitidos, boolean requiereEquipo) {
    }

    private ReglaModalidad reglaPara(Modalidad modalidad) {
        return switch (modalidad) {
            // Un puesto 24/7 necesita 4-5 vigilantes por el sistema de turnos
            case VIGILANCIA_FIJA -> new ReglaModalidad(4, 5,
                    EnumSet.of(Cargo.VIGILANTE), true);

            // Un vehículo = 2 personas (conductor y copiloto), hasta 2 vehículos
            case PATRULLAJE_MOVIL -> new ReglaModalidad(2, 4,
                    EnumSet.of(Cargo.VIGILANTE), true);

            // Escolta: 2 a 4 escoltas más conductor
            case ESCOLTA -> new ReglaModalidad(3, 5,
                    EnumSet.of(Cargo.ESCOLTA, Cargo.CONDUCTOR), true);

            // Monitoreo: operadores por turno más supervisor
            case MONITOREO -> new ReglaModalidad(4, 12,
                    EnumSet.of(Cargo.OPERADOR_MONITOREO, Cargo.SUPERVISOR), true);

            // Custodia de valores: custodio, conductor y escolta armado
            case CUSTODIA_VALORES -> new ReglaModalidad(2, 3,
                    EnumSet.of(Cargo.CUSTODIO, Cargo.CONDUCTOR, Cargo.ESCOLTA), true);
        };
    }

    private boolean esTipoAsignable(Tipo_Equipo tipo) {
        return tipo == Tipo_Equipo.ARMA
                || tipo == Tipo_Equipo.COMUNICACION
                || tipo == Tipo_Equipo.TECNOLOGICO;
    }

    public EquipoEntity obtenerEquipoPorId(Long id) {
        return equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }
}
