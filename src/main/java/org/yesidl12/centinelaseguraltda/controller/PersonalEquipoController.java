package org.yesidl12.centinelaseguraltda.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.yesidl12.centinelaseguraltda.dto.EmpleadoCommand;
import org.yesidl12.centinelaseguraltda.dto.EquipoCommand;
import org.yesidl12.centinelaseguraltda.dto.FilaRecursoVM;
import org.yesidl12.centinelaseguraltda.enums.Cargo;
import org.yesidl12.centinelaseguraltda.enums.Estado_Equipo;
import org.yesidl12.centinelaseguraltda.enums.Tipo_Equipo;
import org.yesidl12.centinelaseguraltda.repository.EmpleadoRepository;
import org.yesidl12.centinelaseguraltda.repository.EquipoRepository;
import org.yesidl12.centinelaseguraltda.repository.ServicioRepository;
import org.yesidl12.centinelaseguraltda.service.PersonalEquipoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/personal-equipo")
public class PersonalEquipoController {

    private final PersonalEquipoService personalEquipoService;
    private final EmpleadoRepository empleadoRepository;
    private final EquipoRepository equipoRepository;
    private final ServicioRepository servicioRepository;

    // ==================== PERSONAL ====================

    @GetMapping("/personal")
    public String listarPersonal(Model model) {
        model.addAttribute("empleados", empleadoRepository.findAll());
        model.addAttribute("cargos", Cargo.values());
        model.addAttribute("empleadoForm", new EmpleadoCommand(null, null, null, null, null, null));
        return "personal/lista";
    }

    @PostMapping("/personal")
    public String crearPersonal(@ModelAttribute("empleadoForm") EmpleadoCommand command,
            Authentication authentication, RedirectAttributes flash) {
        try {
            personalEquipoService.crearPersonal(command, authentication);
            flash.addFlashAttribute("exito", "Empleado creado correctamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/personal-equipo/personal";
    }

    @PostMapping("/personal/{documento}/actualizar")
    public String actualizarPersonal(@org.springframework.web.bind.annotation.PathVariable String documento,
            @ModelAttribute EmpleadoCommand command, Authentication authentication, RedirectAttributes flash) {
        try {
            personalEquipoService.actualizarPersonal(command, authentication);
            flash.addFlashAttribute("exito", "Empleado actualizado correctamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/personal-equipo/personal";
    }

    @PostMapping("/personal/{documento}/eliminar")
    public String eliminarPersonal(@org.springframework.web.bind.annotation.PathVariable String documento,
            Authentication authentication, RedirectAttributes flash) {
        try {
            personalEquipoService.eliminarPersonal(documento, authentication);
            flash.addFlashAttribute("exito", "Empleado desactivado correctamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/personal-equipo/personal";
    }

    // ==================== EQUIPOS ====================

    @GetMapping("/equipos")
    public String listarEquipos(Model model) {
        model.addAttribute("equipos", equipoRepository.findAll());
        model.addAttribute("tipos", Tipo_Equipo.values());
        model.addAttribute("estados", Estado_Equipo.values());
        model.addAttribute("equipoForm", new EquipoCommand(null, null, null, null, null, null, null));
        return "equipos/lista";
    }

    @PostMapping("/equipos")
    public String crearEquipo(@RequestParam Long id, @ModelAttribute("equipoForm") EquipoCommand command,
            Authentication authentication, RedirectAttributes flash) {
        try {
            personalEquipoService.crearEquipo(id, command, authentication);
            flash.addFlashAttribute("exito", "Equipo creado correctamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/personal-equipo/equipos";
    }

    @PostMapping("/equipos/{id}/actualizar")
    public String actualizarEquipo(@org.springframework.web.bind.annotation.PathVariable Long id,
            @ModelAttribute EquipoCommand command, Authentication authentication, RedirectAttributes flash) {
        try {
            personalEquipoService.actualizarEquipo(id, command, authentication);
            flash.addFlashAttribute("exito", "Equipo actualizado correctamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/personal-equipo/equipos";
    }

    @PostMapping("/equipos/{id}/eliminar")
    public String eliminarEquipo(@org.springframework.web.bind.annotation.PathVariable Long id,
            Authentication authentication, RedirectAttributes flash) {
        try {
            personalEquipoService.eliminarEquipo(id, authentication);
            flash.addFlashAttribute("exito", "Equipo eliminado correctamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/personal-equipo/equipos";
    }

    // ==================== ASIGNACIÓN A SERVICIOS ====================

    @GetMapping("/asignar/{idServicio}")
    public String formularioAsignacion(@org.springframework.web.bind.annotation.PathVariable Long idServicio,
            Model model) {
        model.addAttribute("servicio", servicioRepository.findById(idServicio).orElse(null));
        model.addAttribute("empleadosDisponibles", empleadoRepository.findAll());
        model.addAttribute("equiposDisponibles", equipoRepository.findAll());
        return "personal/asignar";
    }

    @PostMapping("/asignar/{idServicio}")
    public String asignar(@org.springframework.web.bind.annotation.PathVariable Long idServicio,
            @RequestParam(required = false) List<Long> idPersonal,
            @RequestParam(required = false) List<Long> idEquipos,
            Authentication authentication, RedirectAttributes flash) {
        try {
            personalEquipoService.asignarPersonalEquipoAServicio(idEquipos, idPersonal, idServicio, authentication);
            flash.addFlashAttribute("exito", "Personal y equipos asignados correctamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/personal-equipo/asignar/" + idServicio;
    }

    @GetMapping("/recursos")
public String listarRecursos(@RequestParam(required = false) String filtro, Model model) {
    List<FilaRecursoVM> filas = new ArrayList<>();

    if (filtro == null || filtro.equals("PERSONAL")) {
        empleadoRepository.findAll().forEach(e -> filas.add(new FilaRecursoVM(
                "PERSONAL",
                e.getDocumento(),
                e.getNombre(),
                e.getCargo() != null ? e.getCargo().name() : "",
                "",
                Boolean.TRUE.equals(e.getActivo()) ? "ACTIVO" : "INACTIVO",
                e.getVence_credencial() != null ? e.getVence_credencial().toString() : "Sin fecha")));
    }

    if (filtro == null || filtro.equals("EQUIPO")) {
        equipoRepository.findAll().forEach(q -> filas.add(new FilaRecursoVM(
                "EQUIPO",
                String.valueOf(q.getId()),
                q.getDescripcion(),
                q.getTipo() != null ? q.getTipo().name() : "",
                q.getUbicacion(),
                q.getEstado() != null ? q.getEstado().name() : "",
                "")));
    }

    model.addAttribute("filas", filas);
    model.addAttribute("filtro", filtro == null ? "TODOS" : filtro);
    return "recursos/lista";
}
}