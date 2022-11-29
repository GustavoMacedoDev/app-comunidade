package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.ministerio.CadastraMembroMinisterioDto;
import br.com.macedo.sistemas.domain.entities.MembroEntity;
import br.com.macedo.sistemas.domain.entities.MembroMinisterioEntity;
import br.com.macedo.sistemas.domain.entities.MinisterioEntity;
import br.com.macedo.sistemas.domain.entities.PerfilEntity;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

@ApplicationScoped
public class MembroMinisterioService {

    @Inject
    MinisterioService ministerioService;

    @Inject
    MembroService membroService;

    @Inject
    PerfilService perfilService;

    @Transactional
    public MensagemResposta cadastraMembroMinisterio(CadastraMembroMinisterioDto cadastraMembroMinisterioDto) {
        MembroMinisterioEntity membroMinisterio = new MembroMinisterioEntity();
        membroMinisterio.setDataEntrada(cadastraMembroMinisterioDto.getDataEntrada());
        membroMinisterio.setDataSaida(cadastraMembroMinisterioDto.getDataSaida());
        membroMinisterio.setResponsavel(cadastraMembroMinisterioDto.getResponsavel());
        membroMinisterio.setMinisterio(buscaMinisterio(cadastraMembroMinisterioDto.getIdMinisterio()));
        membroMinisterio.setMembro(buscaMembro(cadastraMembroMinisterioDto.getIdMembro()));
        membroMinisterio.setPerfil(buscaPerfil(cadastraMembroMinisterioDto.getIdPerfil()));

        try {
            membroMinisterio.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao executar transação" + e.getMessage());
        }

        return new MensagemResposta(membroMinisterio.getIdMembroMinisterio(),
                "Membro cadastrado com sucesso ao ministério");

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
}
