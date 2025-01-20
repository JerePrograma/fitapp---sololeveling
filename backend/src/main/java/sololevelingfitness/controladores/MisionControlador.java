package sololevelingfitness.controladores;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sololevelingfitness.dto.MisionUsuarioDTO;
import sololevelingfitness.entidades.MisionUsuario;
import sololevelingfitness.entidades.Usuario;
import sololevelingfitness.servicios.MisionServicio;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/misiones")
public class MisionControlador {

    private final MisionServicio misionServicio; // si renombraste la clase

    public MisionControlador(MisionServicio misionServicio) {
        this.misionServicio = misionServicio;
    }

    @PostMapping("/{misionId}/progreso")
    public ResponseEntity<?> actualizarProgreso(
            @PathVariable Long misionId,
            @RequestBody Map<String, Integer> body,
            @AuthenticationPrincipal Usuario usuario
    ) {
        int incremento = body.get("incremento");
        misionServicio.actualizarProgresoMision(usuario.getId(), misionId, incremento);
        return ResponseEntity.ok("Progreso actualizado");
    }

    @GetMapping("/diarias")
    public ResponseEntity<?> listarMisionesUsuario(@AuthenticationPrincipal Usuario usuario) {
        if (usuario == null) {
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }

        List<MisionUsuarioDTO> dtos = misionServicio.listarMisionesDiarias(usuario.getId());
        return ResponseEntity.ok(dtos);
    }
}
