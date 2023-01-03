package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.endereco.AlteraDadosLogradouroDto;
import br.com.macedo.sistemas.domain.dto.endereco.CadastraLogradouroDto;
import br.com.macedo.sistemas.domain.dto.endereco.ListaLogradouroDto;
import br.com.macedo.sistemas.domain.entities.enderecos.LogradouroEntity;
import br.com.macedo.sistemas.domain.entities.enderecos.TipoLogradouroEntity;
import br.com.macedo.sistemas.repository.LogradouroRepository;
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
public class LogradouroService {

    @Inject
    TipoLogradouroService tipoLogradouroService;

    @Inject
    LogradouroRepository logradouroRepository;

    @Inject
    EnderecoService enderecoService;

    @Transactional
    public MensagemResposta cadastraLogradouro(CadastraLogradouroDto cadastraLogradouroDto) {
        var logradouro = new LogradouroEntity();
        logradouro.setNome(cadastraLogradouroDto.getNome().toUpperCase());
        logradouro.setTipoLogradouro(buscaTipoLogradouro(cadastraLogradouroDto.getIdTipoLogradouro()));

        try {
            logradouro.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }

        return new MensagemResposta(logradouro.getIdLogradouro(), "Logradouro cadastrado com sucesso");
    }

    private TipoLogradouroEntity buscaTipoLogradouro(Long idTipoLogradouro) {
        return tipoLogradouroService.buscaTipoLogradouro(idTipoLogradouro);
    }

    public List<ListaLogradouroDto> listaLogradouro() {
        List<LogradouroEntity> logradouro = LogradouroEntity.listAll();

        List<ListaLogradouroDto> listaResponse = new ArrayList<>();
        for(var logradouroEntity: logradouro) {
            var listaLogradouroDto = new ListaLogradouroDto();
            listaLogradouroDto.setIdLogradouro(logradouroEntity.getIdLogradouro());
            listaLogradouroDto.setNome(logradouroEntity.getNome());

            listaResponse.add(listaLogradouroDto);
        }

        return listaResponse;
    }

    public LogradouroEntity buscaLogradouro(Long idLogradouro) {
        Optional<LogradouroEntity> logradouro = LogradouroEntity.findByIdOptional(idLogradouro);

        return logradouro.orElseThrow(() -> new ObjectNotFoundException("Logradouro não encontrado"));
    }

    public List<LogradouroEntity> buscaLogradouroPorIdTipoLogradouro(Long idTipoLogradouro) {

        return logradouroRepository.buscaLogradouroPorIdTipoLogradouro(idTipoLogradouro);
    }

    public ListaLogradouroDto buscaLogradouroPorId(Long idLogradouro) {
        var logradouro = buscaLogradouro(idLogradouro);

        var listaLogradouroDto = new ListaLogradouroDto();
        listaLogradouroDto.setIdLogradouro(logradouro.getIdLogradouro());
        listaLogradouroDto.setNome(logradouro.getNome());

        return listaLogradouroDto;

    }

    @Transactional
    public MensagemResposta alteraDadosLogradouro(Long idLogradouro, AlteraDadosLogradouroDto alteraDadosLogradouroDto) {
        var logradouro = buscaLogradouro(idLogradouro);
        verificaVinculoComEndereco(idLogradouro);

        logradouro.setNome(alteraDadosLogradouroDto.getNome().toUpperCase());
        logradouro.setTipoLogradouro(buscaTipoLogradouro(idLogradouro));

        try {
            logradouro.persist();
        } catch (PersistenceException e) {
            throw new ObjectNotFoundException(e.getMessage());
        }

        return new MensagemResposta(logradouro.getIdLogradouro(), "Logradouro alterado com sucesso");
    }

    private void verificaVinculoComEndereco(Long idLogradouro) {
        var enderecos = enderecoService.buscaEnderecosPorIdLogradouro(idLogradouro);

        if (!enderecos.isEmpty()) {
            throw new ObjectNotFoundException("Logradouro não pode ser alterado/excluido pois possui vinculo com endereço");
        }
    }

    @Transactional
    public MensagemResposta deletaLogradouro(Long idLogradouro) {
        var logradouro = buscaLogradouro(idLogradouro);
        verificaVinculoComEndereco(idLogradouro);

        try {
            logradouro.delete();
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }

        return new MensagemResposta(999L, "Logradouro deletado com sucesso");

    }
}
