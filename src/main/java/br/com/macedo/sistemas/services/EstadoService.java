package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.endereco.EstadoDto;
import br.com.macedo.sistemas.domain.dto.endereco.RegiaoDto;
import br.com.macedo.sistemas.domain.entities.enderecos.EstadoEntity;
import br.com.macedo.sistemas.domain.entities.enderecos.RegiaoEntity;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EstadoService {

    @Transactional
    public MensagemResposta cadastraEstados(List<EstadoDto> estados) {

        for(EstadoDto estadoDto : estados) {
            EstadoEntity estado = new EstadoEntity();
            estado.setIdEstado(estadoDto.getId());
            estado.setNome(estadoDto.getNome());
            estado.setSigla(estadoDto.getSigla());
            estado.setRegiao(buscaRegiao(estadoDto.getRegiao().getId()));

            try {
                estado.persist();
            } catch (PersistenceException e) {
                throw new PersistenceException(e.getMessage());
            }
        }

        return new MensagemResposta(999L, "Estados Cadastrados");


    }

    private RegiaoEntity buscaRegiao(Long id) {
        return RegiaoEntity.findById(id);
    }

    public List<EstadoDto> buscaEstados() {
        List<EstadoEntity> estados = EstadoEntity.listAll();

        List<EstadoDto> listaResponse = new ArrayList<>();
        for(EstadoEntity estadoEntity : estados) {
            EstadoDto estadoDto = new EstadoDto();
            estadoDto.setId(estadoEntity.getIdEstado());
            estadoDto.setNome(estadoEntity.getNome());
            estadoDto.setSigla(estadoEntity.getSigla());
            estadoDto.setRegiao(preencheRegiao(estadoEntity.getRegiao().getIdRegiao()));

            listaResponse.add(estadoDto);
        }

        return listaResponse;
    }

    private RegiaoDto preencheRegiao(Long idRegiao) {
        RegiaoEntity regiao = RegiaoEntity.findById(idRegiao);

        RegiaoDto regiaoDto = new RegiaoDto();
        regiaoDto.setId(regiao.getIdRegiao());
        regiaoDto.setNome(regiao.getNome());
        regiaoDto.setSigla(regiao.getSigla());

        return regiaoDto;
    }
}
