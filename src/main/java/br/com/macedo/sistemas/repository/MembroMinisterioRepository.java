package br.com.macedo.sistemas.repository;

import br.com.macedo.sistemas.domain.entities.MembroMinisterioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MembroMinisterioRepository implements PanacheRepository<MembroMinisterioEntity> {


    public List<MembroMinisterioEntity> listaMembroPorMinisterio(Long idMinisterio) {
        return find("ministerio.idMinisterio", idMinisterio).list();
    }

    public Optional<MembroMinisterioEntity> buscaMembroMinisterio(Long idMinisterio, Long idMembro) {
        return find("ministerio.idMinisterio = ?1 and membro.idMembro = ?2", idMinisterio, idMembro).firstResultOptional();
    }

    public Optional<MembroMinisterioEntity> buscaMembrosPorPerfilId(Long idPerfil) {
        return find("perfil.idPerfil", idPerfil).firstResultOptional();
    }
}
