package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.perfil.CadastraPerfilDto;
import br.com.macedo.sistemas.domain.dto.perfil.EditaPerfilDto;
import br.com.macedo.sistemas.domain.dto.perfil.ListaPerfilDto;
import br.com.macedo.sistemas.domain.entities.MembroEntity;
import br.com.macedo.sistemas.domain.entities.MembroMinisterioEntity;
import br.com.macedo.sistemas.domain.entities.PerfilEntity;
import br.com.macedo.sistemas.repository.PerfilRepository;
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
public class PerfilService {

    @Inject
    PerfilRepository perfilRepository;

    @Inject
    MembroMinisterioService membroMinisterioService;

    public PerfilEntity buscaPerfilPorId(Long idPerfil) {
         Optional<PerfilEntity> perfilEntity = PerfilEntity.findByIdOptional(idPerfil);

         return perfilEntity.orElseThrow(() -> new ObjectNotFoundException("Perfil não encontrado"));
    }

    public List<ListaPerfilDto> listaPerfis() {
        List<PerfilEntity> listaPerfis = PerfilEntity.listAll();

        List<ListaPerfilDto> listaResponse = new ArrayList<>();
        for(PerfilEntity perfilEntity: listaPerfis){
            ListaPerfilDto listaPerfilDto = new ListaPerfilDto();
            listaPerfilDto.setIdPerfil(perfilEntity.getIdPerfil());
            listaPerfilDto.setNome(perfilEntity.getNome());

            listaResponse.add(listaPerfilDto);
        }

        return listaResponse;
    }

    @Transactional
    public MensagemResposta cadastrarPerfil(CadastraPerfilDto cadastraPerfilDto) {
        validaExistenciaPerfil(cadastraPerfilDto.getNome());

        PerfilEntity perfilEntity = new PerfilEntity();
        perfilEntity.setNome(cadastraPerfilDto.getNome());

        try{
            perfilEntity.persist();
        } catch (PersistenceException e) {
            throw new ObjectNotFoundException("Erro ao executar a transação" + e.getMessage());
        }

        return new MensagemResposta(perfilEntity.getIdPerfil(), "Perfil cadastrado com sucesso");
    }

    private void validaExistenciaPerfil(String nome) {
        Optional<PerfilEntity> perfil = perfilRepository.buscaPeloNome(nome);

        if(perfil.isPresent()) {
            throw new ObjectNotFoundException("Perfil já cadastrado");
        }
    }

    @Transactional
    public MensagemResposta editaPerfil(Long idPerfil, EditaPerfilDto editaPerfilDto) {
        PerfilEntity perfil = buscaPerfilPorId(idPerfil);

        perfil.setNome(editaPerfilDto.getNome());

        try {
            perfil.persistAndFlush();
        } catch (PersistenceException e) {
            throw new ObjectNotFoundException("Erro ao executar transação" + e.getMessage());
        }

        return new MensagemResposta(perfil.getIdPerfil(), "Perfil alterado com sucesso");
    }


    public ListaPerfilDto listaPerfilPorId(Long idPerfil) {
        PerfilEntity perfil = buscaPerfilPorId(idPerfil);

        ListaPerfilDto listaPerfilDto = new ListaPerfilDto();
        listaPerfilDto.setIdPerfil(perfil.getIdPerfil());
        listaPerfilDto.setNome(perfil.getNome());

        return listaPerfilDto;
    }

    @Transactional
    public MensagemResposta deletaPerfilSemVinculo(Long idPerfil) {
        PerfilEntity perfil = buscaPerfilPorId(idPerfil);
        verificaVinculos(idPerfil);

        try {
            perfil.delete();
        } catch (PersistenceException e) {
            throw new ObjectNotFoundException("Erro ao executar transação" + e.getMessage());
        }

        return new MensagemResposta(999L, "Perfil excluído com sucesso");
    }

    private void verificaVinculos(Long idPerfil) {
        Optional<MembroMinisterioEntity> membroMinisterioEntity = membroMinisterioService.buscaMembrosPorPerfilId(idPerfil);

        if(membroMinisterioEntity.isPresent()) {
            throw new ObjectNotFoundException("Perfil não pode ser alterado/excluido pois possui vinculos");
        }
    }
}
