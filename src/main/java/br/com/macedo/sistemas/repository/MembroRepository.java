package br.com.macedo.sistemas.repository;

import br.com.macedo.sistemas.domain.entities.MembroEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MembroRepository implements PanacheRepository<MembroEntity> {


    public Optional<MembroEntity> buscaMembroPorCpf(String cpf) {
        return find("cpf", cpf).firstResultOptional();
    }

    public List<MembroEntity> buscaMembrosPorIdCargo(Long idCargo) {
        return find("cargo.idCargo", idCargo).list();
    }
}
