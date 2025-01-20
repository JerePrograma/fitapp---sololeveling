package sololevelingfitness.servicios;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sololevelingfitness.dto.request.RegistroUsuarioDTO;
import sololevelingfitness.entidades.Mision;
import sololevelingfitness.entidades.MisionUsuario;
import sololevelingfitness.entidades.Usuario;
import sololevelingfitness.repositorios.MisionRepositorio;
import sololevelingfitness.repositorios.MisionUsuarioRepositorio;
import sololevelingfitness.repositorios.UsuarioRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;
    private final MisionRepositorio misionRepositorio;
    private final MisionUsuarioRepositorio misionUsuarioRepositorio;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder, MisionRepositorio misionRepositorio, MisionUsuarioRepositorio misionUsuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
        this.misionRepositorio = misionRepositorio;
        this.misionUsuarioRepositorio = misionUsuarioRepositorio;
    }

    /**
     * Registra un usuario usando datos de RegistroUsuarioDTO.
     * Lanza IllegalArgumentException si el email ya existe.
     */
    public String registrarUsuario(RegistroUsuarioDTO datosRegistro) {
        Optional<Usuario> usuarioExistente = usuarioRepositorio.findByEmail(datosRegistro.email());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("El email ya está en uso.");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(datosRegistro.nombreUsuario());
        nuevoUsuario.setEmail(datosRegistro.email());
        nuevoUsuario.setContrasena(passwordEncoder.encode(datosRegistro.contrasena()));
        nuevoUsuario.setNivel(1);
        nuevoUsuario.setExp(0);

        // 1. Guardar el usuario -> para que tenga ID
        Usuario savedUser = usuarioRepositorio.save(nuevoUsuario);

        // 2. Asignar las 4 misiones diarias al usuario *ya persistido*
        asignarMisionesFijasAlUsuario(savedUser);

        return "Usuario creado exitosamente.";
    }

    private void asignarMisionesFijasAlUsuario(Usuario usuario) {
        // IDs fijos en la tabla "misiones"
        List<Long> idsFijos = List.of(1L, 2L, 3L, 4L);
        List<Mision> misiones = misionRepositorio.findAllById(idsFijos);

        for (Mision m : misiones) {
            MisionUsuario mu = new MisionUsuario();
            mu.setUsuario(usuario);
            mu.setMision(m);
            mu.setProgreso(0);
            mu.setCompletado(false);
            misionUsuarioRepositorio.save(mu);
        }
    }

    /**
     * Actualiza el nombre de usuario. Si no existe, lanza excepción.
     */
    public void actualizarNombreDeUsuario(Long idUsuario, String nuevoNombre) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        usuario.setNombreUsuario(nuevoNombre);
        usuarioRepositorio.save(usuario);
    }

    /**
     * Retorna un usuario por su id, o lanza excepción si no existe.
     */
    public Usuario obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    // Podrías tener métodos adicionales para actualizar contraseña, email, etc.
}
