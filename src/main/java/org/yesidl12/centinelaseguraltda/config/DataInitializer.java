package org.yesidl12.centinelaseguraltda.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.yesidl12.centinelaseguraltda.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.yesidl12.centinelaseguraltda.entity.UsuarioEntity;
import org.yesidl12.centinelaseguraltda.enums.Rol;

@Component 
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return;
        }   

        var superUsuario = UsuarioEntity.builder()
                .nombre("Super Usuario")
                .documento("00000000")
                .correo("superusuario@centinela.com")
                .passwordHash(passwordEncoder.encode("password"))
                .rol(Rol.ADMINISTRADOR)
                .activo(true)
                .build();
        
        userRepository.save(superUsuario);
    }

}
