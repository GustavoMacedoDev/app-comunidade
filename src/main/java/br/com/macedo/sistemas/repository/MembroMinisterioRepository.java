package br.com.macedo.sistemas.repository;

import br.com.macedo.sistemas.domain.entities.MembroMinisterioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MembroMinisterioRepository implements PanacheRepository<MembroMinisterioEntity> {


    public List<MembroMinisterioEntity> listaMembroPorMinisterio(Long idMinisterio) {
        return find("ministerio.idMinisterio", idMinisterio).list();
    }
}
