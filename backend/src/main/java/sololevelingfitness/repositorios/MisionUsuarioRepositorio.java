package sololevelingfitness.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sololevelingfitness.entidades.MisionUsuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface MisionUsuarioRepositorio extends JpaRepository<MisionUsuario, Long> {
    List<MisionUsuario> findByUsuarioId(Long usuarioId);
    Optional<MisionUsuario> findByUsuarioIdAndMisionId(Long usuarioId, Long misionId);
}