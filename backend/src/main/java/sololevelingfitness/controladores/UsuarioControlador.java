package sololevelingfitness.controladores;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sololevelingfitness.dto.request.ModificacionUsuarioDTO;
import sololevelingfitness.dto.request.RegistroUsuarioDTO;
import sololevelingfitness.entidades.Usuario;
import sololevelingfitness.servicios.UsuarioServicio;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    // 1. Registro
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroUsuarioDTO datosRegistro) {
        try {
            String mensaje = usuarioServicio.registrarUsuario(datosRegistro);
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor.");
        }
    }

    // 2. Obtener perfil del usuario logueado
    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerPerfil(@AuthenticationPrincipal Usuario usuario) {
        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }
        // O usar un DTO de respuesta
        return ResponseEntity.ok(usuario);
    }

    // 3. Actualizar nombre de usuario
    // Se asume un body JSON: { "nombreUsuario": "nuevoNombre" }
    @PatchMapping("/perfil")
    public ResponseEntity<?> actualizarNombreUsuario(@AuthenticationPrincipal Usuario usuario,
                                                     @RequestBody ModificacionUsuarioDTO datos) {
        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }
        if (datos.nombreUsuario().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("nuevoNombreUsuario es requerido");
        }

        try {
            usuarioServicio.actualizarNombreDeUsuario(usuario.getId(), datos.nombreUsuario());
            return ResponseEntity.ok("Nombre de usuario actualizado a: " + datos.nombreUsuario());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor.");
        }
    }

}
