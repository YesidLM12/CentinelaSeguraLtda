package org.yesidl12.centinelaseguraltda.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yesidl12.centinelaseguraltda.dto.EntrenamientoCommand;
import org.yesidl12.centinelaseguraltda.entity.EntrenamientoEntity;
import org.yesidl12.centinelaseguraltda.service.EntrenamientoService;

@Controller
@RequestMapping("/entrenamiento")
public class EntrenamientoController {
    private final EntrenamientoService entrenamientoService;

    public EntrenamientoController(EntrenamientoService entrenamientoService) {
        this.entrenamientoService = entrenamientoService;
    }

    @PostMapping
    public String crearEntrenamiento(@ModelAttribute EntrenamientoCommand command, Authentication authentication) {
        entrenamientoService.crearEntrenamiento(command, authentication);
        return "redirect:/entrenamiento";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacionEntrenamiento(Model model) {
        model.addAttribute("entrenamiento", new EntrenamientoEntity());
        return "entrenamiento/crear";
    }


    @GetMapping
    public String listarEntrenamientos(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "5") int size,
        Model model
    ) {
        Page<EntrenamientoEntity> entrenamientos = entrenamientoService.obtenerEntrenamientos(page, size);
        model.addAttribute("entrenamientos", entrenamientos);
        return "entrenamiento/lista";
    }

    @PatchMapping("/{id}")
    public String cambiarEstadoEntrenamiento(EntrenamientoCommand command, Authentication authentication) {
        entrenamientoService.actualizarEntrenamiento(command, authentication);
        return "redirect:/entrenamiento";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizacionEntrenamiento(@PathVariable Long id, EntrenamientoCommand command, Authentication authentication, Model model) {
        EntrenamientoEntity entrenamiento = entrenamientoService.obtenerEntrenamientoPorId(id);
        model.addAttribute("entrenamiento", entrenamiento);
        return "entrenamiento/actualizar";
    }

    @PostMapping("/{id}")
    public String eliminarEntrenamiento(@PathVariable Long id, Authentication authentication) {
        entrenamientoService.eliminarEntrenamiento(id, authentication);
        return "redirect:/entrenamiento";
    }
}
