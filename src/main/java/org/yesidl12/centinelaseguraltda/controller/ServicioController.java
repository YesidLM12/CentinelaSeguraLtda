package org.yesidl12.centinelaseguraltda.controller;


import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;    
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yesidl12.centinelaseguraltda.dto.FiltroServicioDto;
import org.yesidl12.centinelaseguraltda.dto.ServicioCommand;
import org.yesidl12.centinelaseguraltda.dto.ServicioResponse;
import org.yesidl12.centinelaseguraltda.entity.ServicioEntity;
import org.yesidl12.centinelaseguraltda.service.ServicioService;

@Controller
@RequestMapping("/servicios")
public class ServicioController {
    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }
    
    @PostMapping
    public String crearServicio(@ModelAttribute ServicioCommand servicioCommand, Authentication authentication) {
        servicioService.crearServicio(servicioCommand, authentication);
        return "redirect:/servicios";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacionServicio(Model model) {
        model.addAttribute("servicio", new ServicioEntity());
        return "servicios/crear";
    }

    @GetMapping
    public String listarServicios(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "5") int size, 
        FiltroServicioDto filtro, 
        Model model) {
        Page<ServicioResponse> servicios = servicioService.listarServiciosconFiltros(page, size, filtro);
        model.addAttribute("servicios", servicios);
        model.addAttribute("filtro", filtro != null ? filtro : new FiltroServicioDto(null, null, null, null, null, null));
        return "servicios/lista";
    }

    @PatchMapping("/{id}")
    public String modificarServicio(@PathVariable Long id, @ModelAttribute ServicioCommand servicioCommand, Authentication authentication) {
        servicioService.modificarServicio(id, servicioCommand, authentication);
        return "redirect:/servicios";
    }
    
    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizacionServicio(@PathVariable Long id,ServicioCommand servicioCommand, Authentication authentication, Model model) {
        ServicioEntity servicio = servicioService.obtenerServicioPorId(id);
        model.addAttribute("servicio", servicio);
        return "servicios/actualizar";
    }

    @PatchMapping("/cambiar-estado/{id}")
    public String cambiarEstadoServicio(
        @PathVariable Long id,
        Authentication authentication
    ) {
        servicioService.desactivarServicio(id, authentication);
        return "redirect:/servicios";
    }

    @DeleteMapping("/{id}")
    public String eliminarServicio(@PathVariable Long id, Authentication authentication) {
        servicioService.eliminarServicio(id, authentication);
        return "redirect:/servicios";
    }

    @GetMapping("/eliminar/{id}")
    public String mostrarFormularioEliminacionServicio(@PathVariable Long id, Model model, Authentication authentication) {
        servicioService.eliminarServicio(id, authentication);
        model.addAttribute("servicio", id);
        model.addAttribute("authentication", authentication);
        return "servicios/eliminar";
    }

}
