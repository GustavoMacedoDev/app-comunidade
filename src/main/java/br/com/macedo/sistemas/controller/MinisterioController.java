package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.ministerio.CadastraMinisterioDto;
import br.com.macedo.sistemas.domain.dto.ministerio.EditaDadosMinisterioDto;
import br.com.macedo.sistemas.domain.dto.ministerio.ListaMinisterioDto;
import br.com.macedo.sistemas.services.MinisterioService;
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

@Path("/ministerios")
@ApplicationScoped
@Produces(APPLICATION_JSON)
@Tag(name = "Ministérios")
public class MinisterioController {

    @Inject
    MinisterioService ministerioService;

    @POST
    @Path("/")
    @Operation(summary = "Cadastra um ministério", description = "Cadastra um ministérios")
    public Response cadastraMinisterio(@Valid CadastraMinisterioDto cadastraMinisterioDto) {
        MensagemResposta mensagemResposta = ministerioService.cadastraMinisterio(cadastraMinisterioDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();

    }

    @GET
    @Path("/")
    @Operation(description = "Lista todos os ministérios", summary = "Lista todos os ministérios")
    public Response listaMinisterios(){
        List<ListaMinisterioDto> listaMinisterios = ministerioService.listaMinisterios();

        return Response.status(Response.Status.OK).entity(listaMinisterios).build();

    }

    @GET
    @Path("/detalha/{idMinisterio}")
    @Operation(summary = "Lista Ministério por Id", description = "Lista Ministério por Id")
    public Response listaMinisterioPorId(@PathParam("idMinisterio") Long idMinisterio) {
        ListaMinisterioDto listaMinisterioDto = ministerioService.listaMinisterioPorId(idMinisterio);

        return Response.status(Response.Status.OK).entity(listaMinisterioDto).build();
    }

    @PUT
    @Path("/{idMinisterio}")
    @Operation(summary = "Edita dados de um ministério", description = "Edita dados de um ministério")
    public Response editaMinisterio(@PathParam("idMinisterio") Long idMinisterio,
                                    @RequestBody EditaDadosMinisterioDto editaDadosMinisterioDto) {

        MensagemResposta mensagemResposta = ministerioService.editaDadosMinisterio(editaDadosMinisterioDto, idMinisterio);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }

    @DELETE
    @Path("/{idMinisterio}")
    @Operation(summary = "Deleta um ministerio", description = "Deleta um ministerio")
    public Response deletaMinisterio(@PathParam("idMinisterio") Long idMinisterio ){

        MensagemResposta mensagemResposta = ministerioService.deletaMinisterio(idMinisterio);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }
}
