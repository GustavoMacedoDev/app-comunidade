package br.com.macedo.sistemas.repository;

import br.com.macedo.sistemas.domain.entities.MinisterioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class MinisterioRepository implements PanacheRepository<MinisterioEntity> {


    public Optional<MinisterioEntity> buscaMinisterioPeloNome(String nome) {
        return find("nome", nome).firstResultOptional();
    }

    public Optional<MinisterioEntity> buscaMinisterioPelaSigla(String sigla) {
        return find("sigla", sigla).firstResultOptional();
    }
}
