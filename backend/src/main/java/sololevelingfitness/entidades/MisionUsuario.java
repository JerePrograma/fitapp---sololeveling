package sololevelingfitness.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "misiones_usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MisionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mision_id")
    private Mision mision;

    private int progreso = 0;
    private boolean completado = false;
}
