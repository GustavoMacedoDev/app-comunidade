package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.endereco.AlteraNomeTipoLogradouroDto;
import br.com.macedo.sistemas.domain.dto.endereco.CadastraTipoLogradouroDto;
import br.com.macedo.sistemas.domain.dto.endereco.TipoLogradouroDto;
import br.com.macedo.sistemas.domain.entities.enderecos.TipoLogradouroEntity;
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
public class TipoLogradouroService {

    @Inject
    LogradouroService logradouroService;

    @Transactional
    public MensagemResposta cadastraTipoLogradouro(CadastraTipoLogradouroDto cadastraTipoLogradouroDto) {
        TipoLogradouroEntity tipoLogradouro = new TipoLogradouroEntity();
        tipoLogradouro.setNome(cadastraTipoLogradouroDto.getNome().toUpperCase());

        try {
            tipoLogradouro.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }

        return new MensagemResposta(tipoLogradouro.getIdTipoLogradouro(),
                "Tipo de Logradouro cadastrado com sucesso");
    }

    public List<TipoLogradouroDto> listaTipoLogradouro() {
        List<TipoLogradouroEntity> tipoLogradouros = TipoLogradouroEntity.listAll();

        List<TipoLogradouroDto> listaResponse = new ArrayList<>();
        for(TipoLogradouroEntity tipoLogradouro : tipoLogradouros) {
            TipoLogradouroDto tipoLogradouroDto = new TipoLogradouroDto();
            tipoLogradouroDto.setIdTipoLogradouro(tipoLogradouro.getIdTipoLogradouro());
            tipoLogradouroDto.setNome(tipoLogradouro.getNome());

            listaResponse.add(tipoLogradouroDto);
        }

        return listaResponse;

    }

    public TipoLogradouroEntity buscaTipoLogradouro(Long idTipoLogradouro) {
        Optional<TipoLogradouroEntity> tipoLogradouro = TipoLogradouroEntity.findByIdOptional(idTipoLogradouro);

        return tipoLogradouro.orElseThrow(() -> new ObjectNotFoundException("Tipo Logradouro não encontrado"));
    }

    public TipoLogradouroDto buscaTipoLogradouroPorId(Long idTipoLogradouro) {
        var logradouroEntity = buscaTipoLogradouro(idTipoLogradouro);

        var tipoLogradouroDto = new TipoLogradouroDto();
        tipoLogradouroDto.setIdTipoLogradouro(logradouroEntity.getIdTipoLogradouro());
        tipoLogradouroDto.setNome(logradouroEntity.getNome());

        return tipoLogradouroDto;

    }

    @Transactional
    public MensagemResposta alteraNomeTipoLogradouro(Long idTipoLogradouro, AlteraNomeTipoLogradouroDto alteraNomeTipoLogradouro) {
        var tipoLogradouro = buscaTipoLogradouro(idTipoLogradouro);
        verificarVinculoComLogradouro(idTipoLogradouro);

        tipoLogradouro.setNome(alteraNomeTipoLogradouro.getNome().toUpperCase());

        try {
            tipoLogradouro.persist();
        } catch (PersistenceException e){
            throw new PersistenceException(e.getMessage());
        }

        return new MensagemResposta(tipoLogradouro.getIdTipoLogradouro(), "Tipo de Logradouro alterado com sucesso");
    }

    private void verificarVinculoComLogradouro(Long idTipoLogradouro) {
        var logradouros = logradouroService.buscaLogradouroPorIdTipoLogradouro(idTipoLogradouro);

        if(!logradouros.isEmpty()) {
            throw new ObjectNotFoundException("Tipo de Logradouro não pode ser alterado/exclúido pois possui vínculos com logradouro");
        }
    }

    public MensagemResposta deletaTipoLogradouroSemVinculos(Long idTipoLogradouro) {
        var tipoLogradouro = buscaTipoLogradouro(idTipoLogradouro);
        verificarVinculoComLogradouro(idTipoLogradouro);

        try {
            tipoLogradouro.delete();
        } catch (PersistenceException e) {
            throw new ObjectNotFoundException(e.getMessage());
        }

        return new MensagemResposta(tipoLogradouro.getIdTipoLogradouro(), "Tipo de logradouro deletado com sucesso");
    }
}
