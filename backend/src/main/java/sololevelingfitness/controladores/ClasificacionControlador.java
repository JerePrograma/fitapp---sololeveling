package sololevelingfitness.controladores;


import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sololevelingfitness.entidades.Usuario;
import sololevelingfitness.repositorios.UsuarioRepositorio;

import java.util.List;

@RestController
@RequestMapping("/api/clasificacion")
public class ClasificacionControlador {

    private final UsuarioRepositorio usuarioRepositorio;

    public ClasificacionControlador(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @GetMapping
    public List<Usuario> obtenerClasificacion() {
        // Ordenar primero por nivel DESC y luego por exp DESC
        return usuarioRepositorio.findAll(
                Sort.by(Sort.Direction.DESC, "nivel")
                        .and(Sort.by(Sort.Direction.DESC, "exp"))
        );
    }
}
