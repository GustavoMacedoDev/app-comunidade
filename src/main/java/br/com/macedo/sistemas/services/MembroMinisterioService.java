package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.cargo.ListaCargosDto;
import br.com.macedo.sistemas.domain.dto.contato.ListaContatoDto;
import br.com.macedo.sistemas.domain.dto.membro.ListaMembroDto;
import br.com.macedo.sistemas.domain.dto.membroMinisterio.CadastraMembroMinisterioDto;
import br.com.macedo.sistemas.domain.dto.membroMinisterio.ListaMembroPorMinisterioDto;
import br.com.macedo.sistemas.domain.dto.perfil.ListaPerfilDto;
import br.com.macedo.sistemas.domain.entities.CargoEntity;
import br.com.macedo.sistemas.domain.entities.ContatoEntity;
import br.com.macedo.sistemas.domain.entities.MembroEntity;
import br.com.macedo.sistemas.domain.entities.MembroMinisterioEntity;
import br.com.macedo.sistemas.domain.entities.MinisterioEntity;
import br.com.macedo.sistemas.domain.entities.PerfilEntity;
import br.com.macedo.sistemas.repository.MembroMinisterioRepository;
import br.com.macedo.sistemas.utils.exceptions.ObjectNotFoundException;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MembroMinisterioService {

    private final static Logger LOGGER = Logger.getLogger(MembroMinisterioEntity.class);

    @Inject
    MinisterioService ministerioService;

    @Inject
    MembroService membroService;

    @Inject
    PerfilService perfilService;

    @Inject
    CargoService cargoService;

    @Inject
    ContatoService contatoService;

    @Inject
    MembroMinisterioRepository membroMinisterioRepository;

    @Transactional
    public MensagemResposta cadastraMembroMinisterio(CadastraMembroMinisterioDto cadastraMembroMinisterioDto) {
        MinisterioEntity ministerioEntity = buscaMinisterio(cadastraMembroMinisterioDto.getIdMinisterio());
        buscaMembro(cadastraMembroMinisterioDto.getIdMembro());

        validaVinculoMembroMinisterio(cadastraMembroMinisterioDto.getIdMinisterio(), cadastraMembroMinisterioDto.getIdMembro());

        MembroMinisterioEntity membroMinisterio = new MembroMinisterioEntity();
        membroMinisterio.setDataEntrada(cadastraMembroMinisterioDto.getDataEntrada());
        membroMinisterio.setResponsavel(cadastraMembroMinisterioDto.getResponsavel());
        membroMinisterio.setMembro(buscaMembro(cadastraMembroMinisterioDto.getIdMembro()));
        membroMinisterio.setMinisterio(ministerioEntity);
        membroMinisterio.setPerfil(buscaPerfil(cadastraMembroMinisterioDto.getIdPerfil()));


        try {
            membroMinisterio.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao executar transação" + e.getMessage());
        }

        return new MensagemResposta(membroMinisterio.getIdMembroMinisterio(),
                "Membro cadastrado com sucesso ao ministério");

    }

    private void validaVinculoMembroMinisterio(Long idMinisterio, Long idMembro) {
        Optional<MembroMinisterioEntity> membroMinisterio = membroMinisterioRepository.buscaMembroMinisterio(idMinisterio, idMembro);

        if(membroMinisterio.isPresent()) {
            throw new ObjectNotFoundException("Membro já vinculado ao ministério");
        }
    }

    private PerfilEntity buscaPerfil(Long idPerfil) {
        return perfilService.buscaPerfilPorId(idPerfil);
    }

    private MembroEntity buscaMembro(Long idMembro) {
        return membroService.buscaMembroPorId(idMembro);
    }

    private MinisterioEntity buscaMinisterio(Long idMinisterio) {
        return ministerioService.buscaMinisterioPorId(idMinisterio);
    }

    public List<ListaMembroPorMinisterioDto> listaMembroPorMinisterio(Long idMinisterio) {
        List<MembroMinisterioEntity> listaMembroPorMinisterio =
                membroMinisterioRepository.listaMembroPorMinisterio(idMinisterio);

        List<ListaMembroPorMinisterioDto> listaMembroPorMinisterioResponse = new ArrayList<>();
        for(MembroMinisterioEntity membroMinisterio : listaMembroPorMinisterio) {
            ListaMembroPorMinisterioDto listaMembroPorMinisterioDto = new ListaMembroPorMinisterioDto();
            listaMembroPorMinisterioDto.setIdMembroMinisterio(membroMinisterio.getIdMembroMinisterio());
            listaMembroPorMinisterioDto.setDataEntrada(membroMinisterio.getDataEntrada());
            listaMembroPorMinisterioDto.setResponsavel(membroMinisterio.getResponsavel());
            listaMembroPorMinisterioDto.setMembro(preencheListaMembros(membroMinisterio.getMembro()));
            listaMembroPorMinisterioDto.setPerfilMembro(preencheListaPerfilMembro(membroMinisterio.getPerfil()));

            listaMembroPorMinisterioResponse.add(listaMembroPorMinisterioDto);
        }

        return listaMembroPorMinisterioResponse;
    }

    private ListaPerfilDto preencheListaPerfilMembro(PerfilEntity perfil) {
        return new ListaPerfilDto(perfil.getIdPerfil(), perfil.getNome());
    }

    private ListaMembroDto preencheListaMembros(MembroEntity membro) {
        ListaMembroDto listaMembroDto = new ListaMembroDto();
        listaMembroDto.setIdMembro(membro.getIdMembro());
        listaMembroDto.setNome(membro.getNome());
        listaMembroDto.setCpf(membro.getCpf());
        listaMembroDto.setEndereco(membro.getEndereco());
        listaMembroDto.setDataNascimento(membro.getDataNascimento());
        listaMembroDto.setCargo(buscaCargos(membro.getCargo().getIdCargo()));
        listaMembroDto.setContatos(buscaContatosPeloIdMembro(membro.getIdMembro()));

        return listaMembroDto;
    }

    private List<ListaContatoDto> buscaContatosPeloIdMembro(Long idMembro) {
        List<ContatoEntity> listaContatos = contatoService.buscaContatosPorIdMembro(idMembro);

        List<ListaContatoDto> listaContatosResponse = new ArrayList<>();
        for(ContatoEntity contatoEntity : listaContatos) {
            ListaContatoDto listaContatoDto = new ListaContatoDto();
            listaContatoDto.setIdContato(contatoEntity.getIdContato());
            listaContatoDto.setEmail(contatoEntity.getEmail());
            listaContatoDto.setTelefone(contatoEntity.getTelefone());

            listaContatosResponse.add(listaContatoDto);
        }

        return listaContatosResponse;
    }

    private ListaCargosDto buscaCargos(Long idCargo) {
        CargoEntity cargoEntity = cargoService.buscaCargoPeloId(idCargo);

        return new ListaCargosDto(cargoEntity.getIdCargo(), cargoEntity.getNome());
    }


    public List<MembroMinisterioEntity> buscaMembrosPorMinisterioId(Long idMinisterio) {

        return membroMinisterioRepository.listaMembroPorMinisterio(idMinisterio);
    }

    public Optional<MembroMinisterioEntity> buscaMembrosPorPerfilId(Long idPerfil) {
        return membroMinisterioRepository.buscaMembrosPorPerfilId(idPerfil);
    }
}
