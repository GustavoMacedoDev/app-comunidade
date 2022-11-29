package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.ministerio.CadastraMinisterioDto;
import br.com.macedo.sistemas.domain.dto.ministerio.ListaMinisterioDto;
import br.com.macedo.sistemas.domain.entities.MinisterioEntity;
import br.com.macedo.sistemas.utils.exceptions.ObjectNotFoundException;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MinisterioService {

    @Transactional
    public MensagemResposta cadastraMinisterio(CadastraMinisterioDto cadastraMinisterioDto) {
        MinisterioEntity ministerioEntity = new MinisterioEntity();
        ministerioEntity.setNome(cadastraMinisterioDto.getNome());
        ministerioEntity.setSigla(cadastraMinisterioDto.getSigla());
        ministerioEntity.setDataCriacao(cadastraMinisterioDto.getDataCriacao());

        try {
            ministerioEntity.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao executar a transação" + e.getMessage());
        }

        return new MensagemResposta(ministerioEntity.getIdMinisterio(), "Ministério cadastrado com sucesso");

    }

    public MinisterioEntity buscaMinisterioPorId(Long idMinisterio) {
        Optional<MinisterioEntity> ministerioEntity = MinisterioEntity.findByIdOptional(idMinisterio);

        return ministerioEntity.orElseThrow(() -> new ObjectNotFoundException("Ministério não encontrado"));
    }

    public List<ListaMinisterioDto> listaMinisterios() {
        List<MinisterioEntity> listaMinisterios = MinisterioEntity.listAll();

        List<ListaMinisterioDto> listaResponse = new ArrayList<>();
        for(MinisterioEntity ministerioEntity: listaMinisterios) {
            ListaMinisterioDto listaMinisterioDto = new ListaMinisterioDto();
            listaMinisterioDto.setIdMinisterio(ministerioEntity.getIdMinisterio());
            listaMinisterioDto.setNome(ministerioEntity.getNome());
            listaMinisterioDto.setSigla(ministerioEntity.getSigla());
            listaMinisterioDto.setDataCriacao(ministerioEntity.getDataCriacao());

            listaResponse.add(listaMinisterioDto);
        }

        return listaResponse;
    }
}
