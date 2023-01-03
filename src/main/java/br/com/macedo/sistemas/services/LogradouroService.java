package br.com.macedo.sistemas.services;

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

    @Transactional
    public MensagemResposta cadastraLogradouro(CadastraLogradouroDto cadastraLogradouroDto) {
        LogradouroEntity logradouro = new LogradouroEntity();
        logradouro.setNome(cadastraLogradouroDto.getNome());
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
        for(LogradouroEntity logradouroEntity: logradouro) {
            ListaLogradouroDto listaLogradouroDto = new ListaLogradouroDto();
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
        List<LogradouroEntity> logradouros = logradouroRepository.buscaLogradouroPorIdTipoLogradouro(idTipoLogradouro);

        return logradouros;
    }
}
