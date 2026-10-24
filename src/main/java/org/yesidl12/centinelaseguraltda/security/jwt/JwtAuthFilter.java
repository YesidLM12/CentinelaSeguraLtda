package org.yesidl12.centinelaseguraltda.security.jwt;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yesidl12.centinelaseguraltda.security.UsuarioDetailsService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UsuarioDetailsService usuarioDetailsService;

    public JwtAuthFilter(JwtService jwtService, UsuarioDetailsService usuarioDetailsService) {
        this.jwtService = jwtService;
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                String username = jwtService.extraerUsername(token);

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Se consulta la base de datos en cada petición: si un usuario es desactivado,
                    // pierde el acceso de inmediato aunque el token aún sea válido
                    UserDetails usuario = usuarioDetailsService.loadUserByUsername(username);
                    
                    if(usuario.isEnabled() && usuario.isAccountNonExpired() && usuario.isAccountNonLocked() && usuario.isCredentialsNonExpired()) {
                        var auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            } catch (JwtException | UsernameNotFoundException e) {
                // Token inválido, continuar sin autenticar
            }
        }
        filterChain.doFilter(request, response);
    }
}
