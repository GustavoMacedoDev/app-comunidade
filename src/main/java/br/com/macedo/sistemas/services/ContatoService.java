package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.entities.ContatoEntity;
import br.com.macedo.sistemas.repository.ContatoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ContatoService {

    @Inject
    ContatoRepository contatoRepository;


    public List<ContatoEntity> buscaContatosPorIdMembro(Long id) {
        return contatoRepository.buscaContatoPeloIdMembro(id);

    }
}
