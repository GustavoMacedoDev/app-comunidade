package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.endereco.EstadoDto;
import br.com.macedo.sistemas.domain.dto.endereco.MunicipioDto;
import br.com.macedo.sistemas.domain.entities.enderecos.EstadoEntity;
import br.com.macedo.sistemas.domain.entities.enderecos.MunicipioEntity;
import br.com.macedo.sistemas.utils.exceptions.ObjectNotFoundException;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MunicipioService {

    @Transactional
    public MensagemResposta cadastraMunicipios(List<MunicipioDto> municipios) {

        for(MunicipioDto municipioDto : municipios) {
            MunicipioEntity municipio = new MunicipioEntity();
            municipio.setIdMunicipio(municipioDto.getId());
            municipio.setNome(municipioDto.getNome());
            municipio.setEstado(buscaEstado(municipioDto.getMicrorregiao().getMesorregiao().getUF().getId()));

            try {
                municipio.persist();
            } catch (PersistenceException e) {
                throw new PersistenceException(e.getMessage());
            }
        }

        return new MensagemResposta(999L, "Municipios cadastrados");
    }

    private EstadoEntity buscaEstado(Long id) {
        return EstadoEntity.findById(id);
    }

    public List<MunicipioDto> buscaMunicipios() {
        List<MunicipioEntity> municipios = MunicipioEntity.listAll();

        List<MunicipioDto> listaResponse = new ArrayList<>();
        for(MunicipioEntity municipio : municipios) {
            MunicipioDto municipioDto = new MunicipioDto();
            municipioDto.setId(municipio.getIdMunicipio());
            municipioDto.setNome(municipio.getNome());

            listaResponse.add(municipioDto);
        }

        return listaResponse;
    }

    public MunicipioEntity buscaMunicipio(Long idMunicipio) {
        Optional<MunicipioEntity> municipio = MunicipioEntity.findByIdOptional(idMunicipio);

        return municipio.orElseThrow(() -> new ObjectNotFoundException("Municipio não encontrado"));
    }
}
