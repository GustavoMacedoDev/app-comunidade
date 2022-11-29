package br.com.macedo.sistemas.repository;

import br.com.macedo.sistemas.domain.entities.CargoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class CargoRepository implements PanacheRepository<CargoEntity> {


    public Optional<CargoEntity> buscaCargoPeloNome(String nome) {
        return find("nome", nome).firstResultOptional();
    }
}
