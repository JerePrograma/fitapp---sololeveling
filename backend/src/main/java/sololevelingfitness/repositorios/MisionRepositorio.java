package sololevelingfitness.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sololevelingfitness.entidades.Mision;

@Repository
public interface MisionRepositorio extends JpaRepository<Mision, Long> {
    // Misiones fijas o personalizadas
}
