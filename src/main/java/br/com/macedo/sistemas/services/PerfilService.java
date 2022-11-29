package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.perfil.ListaPerfilDto;
import br.com.macedo.sistemas.domain.entities.PerfilEntity;
import br.com.macedo.sistemas.utils.exceptions.ObjectNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PerfilService {

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
}
