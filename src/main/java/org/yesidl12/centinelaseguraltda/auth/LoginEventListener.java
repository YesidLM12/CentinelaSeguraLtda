package org.yesidl12.centinelaseguraltda.auth;

import java.time.LocalDateTime;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.yesidl12.centinelaseguraltda.repository.UsuarioRepository;

@Component 
public class LoginEventListener {
    private static final int MAX_INTENTOS = 5;
    private static final int BLOQUEO_MINUTOS = 15;

    private final UsuarioRepository usuarioRepository;

    public LoginEventListener(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @EventListener
    public void onContraseñaIncorrecta(AuthenticationFailureBadCredentialsEvent event) {
        usuarioRepository.findByNombre(event.getAuthentication().getName())
        .ifPresent(u -> {
            u.setIntentosFallidos(u.getIntentosFallidos() + 1);

            if (u.getIntentosFallidos() >= MAX_INTENTOS) {
                u.setBloqueadoHasta(LocalDateTime.now().plusMinutes(BLOQUEO_MINUTOS));
                u.setIntentosFallidos(0);
            }
            
            usuarioRepository.save(u);
        });
    }

    @EventListener
    public void onLoginExitoso(AuthenticationSuccessEvent event) {
        usuarioRepository.findByNombre(event.getAuthentication().getName())
        .ifPresent(u -> {
            u.setIntentosFallidos(0);
            u.setBloqueadoHasta(null);
            usuarioRepository.save(u);
        });
    }

}
