package br.com.macedo.sistemas.repository;

import br.com.macedo.sistemas.domain.entities.enderecos.LogradouroEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class LogradouroRepository implements PanacheRepository<LogradouroEntity> {

    public List<LogradouroEntity> buscaLogradouroPorIdTipoLogradouro(Long idTipoLogradouro) {
        return find("tipoLogradouro.idTipoLogradouro", idTipoLogradouro).list();
    }
}
