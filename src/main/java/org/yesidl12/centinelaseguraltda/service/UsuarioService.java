package org.yesidl12.centinelaseguraltda.service;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.yesidl12.centinelaseguraltda.repository.UsuarioRepository;
import org.yesidl12.centinelaseguraltda.dto.UsuarioCommand;
import org.yesidl12.centinelaseguraltda.dto.UsuarioResponse;
import org.yesidl12.centinelaseguraltda.entity.UsuarioEntity;
import org.yesidl12.centinelaseguraltda.enums.Rol;
import org.yesidl12.centinelaseguraltda.exception.UsuarioConflictException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UsuarioEntity crearUsuario(UsuarioCommand command, Authentication authentication) {
        String usuarioLogueado = authentication.getName();

        boolean existeUsuario = usuarioRepository.findByDocumento(command.documento()).isPresent();
        
        if (existeUsuario) {
            log.warn("Intento de crear usuario duplicado por {}: {}", usuarioLogueado, command.documento());
            throw new UsuarioConflictException("El usuario ya existe");
        }

        var usuarioNuevo = UsuarioEntity.builder()
        .documento(command.documento())
        .nombre(command.nombre())
        .apellido(command.apellido())
        .correo(command.correo())
        .rol(Rol.valueOf(command.rol().toUpperCase()))
        .activo(true)
        .passwordHash(passwordEncoder.encode(command.password()))
        .debeCambiarPassword(true)
        .build();
        
        
        log.info("Usuario creado exitosamente por {}: {}", usuarioLogueado, command.documento());
        return usuarioRepository.save(usuarioNuevo);
    }

    public Page<UsuarioResponse> listarUsuarios(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UsuarioEntity> usuarios = usuarioRepository.findAll(pageable);
        
        return usuarios.map(u -> new UsuarioResponse(
            u.getDocumento(),
            u.getNombre(),
            u.getApellido(),
            u.getCorreo(),
            u.getRol().name(),
            u.isActivo()
        ));
    }

    public UsuarioEntity obtenerUsuario(String documento) {
        return usuarioRepository.findByDocumento(documento).get();
    }

    public UsuarioResponse obtenerUsuarioResponse(String documento) {
        UsuarioEntity usuario = usuarioRepository.findByDocumento(documento).get();
        return new UsuarioResponse(
            usuario.getDocumento(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getCorreo(),
            usuario.getRol().name(),
            usuario.isActivo()
        );
    }

    public UsuarioEntity actualizarUsuario(String documento, UsuarioCommand command, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        
        boolean existeUsuario = usuarioRepository.findByDocumento(documento).isPresent();
        
        if (!existeUsuario) {
            log.warn("Intento de actualizar usuario inexistente por {}: {}", usuarioLogueado, documento);
            throw new UsuarioConflictException("El usuario no existe");
        }

        UsuarioEntity usuario = usuarioRepository.findByDocumento(documento).get();
        usuario.setNombre(command.nombre() != null ? command.nombre() : usuario.getNombre());
        usuario.setApellido(command.apellido() != null ? command.apellido() : usuario.getApellido());
        usuario.setCorreo(command.correo() != null ? command.correo() : usuario.getCorreo());
        usuario.setRol(command.rol() != null ? Rol.valueOf(command.rol()) : usuario.getRol());
        usuarioRepository.save(usuario);
        
        log.info("Usuario actualizado por {}: {}", usuarioLogueado, documento);
        return usuario;
    }

    public UsuarioEntity desactivarUsuario(String documento, Authentication authentication) {
        String usuarioLogueado = authentication.getName();
        
        boolean existeUsuario = usuarioRepository.findByDocumento(documento).isPresent();
        
        if (!existeUsuario) {
            log.warn("Intento de desactivar usuario inexistente por {}: {}", usuarioLogueado, documento);
            throw new UsuarioConflictException("El usuario no existe");
        }

        UsuarioEntity usuario = usuarioRepository.findByDocumento(documento).get();
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
        
        log.info("Usuario desactivado por {}: {}", usuarioLogueado, documento);
        return usuario;
    }

    // TODO => Restringir que un admin se puede elminar a el mismo
    public void eliminarUsuario(String documento, Authentication authentication) {
        String usuarioLogueado = authentication.getPrincipal().getClass().getName();
        
        boolean existeUsuario = usuarioRepository.findByDocumento(documento).isPresent();
        
        if (!existeUsuario) {
            log.warn("Intento de eliminar usuario inexistente por {}: {}", usuarioLogueado, documento);
            throw new UsuarioConflictException("El usuario no existe");
        }

        UsuarioEntity usuario = usuarioRepository.findByDocumento(documento).get();

        if (usuario.getRol() == Rol.ADMINISTRADOR) {
            log.warn("Intento de eliminar usuario admin por {}: {}", usuarioLogueado, documento);
            throw new UsuarioConflictException("No se puede eliminar un usuario admin");
        }

        usuarioRepository.delete(usuario);
        log.info("Usuario eliminado por {}: {}", usuarioLogueado, documento);
    }
}
