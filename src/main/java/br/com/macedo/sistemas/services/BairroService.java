package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.endereco.AlteraDadosBairroDto;
import br.com.macedo.sistemas.domain.dto.endereco.CadastraBairroDto;
import br.com.macedo.sistemas.domain.dto.endereco.ListaBairroDto;
import br.com.macedo.sistemas.domain.entities.enderecos.BairroEntity;
import br.com.macedo.sistemas.domain.entities.enderecos.MunicipioEntity;
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
public class BairroService {

    @Inject
    MunicipioService municipioService;

    @Inject
    EnderecoService enderecoService;

    @Transactional
    public MensagemResposta cadastraBairro(CadastraBairroDto cadastraBairroDto) {
        var bairroEntity = new BairroEntity();
        bairroEntity.setNome(cadastraBairroDto.getNome().toUpperCase());
        bairroEntity.setMunicipio(buscaMunicipio(cadastraBairroDto.getIdMunicipio()));

        try {
            bairroEntity.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }

        return new MensagemResposta(bairroEntity.getIdBairro(), "Bairro cadastrado com sucesso");
    }

    private MunicipioEntity buscaMunicipio(Long idMunicipio) {
        return municipioService.buscaMunicipio(idMunicipio);
    }

    public List<ListaBairroDto> listaBairros() {
        List<BairroEntity> listaBairros = BairroEntity.listAll();

        List<ListaBairroDto> listaResponse = new ArrayList<>();
        for(var bairroEntity : listaBairros) {
            var listaBairroDto = new ListaBairroDto();
            listaBairroDto.setIdBairro(bairroEntity.getIdBairro());
            listaBairroDto.setNome(bairroEntity.getNome());

            listaResponse.add(listaBairroDto);
        }

        return listaResponse;

    }

    public BairroEntity buscaBairro(Long idBairro) {
        Optional<BairroEntity> bairroEntity = BairroEntity.findByIdOptional(idBairro);

        return bairroEntity.orElseThrow(() -> new ObjectNotFoundException("Bairro não encontrado"));
    }

    public ListaBairroDto listaBairroPorId(Long idBairro) {
        var bairro = buscaBairro(idBairro);

        var bairroDto = new ListaBairroDto();
        bairroDto.setIdBairro(bairro.getIdBairro());
        bairroDto.setNome(bairro.getNome());

        return bairroDto;
    }

    @Transactional
    public MensagemResposta alteraDadosBairro(Long idBairro, AlteraDadosBairroDto alteraDadosBairroDto) {
        var bairro = buscaBairro(idBairro);
        verificaVinculoComEndereco(idBairro);

        bairro.setNome(alteraDadosBairroDto.getNome().toUpperCase());
        bairro.setMunicipio(buscaMunicipio(alteraDadosBairroDto.getId()));

        try {
            bairro.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }

        return new MensagemResposta(bairro.getIdBairro(), "Bairro alterado com sucesso");
    }

    private void verificaVinculoComEndereco(Long idBairro) {
        var enderecos = enderecoService.buscaEnderecoPorIdBairro(idBairro);

        if(!enderecos.isEmpty()) {
            throw new ObjectNotFoundException("Bairro não pode ser alterado/excluido pois possui vinculo com endereço");
        }
    }

    @Transactional
    public MensagemResposta deletaBairro(Long idBairro) {
        var bairro = buscaBairro(idBairro);
        verificaVinculoComEndereco(idBairro);

        try {
            bairro.delete();
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }

        return new MensagemResposta(999L, "Bairro excluido com sucesso");
    }
}
