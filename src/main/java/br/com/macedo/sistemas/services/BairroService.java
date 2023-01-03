package br.com.macedo.sistemas.services;

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

    @Transactional
    public MensagemResposta cadastraBairro(CadastraBairroDto cadastraBairroDto) {
        BairroEntity bairroEntity = new BairroEntity();
        bairroEntity.setNome(cadastraBairroDto.getNome());
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
        for(BairroEntity bairroEntity : listaBairros) {
            ListaBairroDto listaBairroDto = new ListaBairroDto();
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
}
