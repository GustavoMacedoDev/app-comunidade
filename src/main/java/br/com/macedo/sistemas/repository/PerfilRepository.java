package br.com.macedo.sistemas.repository;

import br.com.macedo.sistemas.domain.entities.PerfilEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class PerfilRepository implements PanacheRepository<PerfilEntity> {


    public Optional<PerfilEntity> buscaPeloNome(String nome) {
        return find("nome", nome).firstResultOptional();
    }
}
