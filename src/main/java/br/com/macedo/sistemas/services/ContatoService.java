package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.contato.CadastraContatoDto;
import br.com.macedo.sistemas.domain.dto.contato.ListaContatoDto;
import br.com.macedo.sistemas.domain.entities.ContatoEntity;
import br.com.macedo.sistemas.domain.entities.MembroEntity;
import br.com.macedo.sistemas.repository.ContatoRepository;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ContatoService {

    @Inject
    ContatoRepository contatoRepository;

    @Inject
    MembroService membroService;

    @Transactional
    public MensagemResposta cadastraContato(CadastraContatoDto cadastraContatoDto) {
        ContatoEntity contatoEntity = new ContatoEntity();
        contatoEntity.setTelefone(cadastraContatoDto.getTelefone());
        contatoEntity.setEmail(cadastraContatoDto.getEmail());
        contatoEntity.setMembro(buscaMembro(cadastraContatoDto.getIdMembro()));

        try {
            contatoEntity.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }

        return new MensagemResposta(contatoEntity.getIdContato(), "Contato cadastrado com sucesso");
    }

    private MembroEntity buscaMembro(Long idMembro) {
        return membroService.buscaMembroPorId(idMembro);
    }

    public List<ListaContatoDto> buscaContatoPorIdMembro(Long idMembro) {
        List<ContatoEntity> contatos = contatoRepository.buscaContatoPeloIdMembro(idMembro);

        List<ListaContatoDto> listaResponse = new ArrayList<>();
        for(ContatoEntity contatoEntity: contatos) {
            ListaContatoDto listaContatoDto = new ListaContatoDto();
            listaContatoDto.setIdContato(contatoEntity.getIdContato());
            listaContatoDto.setTelefone(contatoEntity.getTelefone());
            listaContatoDto.setEmail(contatoEntity.getEmail());

            listaResponse.add(listaContatoDto);
        }

        return listaResponse;

    }
}
