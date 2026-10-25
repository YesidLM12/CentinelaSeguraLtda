package org.yesidl12.centinelaseguraltda.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.yesidl12.centinelaseguraltda.dto.UsuarioCommand;
import org.yesidl12.centinelaseguraltda.dto.UsuarioResponse;
import org.yesidl12.centinelaseguraltda.entity.UsuarioEntity;
import org.yesidl12.centinelaseguraltda.service.UsuarioService;

@Controller("/usuarios")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/crear")
    public String crearUsuario(
        @RequestBody UsuarioCommand usuarioCommand, 
        Authentication authentication, 
        Model model
    ) {
        UsuarioEntity usuario = usuarioService.crearUsuario(usuarioCommand, authentication);
        model.addAttribute("usuario", usuario);
        return "usuarios/crear";
    }

    @GetMapping("/lista")
    public String listarUsuarios(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Model model
    ) {
        Page<UsuarioResponse> usuarios = usuarioService.listarUsuarios(page, size);
        model.addAttribute("usuarios", usuarios);
        return "usuarios/lista";
    }
    
}
