package sololevelingfitness.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "misiones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;      // "10 Squats", "1 km Run", etc.
    private int meta;           // p. ej. 20 repeticiones
    private int expRecompensa;  // Cuánta EXP da la misión
    private String tipo;        // "sentadillas", "abdominales", etc.
}
