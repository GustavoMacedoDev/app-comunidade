package br.com.macedo.sistemas.repository;

import br.com.macedo.sistemas.domain.entities.enderecos.EnderecoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<EnderecoEntity> {

    public List<EnderecoEntity> buscaEnderecoPorIdMembro(Long idMembro) {
        return find("membro.idMembro", idMembro).list();
    }

    public List<EnderecoEntity> buscaEnderecoPorIdLogradouro(Long idLogradouro) {
        return find("logradouro.idLogradouro", idLogradouro).list();
    }

    public List<EnderecoEntity> buscaEnderecoPorIdBairro(Long idBairro) {
        return find("bairro.idBairro", idBairro).list();
    }
}
