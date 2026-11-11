package org.yesidl12.centinelaseguraltda.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.yesidl12.centinelaseguraltda.entity.ClienteEntity;
import org.yesidl12.centinelaseguraltda.enums.Tipo_Cliente;
import org.yesidl12.centinelaseguraltda.repository.ClienteRepository;
import org.yesidl12.centinelaseguraltda.dto.Clientecommand;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;

@Service
@Slf4j 
public class ClienteService {

    private final ClienteRepository clienteRepository;
    
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    public void crearCliente(Clientecommand cliente, Authentication authentication) {
        String usuarioLogueado = authentication.getName();

        boolean existeCliente = clienteRepository.existsByDocumentoIgnoreCase(cliente.documento());
        
        if (existeCliente) {
            log.warn("Intento de crear cliente con documento duplicado: {}", cliente.documento());
            throw new RuntimeException("El cliente con documento " + cliente.documento() + " ya existe");
        }
        
        ClienteEntity clienteNuevo = ClienteEntity.builder()
            .tipo(Tipo_Cliente.valueOf(cliente.tipo()))
            .documento(cliente.documento())
            .nombreRazonSocial(cliente.nombreRazonSocial())
            .representanteLegal(cliente.representanteLegal())
            .correo(cliente.correo())
            .telefono(cliente.telefono())
            .direccion(cliente.direccion())
            .ciudad(cliente.ciudad())
            .observaciones(cliente.observaciones())
            .activo(true)
            .build();
        
        clienteRepository.save(clienteNuevo);
        log.info("Cliente creado exitosamente por {}: {}", usuarioLogueado, cliente.documento());
    }   

    public Page<Clientecommand> listarClientes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return clienteRepository.findAll(pageable).map(cliente -> new Clientecommand(
            cliente.getTipo().name(),
            cliente.getDocumento(),
            cliente.getNombreRazonSocial(),
            cliente.getRepresentanteLegal(),
            cliente.getCorreo(),
            cliente.getTelefono(),
            cliente.getDireccion(),
            cliente.getCiudad(),
            cliente.getObservaciones()
        ));
    }

    public void modificarCliente(Long id, Clientecommand cliente, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        
        boolean existeCliente = clienteRepository.existsById(id);
        
        if (!existeCliente) {
            log.warn("Intento de modificar cliente inexistente por usuario: {}", usuarioLogueado);
            throw new RuntimeException("El cliente no existe");
        }
        
        ClienteEntity clienteExistente = clienteRepository.findById(id).get();
        clienteExistente.setTipo(cliente.tipo() != null ? Tipo_Cliente.valueOf(cliente.tipo()) : clienteExistente.getTipo());
        clienteExistente.setDocumento(cliente.documento() != null ? cliente.documento() : clienteExistente.getDocumento());
        clienteExistente.setNombreRazonSocial(cliente.nombreRazonSocial() != null ? cliente.nombreRazonSocial() : clienteExistente.getNombreRazonSocial());
        clienteExistente.setRepresentanteLegal(cliente.representanteLegal() != null ? cliente.representanteLegal() : clienteExistente.getRepresentanteLegal());
        clienteExistente.setCorreo(cliente.correo() != null ? cliente.correo() : clienteExistente.getCorreo());
        clienteExistente.setTelefono(cliente.telefono() != null ? cliente.telefono() : clienteExistente.getTelefono());
        clienteExistente.setDireccion(cliente.direccion() != null ? cliente.direccion() : clienteExistente.getDireccion());
        clienteExistente.setCiudad(cliente.ciudad() != null ? cliente.ciudad() : clienteExistente.getCiudad());
        clienteExistente.setObservaciones(cliente.observaciones() != null ? cliente.observaciones() : clienteExistente.getObservaciones());
        
        clienteRepository.save(clienteExistente);
        log.info("Cliente modificado exitosamente por {}: {}", usuarioLogueado, cliente.documento());
    }

    public void eliminarCliente(Long id, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        
        boolean existeCliente = clienteRepository.existsById(id);
        
        if (!existeCliente) {
            log.warn("Intento de eliminar cliente inexistente por usuario: {}", usuarioLogueado);
            throw new RuntimeException("El cliente no existe");
        }
        
        clienteRepository.deleteById(id);
        log.info("Cliente eliminado exitosamente por {}: {}", usuarioLogueado, id);
    }

    public ClienteEntity obtenerClientePorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("El cliente no existe"));
    }
}
