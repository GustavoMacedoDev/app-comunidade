package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.endereco.CadastraEnderecoDto;
import br.com.macedo.sistemas.domain.dto.endereco.ListaEnderecoDto;
import br.com.macedo.sistemas.domain.entities.MembroEntity;
import br.com.macedo.sistemas.domain.entities.enderecos.BairroEntity;
import br.com.macedo.sistemas.domain.entities.enderecos.EnderecoEntity;
import br.com.macedo.sistemas.domain.entities.enderecos.LogradouroEntity;
import br.com.macedo.sistemas.repository.EnderecoRepository;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EnderecoService {

    @Inject
    BairroService bairroService;

    @Inject
    LogradouroService logradouroService;

    @Inject
    MembroService membroService;

    @Inject
    EnderecoRepository enderecoRepository;

    @Transactional
    public MensagemResposta cadastraEndereco(CadastraEnderecoDto cadastraEnderecoDto) {
        EnderecoEntity enderecoEntity = new EnderecoEntity();
        enderecoEntity.setBairro(buscaBairro(cadastraEnderecoDto.getIdBairro()));
        enderecoEntity.setComplemento(cadastraEnderecoDto.getComplemento());
        enderecoEntity.setCep(cadastraEnderecoDto.getCep());
        enderecoEntity.setNumero(cadastraEnderecoDto.getNumero());
        enderecoEntity.setLogradouro(buscaLogradouro(cadastraEnderecoDto.getIdLogradouro()));
        enderecoEntity.setMembro(buscaMembro(cadastraEnderecoDto.getIdMembro()));

        try {
            enderecoEntity.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }

        return new MensagemResposta(enderecoEntity.getIdEndereco(), "Endereço cadastrado com sucesso");
    }

    private MembroEntity buscaMembro(Long idMembro) {
        return membroService.buscaMembroPorId(idMembro);
    }

    private LogradouroEntity buscaLogradouro(Long idLogradouro) {
        return logradouroService.buscaLogradouro(idLogradouro);
    }

    private BairroEntity buscaBairro(Long idBairro) {
        return bairroService.buscaBairro(idBairro);
    }

    public List<ListaEnderecoDto> buscaEnderecoPorIdMembro(Long idMembro) {
        List<EnderecoEntity> enderecos = enderecoRepository.buscaEnderecoPorIdMembro(idMembro);

        List<ListaEnderecoDto> listaResponse = new ArrayList<>();
        for(EnderecoEntity endereco: enderecos) {
            ListaEnderecoDto listaEnderecoDto = new ListaEnderecoDto();
            listaEnderecoDto.setIdEndereco(endereco.getIdEndereco());
            listaEnderecoDto.setEstado(endereco.getBairro().getMunicipio().getEstado().getNome());
            listaEnderecoDto.setSiglaEstado(endereco.getBairro().getMunicipio().getEstado().getSigla());
            listaEnderecoDto.setMunicipio(endereco.getBairro().getMunicipio().getNome());
            listaEnderecoDto.setComplemento(endereco.getComplemento());
            listaEnderecoDto.setCep(endereco.getCep());
            listaEnderecoDto.setLogradouro(endereco.getLogradouro().getNome());
            listaEnderecoDto.setNumero(endereco.getNumero());
            listaEnderecoDto.setTipoLogradouro(endereco.getLogradouro().getTipoLogradouro().getNome());
            listaEnderecoDto.setBairro(endereco.getBairro().getNome());

            listaResponse.add(listaEnderecoDto);
        }

        return listaResponse;
    }
}
