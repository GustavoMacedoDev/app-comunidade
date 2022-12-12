package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.ministerio.CadastraMinisterioDto;
import br.com.macedo.sistemas.domain.dto.ministerio.EditaDadosMinisterioDto;
import br.com.macedo.sistemas.domain.dto.ministerio.ListaMinisterioDto;
import br.com.macedo.sistemas.domain.entities.MinisterioEntity;
import br.com.macedo.sistemas.repository.MinisterioRepository;
import br.com.macedo.sistemas.utils.exceptions.ObjectNotFoundException;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MinisterioService {

    @Inject
    MinisterioRepository ministerioRepository;

    @Transactional
    public MensagemResposta cadastraMinisterio(CadastraMinisterioDto cadastraMinisterioDto) {
        buscaMinisterioPeloNome(cadastraMinisterioDto.getNome());
        buscaMinisterioPelaSigla(cadastraMinisterioDto.getSigla());

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

    private void buscaMinisterioPelaSigla(String sigla) {
        Optional<MinisterioEntity> ministerio = ministerioRepository.buscaMinisterioPelaSigla(sigla);

        if(ministerio.isPresent()){
            throw new ObjectNotFoundException("Sigla já cadastrada para o " + ministerio.get().getNome());
        }
    }

    private void buscaMinisterioPeloNome(String nome) {
        Optional<MinisterioEntity> ministerio = ministerioRepository.buscaMinisterioPeloNome(nome);

        if (ministerio.isPresent()){
            throw new ObjectNotFoundException("Ministério já cadastrado");
        }
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

    public ListaMinisterioDto listaMinisterioPorId(Long idMinisterio) {
        MinisterioEntity ministerioEntity = buscaMinisterioPorId(idMinisterio);

        return new ListaMinisterioDto(
                ministerioEntity.getIdMinisterio(),
                ministerioEntity.getNome(),
                ministerioEntity.getSigla(),
                ministerioEntity.getDataCriacao()
                );

    }

    @Transactional
    public MensagemResposta editaDadosMinisterio(EditaDadosMinisterioDto editaDadosMinisterioDto, Long idMinisterio) {
        MinisterioEntity ministerioEntity = buscaMinisterioPorId(idMinisterio);

        ministerioEntity.setNome(editaDadosMinisterioDto.getNome());
        ministerioEntity.setSigla(editaDadosMinisterioDto.getSigla());
        ministerioEntity.setDataCriacao(editaDadosMinisterioDto.getDataCriacao());

        try {
            ministerioEntity.persistAndFlush();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao executar a transação " + e.getMessage());
        }

        return new MensagemResposta(ministerioEntity.getIdMinisterio(), "Dados alterados com sucesso");
    }

    @Transactional
    public MensagemResposta deletaMinisterio(Long idMinisterio) {
        MinisterioEntity ministerioEntity = buscaMinisterioPorId(idMinisterio);

        try {
            ministerioEntity.delete();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao executar a transação");
        }

        return new MensagemResposta(999L, "Ministerio deletado com sucesso");
    }
}
