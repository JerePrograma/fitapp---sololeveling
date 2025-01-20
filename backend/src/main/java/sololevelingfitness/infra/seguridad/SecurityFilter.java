package sololevelingfitness.infra.seguridad;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sololevelingfitness.repositorios.UsuarioRepositorio;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepositorio usuarioRepositorio;

    public SecurityFilter(TokenService tokenService, UsuarioRepositorio usuarioRepositorio) {
        this.tokenService = tokenService;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var email = tokenService.getSubject(token); //Extrae nombre de usuario
            if (email != null) {
                //Si trae el nombre de usuario, el Token es valido
                var usuarioOpt = usuarioRepositorio.findByEmail(email);
                if (usuarioOpt.isPresent()) {
                    var userEntity = usuarioOpt.get();
                    // userEntity implementa UserDetails
                    var authentication = new UsernamePasswordAuthenticationToken(
                            userEntity,
                            null,
                            userEntity.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
