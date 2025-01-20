package sololevelingfitness.controladores;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import sololevelingfitness.dto.request.AutenticacionRequest;
import sololevelingfitness.entidades.Usuario;
import sololevelingfitness.infra.seguridad.DatosJWTToken;
import sololevelingfitness.infra.seguridad.TokenService;
//import sololevelingfitness.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/api/login")
public class AutenticacionControlador {

    private final AuthenticationManager manager;

    private final TokenService tokenService;

//    private final UsuarioRepositorio usuarioRepositorio;

    public AutenticacionControlador(AuthenticationManager manager, TokenService tokenService/*, UsuarioRepositorio usuarioRepositorio*/) {
        this.manager = manager;
        this.tokenService = tokenService;
//        this.usuarioRepositorio = usuarioRepositorio;
    }

    @PostMapping
    public ResponseEntity<DatosJWTToken> realizarLogin(@RequestBody @Valid AutenticacionRequest datos) {
        var authToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasena());
        var usuarioAutenticado = manager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

/*    private Usuario cargarUsuarioPorEmail(String email) {
        UserDetails userDetails = usuarioRepositorio.findByEmail(email);
        if (userDetails == null) {
            throw new RuntimeException("Usuario no encontrado con el correo electronico: " + email);
        }
        return (Usuario) userDetails;
    }*/
}
