package br.com.macedo.sistemas.repository;

import br.com.macedo.sistemas.domain.entities.UsuarioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<UsuarioEntity> {


    public Optional<UsuarioEntity> buscaUsuarioPeloEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
