package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.membroMinisterio.CadastraMembroMinisterioDto;
import br.com.macedo.sistemas.domain.dto.membroMinisterio.ListaMembroPorMinisterioDto;
import br.com.macedo.sistemas.services.MembroMinisterioService;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/membro-ministerio")
@ApplicationScoped
@Produces(APPLICATION_JSON)
@Tag(name = "Membros do Ministério")
public class MembroMinisterioController {

    @Inject
    MembroMinisterioService membroMinisterioService;

    @POST
    @Path("/")
    @Operation(summary = "Cadastra um membro no ministerio", description = "Cadastra um membro no ministerio")
    public Response cadastraMembroMinisterio(@Valid CadastraMembroMinisterioDto cadastraMembroMinisterioDto) {

        MensagemResposta mensagemResposta = membroMinisterioService.cadastraMembroMinisterio(cadastraMembroMinisterioDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/{idMinisterio}")
    @Operation(summary = "Lista membros por ministério",description = "Lista membros por ministério")
    public Response listaMembrosPorMinisterio(@PathParam("idMinisterio") Long idMinisterio) {
        List<ListaMembroPorMinisterioDto> listaMembroPorMinisterioDto =
                membroMinisterioService.listaMembroPorMinisterio(idMinisterio);

        return Response.status(Response.Status.OK).entity(listaMembroPorMinisterioDto).build();
    }
}
