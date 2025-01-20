package sololevelingfitness.servicios;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sololevelingfitness.dto.MisionUsuarioDTO;
import sololevelingfitness.entidades.Mision;
import sololevelingfitness.entidades.MisionUsuario;
import sololevelingfitness.entidades.Usuario;
import sololevelingfitness.repositorios.MisionRepositorio;
import sololevelingfitness.repositorios.MisionUsuarioRepositorio;
import sololevelingfitness.repositorios.UsuarioRepositorio;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MisionServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final MisionUsuarioRepositorio misionUsuarioRepositorio;
    private final MisionRepositorio misionRepositorio;

    public MisionServicio(UsuarioRepositorio usuarioRepositorio,
                          MisionUsuarioRepositorio misionUsuarioRepositorio,
                          MisionRepositorio misionRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.misionUsuarioRepositorio = misionUsuarioRepositorio;
        this.misionRepositorio = misionRepositorio;
    }

    /**
     * Resetea las misiones de todos los usuarios cada dia a medianoche.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void reiniciarMisionesDiarias() {
        List<Usuario> todosLosUsuarios = usuarioRepositorio.findAll();
        for (Usuario usuario : todosLosUsuarios) {
            List<MisionUsuario> misionesUsuario = misionUsuarioRepositorio.findByUsuarioId(usuario.getId());
            for (MisionUsuario mu : misionesUsuario) {
                mu.setProgreso(0);
                mu.setCompletado(false);
                misionUsuarioRepositorio.save(mu);
            }
            usuario.setUltimaFechaReiniciada(LocalDateTime.now());
            usuarioRepositorio.save(usuario);
        }
    }

    /**
     * Actualiza el progreso de la mision {misionId} para el usuario {usuarioId}.
     * Otorga experiencia si se completa.
     */
    public void actualizarProgresoMision(Long usuarioId, Long misionId, int incremento) {
        Usuario usuario = usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Mision mision = misionRepositorio.findById(misionId)
                .orElseThrow(() -> new RuntimeException("Mision no encontrada"));

        MisionUsuario misionUsuario = misionUsuarioRepositorio
                .findByUsuarioIdAndMisionId(usuarioId, misionId)
                .orElseThrow(() -> new RuntimeException("No se encontro registro de esa mision para este usuario"));

        misionUsuario.setProgreso(misionUsuario.getProgreso() + incremento);

        // Si se alcanza la meta, marcar como completada
        if (misionUsuario.getProgreso() >= mision.getMeta()) {
            misionUsuario.setProgreso(mision.getMeta());
            if (!misionUsuario.isCompletado()) {
                misionUsuario.setCompletado(true);
                usuario.setExp(usuario.getExp() + mision.getExpRecompensa());
                verificarSubidaNivel(usuario);
            }
        }

        misionUsuarioRepositorio.save(misionUsuario);
        usuarioRepositorio.save(usuario);
    }

    private void verificarSubidaNivel(Usuario usuario) {
        // Ejemplo: cada nivel requiere level * 100 de EXP
        while (usuario.getExp() >= usuario.getNivel() * 100) {
            usuario.setExp(usuario.getExp() - usuario.getNivel() * 100);
            usuario.setNivel(usuario.getNivel() + 1);
        }
    }

    public List<MisionUsuarioDTO> listarMisionesDiarias(Long userId) {
        List<MisionUsuario> list = misionUsuarioRepositorio.findByUsuarioId(userId);

        // Convertir cada MisionUsuario a un DTO
        return list.stream()
                .map(mu -> new MisionUsuarioDTO(
                        mu.getMision().getId(),
                        mu.getMision().getTitulo(),
                        mu.getMision().getMeta(),
                        mu.getProgreso(),
                        mu.isCompletado()
                ))
                .toList();
    }
}
