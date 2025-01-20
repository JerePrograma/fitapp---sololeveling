package sololevelingfitness.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreUsuario;   // O "username"
    private String email;
    private String contrasena;      // Hasheada con BCrypt
    private int nivel;
    private int exp;

    private LocalDateTime ultimaFechaReiniciada;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    // Por convención, si inicias sesión con "nombreUsuario":
    @Override
    public String getUsername() {
        return nombreUsuario;
    }
    // o si usas "email" para loguear, entonces retorna email

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Lógica real si lo requieres
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Lógica real si lo requieres
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Lógica real si lo requieres
    }

    @Override
    public boolean isEnabled() {
        return true;  // Lógica real si lo requieres
    }
}
