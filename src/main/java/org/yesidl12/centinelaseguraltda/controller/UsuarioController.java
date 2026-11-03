package org.yesidl12.centinelaseguraltda.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.yesidl12.centinelaseguraltda.dto.UsuarioCommand;
import org.yesidl12.centinelaseguraltda.dto.UsuarioResponse;
import org.yesidl12.centinelaseguraltda.entity.UsuarioEntity;
import org.yesidl12.centinelaseguraltda.service.UsuarioService;

@Controller()
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public String crearUsuario(
        @ModelAttribute UsuarioCommand usuarioCommand, 
        Authentication authentication
    ) {
        usuarioService.crearUsuario(usuarioCommand, authentication);
        return "redirect:/usuarios";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacionUsuario(Model model){
        model.addAttribute("usuario", new UsuarioEntity());
        return "usuarios/formulario";
    }

    @GetMapping
    public String listarUsuarios(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Model model
    ) {
        Page<UsuarioResponse> usuarios = usuarioService.listarUsuarios(page, size);
        model.addAttribute("usuarios", usuarios);
        return "usuarios/lista";
    }

    @GetMapping("/{documento}")
    public String detalleUsuario(
        @PathVariable String documento,
        Model model
    ) {
        UsuarioResponse usuario = usuarioService.obtenerUsuario(documento);
        model.addAttribute("usuario", usuario);
        return "usuarios/detalle";
    }

    @PatchMapping("/actualizar/{documento}")
    public String actualizarUsuario (
        @PathVariable String documento,
        @ModelAttribute UsuarioCommand command, 
        Model model, Authentication authentication
        ){
        usuarioService.actualizarUsuario(documento,command, authentication);
        return "redirect:/usuarios";
    }

    @GetMapping("/actualizar/{documento}")
    public String mostrarFormularioActualizacionUsuario(
        @PathVariable String documento,
        Model model
    ) {
        UsuarioResponse usuario = usuarioService.obtenerUsuario(documento);
        model.addAttribute("usuario", usuario);
        return "usuarios/actualizar";
    }

    @PatchMapping("/cambiar-estado/{documento}")
    public String cambiarEstadoUsuario(
        @PathVariable String documento,
        Authentication authentication
    ) {
        usuarioService.desactivarUsuario(documento, authentication);
        return "redirect:/usuarios";
    }

    @DeleteMapping("/eliminar/{documento}")
    public String eliminarUsuario(
        @PathVariable String documento,
        Authentication authentication
    ) {
        usuarioService.eliminarUsuario(documento, authentication);
        return "redirect:/usuarios";
    }   
    
}
