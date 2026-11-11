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
import org.yesidl12.centinelaseguraltda.dto.Clientecommand;
import org.yesidl12.centinelaseguraltda.entity.ClienteEntity;
import org.yesidl12.centinelaseguraltda.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @PostMapping
    public String crearCliente(@ModelAttribute Clientecommand cliente, Authentication authentication) {
        clienteService.crearCliente(cliente, authentication);
        return "redirect:/cliente";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("cliente", new ClienteEntity());
        return "cliente/crear";
    }

    @GetMapping
    public String listarClientes(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model
    ) {
        Page<Clientecommand> clientes = clienteService.listarClientes(page, size);
        model.addAttribute("clientes", clientes);
        return "clientes/listar";
    }

    @PatchMapping("/{id}")
    public String actualizarCliente(@PathVariable Long id, @ModelAttribute Clientecommand cliente, Authentication authentication) {
        clienteService.modificarCliente(id, cliente, authentication);
        return "redirect:/cliente";
    }

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioActualizar(@PathVariable Long id, Model model) {
        ClienteEntity cliente = clienteService.obtenerClientePorId(id);
        model.addAttribute("cliente", cliente);
        return "cliente/actualizar";
    }
}
