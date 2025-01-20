package sololevelingfitness.infra.seguridad;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sololevelingfitness.repositorios.UsuarioRepositorio;

@Service
public class AutenticacionService implements UserDetailsService {

    private final UsuarioRepositorio repositorio;

    public AutenticacionService(UsuarioRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // El repositorio retorna Optional<Usuario>
        return repositorio.findByEmail(email)
                // El repositorio da la entidad
                .map(usuario -> (UserDetails) usuario)
                // Casteas a UserDetails (porque Usuario implementa UserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró un usuario con el email: " + email));
    }


}
