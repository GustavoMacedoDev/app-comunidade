package br.com.macedo.sistemas.repository;

import br.com.macedo.sistemas.domain.entities.ContatoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ContatoRepository implements PanacheRepository<ContatoEntity> {

    public List<ContatoEntity> buscaContatoPeloIdMembro(Long id) {
        return find("membro.idMembro", id).list();
    }
}
