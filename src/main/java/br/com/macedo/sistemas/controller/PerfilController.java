package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.perfil.ListaPerfilDto;
import br.com.macedo.sistemas.services.PerfilService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/perfil")
@ApplicationScoped
@Produces(APPLICATION_JSON)
@Tag(name = "Perfis")
public class PerfilController {

    @Inject
    PerfilService perfilService;

    @GET
    @Path("/")
    @Operation(summary = "Lista os perfis", description = "Lista os perfis")
    public Response listaMembros() {
        List<ListaPerfilDto> listaPerfilDto = perfilService.listaPerfis();

        return Response.status(Response.Status.OK).entity(listaPerfilDto).build();
    }
}
