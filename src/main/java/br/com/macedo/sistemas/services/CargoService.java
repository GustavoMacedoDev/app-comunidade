package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.cargo.CadastraCargoDto;
import br.com.macedo.sistemas.domain.dto.cargo.EditaCargoDto;
import br.com.macedo.sistemas.domain.dto.cargo.ListaCargosDto;
import br.com.macedo.sistemas.domain.entities.CargoEntity;
import br.com.macedo.sistemas.domain.entities.MembroEntity;
import br.com.macedo.sistemas.repository.CargoRepository;
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
public class CargoService {

    @Inject
    CargoRepository cargoRepository;

    @Inject
    MembroService membroService;

    @Transactional
    public MensagemResposta cadastraCargo(CadastraCargoDto cadastraCargoDto) {
        validaExistenciaCargoPeloNome(cadastraCargoDto.getNome());

        CargoEntity cargo = new CargoEntity();
        cargo.setNome(cadastraCargoDto.getNome());

        try {
            cargo.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao executar a transação" + e.getMessage());
        }

        return new MensagemResposta(cargo.getIdCargo(), "Cargo cadastrado com sucesso");

    }


    private void validaExistenciaCargoPeloNome(String nome) {
        Optional<CargoEntity> cargo = cargoRepository.buscaCargoPeloNome(nome);

        if(cargo.isPresent()) {
            throw new ObjectNotFoundException("Cargo já cadastrado");
        }
    }

    public List<ListaCargosDto> listaCargos() {
        List<CargoEntity> listaCargos = CargoEntity.listAll();

        List<ListaCargosDto> listaCargosResponse = new ArrayList<>();
        for(CargoEntity cargo : listaCargos){
            ListaCargosDto listaCargosDto = new ListaCargosDto();
            listaCargosDto.setIdCargo(cargo.getIdCargo());
            listaCargosDto.setNome(cargo.getNome());

            listaCargosResponse.add(listaCargosDto);
        }

        return listaCargosResponse;
    }

    @Transactional
    public MensagemResposta editaDadosCargo(Long idCargo, EditaCargoDto editaCargoDto) {
        CargoEntity cargoEntity = buscaCargoPeloId(idCargo);
        verificaVinculoComMembro(idCargo);
        validaExistenciaCargoPeloNome(editaCargoDto.getNome());

        cargoEntity.setNome(editaCargoDto.getNome());

        try {
            cargoEntity.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao executar a transação" + e.getMessage());
        }

        return new MensagemResposta(cargoEntity.getIdCargo(), "Cargo alterado com sucesso");
    }

    public CargoEntity buscaCargoPeloId(Long idCargo) {
        Optional<CargoEntity> cargo = cargoRepository.findByIdOptional(idCargo);

        return cargo.orElseThrow(() -> new ObjectNotFoundException("Cargo não encontrado"));
    }

    @Transactional
    public MensagemResposta deletaCargo(Long idCargo) {
        verificaVinculoComMembro(idCargo);
        CargoEntity cargo = buscaCargoPeloId(idCargo);

        try {
            cargo.delete();
        } catch (PersistenceException e){
            throw new PersistenceException("Erro ao executar a transação" + e.getMessage());
        }

        return new MensagemResposta(999L, "Cargo deletado com sucesso");
    }

    private void verificaVinculoComMembro(Long idCargo) {
        List<MembroEntity> listaMembros = membroService.listaMembrosPorIdCargo(idCargo);

        if(!listaMembros.isEmpty()) {
            throw new ObjectNotFoundException("Cargo não pode ser excluído/alterado pois possui vinculo com membros");
        }
    }

    public ListaCargosDto listaCargoPorId(Long idCargo) {
        CargoEntity cargoEntity = buscaCargoPeloId(idCargo);

        ListaCargosDto listaCargosDto = new ListaCargosDto();
        listaCargosDto.setIdCargo(cargoEntity.getIdCargo());
        listaCargosDto.setNome(cargoEntity.getNome());

        return listaCargosDto;
    }
}
