package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.cargo.ListaCargosDto;
import br.com.macedo.sistemas.domain.dto.contato.ListaContatoDto;
import br.com.macedo.sistemas.domain.dto.membro.CadastraMembroDto;
import br.com.macedo.sistemas.domain.dto.membro.DetalhaMembroDto;
import br.com.macedo.sistemas.domain.dto.membro.EditaDadosMembroDto;
import br.com.macedo.sistemas.domain.dto.membro.ListaDadosMembrosDto;
import br.com.macedo.sistemas.domain.dto.membro.ListaMembroDto;
import br.com.macedo.sistemas.domain.entities.CargoEntity;
import br.com.macedo.sistemas.domain.entities.ContatoEntity;
import br.com.macedo.sistemas.domain.entities.MembroEntity;
import br.com.macedo.sistemas.repository.MembroRepository;
import br.com.macedo.sistemas.utils.exceptions.ObjectNotFoundException;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MembroService {

    @Inject
    MembroRepository membroRepository;

    @Inject
    CargoService cargoService;

    @Inject
    ContatoService contatoService;

    @Transactional
    public MensagemResposta cadastraMembro(CadastraMembroDto cadastraMembroDto) {
        validaMembroJaExistente(cadastraMembroDto.getCpf());

        MembroEntity membroEntity = new MembroEntity();
        membroEntity.setNome(cadastraMembroDto.getNome());
        membroEntity.setCpf(cadastraMembroDto.getCpf());
        membroEntity.setEndereco(cadastraMembroDto.getEndereco());
        membroEntity.setDataNascimento(cadastraMembroDto.getDataNascimento());
        membroEntity.setCargo(buscaCargoEntity(cadastraMembroDto.getIdCargo()));

        try {
            membroEntity.persist();
        } catch (PersistenceException e){
            throw new PersistenceException("Erro ao executar transação");
        }

        ContatoEntity contatoEntity = new ContatoEntity();
        contatoEntity.setTelefone(cadastraMembroDto.getTelefone());
        contatoEntity.setEmail(cadastraMembroDto.getEmail());
        contatoEntity.setMembro(membroEntity);

        try {
            contatoEntity.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao executar a transação");
        }

        return new MensagemResposta(membroEntity.getIdMembro(), "Membro cadastrado com sucesso");
    }

    private CargoEntity buscaCargoEntity(Long idCargo) {
        return cargoService.buscaCargoPeloId(idCargo);
    }

    private ListaCargosDto buscaCargo(Long idCargo) {
        CargoEntity cargoEntity = cargoService.buscaCargoPeloId(idCargo);

        return new ListaCargosDto(cargoEntity.getIdCargo(), cargoEntity.getNome());
    }

    private void validaMembroJaExistente(String cpf) {
        Optional<MembroEntity> membroEntity = membroRepository.buscaMembroPorCpf(cpf);

        if(membroEntity.isPresent()) {
            throw new ObjectNotFoundException("Membro já cadastrado");
        }

    }

    public List<ListaMembroDto> listaTodosMembros() {
        PanacheQuery<MembroEntity> listaMembros = MembroEntity.findAll();
        listaMembros.range(0, 3);

        List<MembroEntity> listaMembrosEntity = listaMembros.list();

        List<ListaMembroDto> listaResponse = new ArrayList<>();
        for(MembroEntity membroEntity: listaMembrosEntity){
            ListaMembroDto listaMembroDto = new ListaMembroDto();
            listaMembroDto.setIdMembro(membroEntity.getIdMembro());
            listaMembroDto.setNome(membroEntity.getNome());
            listaMembroDto.setCpf(membroEntity.getCpf());
            listaMembroDto.setEndereco(membroEntity.getEndereco());
            listaMembroDto.setDataNascimento(membroEntity.getDataNascimento());
            listaMembroDto.setCargo(buscaCargo(membroEntity.getCargo().getIdCargo()));
            listaMembroDto.setContatos(buscaContatos(membroEntity.getIdMembro()));
            listaResponse.add(listaMembroDto);
        }

        return listaResponse;

    }

    @Transactional
    public MensagemResposta editaDadosMembro(Long idMembro, EditaDadosMembroDto editaDadosMembroDto) {
        validaMembroJaExistente(editaDadosMembroDto.getCpf());
        MembroEntity membroEntity = buscaMembroPorId(idMembro);
        membroEntity.setNome(editaDadosMembroDto.getNome());
        membroEntity.setCpf(editaDadosMembroDto.getCpf());
        membroEntity.setEndereco(editaDadosMembroDto.getEndereco());
        membroEntity.setDataNascimento(editaDadosMembroDto.getDataNascimento());
        membroEntity.setCargo(buscaCargoEntity(editaDadosMembroDto.getIdCargo()));

        try {
            membroEntity.persistAndFlush();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao executar transação " + e.getMessage());
        }

        return new MensagemResposta(membroEntity.getIdMembro(),"Membro alterado com sucesso");
    }

    public MembroEntity buscaMembroPorId(Long idMembro) {
        Optional<MembroEntity> membroEntity = MembroEntity.findByIdOptional(idMembro);

        return membroEntity.orElseThrow(() -> new ObjectNotFoundException("Membro não encontrado"));
    }

    @Transactional
    public MensagemResposta deletaMembro(Long idMembro) {
        MembroEntity membroEntity = buscaMembroPorId(idMembro);

        try {
            membroEntity.delete();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao executar a transação");
        }

        return new MensagemResposta(999L, "Membro deletado com sucesso");
    }

    public List<MembroEntity> listaMembrosPorIdCargo(Long idCargo) {
        return membroRepository.buscaMembrosPorIdCargo(idCargo);
    }


    public DetalhaMembroDto detalhaMembro(Long id) {
        MembroEntity membroEntity = buscaMembroPorId(id);

        DetalhaMembroDto detalhaMembroDto = new DetalhaMembroDto();
        detalhaMembroDto.setIdMembro(membroEntity.getIdMembro());
        detalhaMembroDto.setNome(membroEntity.getNome());
        detalhaMembroDto.setEndereco(membroEntity.getEndereco());
        detalhaMembroDto.setDataNascimento(membroEntity.getDataNascimento());
        detalhaMembroDto.setCpf(membroEntity.getCpf());
        detalhaMembroDto.setCargoNome(buscaCargo(membroEntity.getCargo().getIdCargo()).getNome());

        detalhaMembroDto.setContatos(buscaContatos(id));

        return detalhaMembroDto;

    }

    private List<ListaContatoDto> buscaContatos(Long id) {
        List<ContatoEntity> listaContatos = contatoService.buscaContatosPorIdMembro(id);

        List<ListaContatoDto> listaContatosResponse = new ArrayList<>();
        for(ContatoEntity contatoEntity : listaContatos){
            ListaContatoDto listaContatoDto = new ListaContatoDto();
            listaContatoDto.setEmail(contatoEntity.getEmail());
            listaContatoDto.setTelefone(contatoEntity.getTelefone());

            listaContatosResponse.add(listaContatoDto);
        }

        return listaContatosResponse;
    }

    public List<ListaDadosMembrosDto> listaDadosMembros() {
        List<MembroEntity> listaMembros = MembroEntity.listAll();

        List<ListaDadosMembrosDto> listaDadosResponse = new ArrayList<>();
        for(MembroEntity membroEntity: listaMembros) {
            ListaDadosMembrosDto listaDadosMembrosDto = new ListaDadosMembrosDto();
            listaDadosMembrosDto.setIdMembro(membroEntity.getIdMembro());
            listaDadosMembrosDto.setNome(membroEntity.getNome());
            listaDadosMembrosDto.setCpf(membroEntity.getCpf());

            listaDadosResponse.add(listaDadosMembrosDto);
        }

        return listaDadosResponse;


    }

}
