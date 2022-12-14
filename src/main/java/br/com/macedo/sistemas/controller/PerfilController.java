package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.perfil.CadastraPerfilDto;
import br.com.macedo.sistemas.domain.dto.perfil.EditaPerfilDto;
import br.com.macedo.sistemas.domain.dto.perfil.ListaPerfilDto;
import br.com.macedo.sistemas.services.PerfilService;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    @POST
    @Path("/")
    @Operation(summary = "Cadastra um novo perfil", description = "Cadastra um novo perfil")
    public Response cadastraPerfil(@Valid @RequestBody CadastraPerfilDto cadastraPerfilDto) {
        MensagemResposta mensagemResposta = perfilService.cadastrarPerfil(cadastraPerfilDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Lista os perfis", description = "Lista os perfis")
    public Response listaMembros() {
        List<ListaPerfilDto> listaPerfilDto = perfilService.listaPerfis();

        return Response.status(Response.Status.OK).entity(listaPerfilDto).build();
    }

    @GET
    @Path("/{idPerfil}")
    @Operation(summary = "Lista perfil por ID", description = "Lista perfil por ID")
    public Response listaPerfilPorId(@PathParam("idPerfil") Long idPerfil) {
        ListaPerfilDto listaPerfilDto = perfilService.listaPerfilPorId(idPerfil);

        return Response.status(Response.Status.OK).entity(listaPerfilDto).build();
    }

    @PUT
    @Path("/{idPerfil}")
    @Operation(summary = "Altera dados de perfil", description = "Altera dados de perfil")
    public Response alteraDadosPerfil(@PathParam ("idPerfil") Long idPerfil,
                                      @Valid @RequestBody EditaPerfilDto editaPerfilDto) {
        MensagemResposta mensagemResposta = perfilService.editaPerfil(idPerfil, editaPerfilDto);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }

    @DELETE
    @Path("/{idPerfil}")
    @Operation(summary = "Deleta um perfil sem vinculos", description = "Deleta um perfil sem vinculos")
    public Response deletaPerfil(@PathParam("idPerfil") Long idPerfil) {
        MensagemResposta mensagemResposta = perfilService.deletaPerfilSemVinculo(idPerfil);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }
}
