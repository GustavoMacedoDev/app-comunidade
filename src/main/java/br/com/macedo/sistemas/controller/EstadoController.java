package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.endereco.EstadoDto;
import br.com.macedo.sistemas.services.EstadoService;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@ApplicationScoped
@Path("/estado")
@Produces(APPLICATION_JSON)
@Tag(name = "Estados")
public class EstadoController {

    @Inject
    EstadoService estadoService;

    @POST
    @Path("/")
    @Operation(summary = "Cadastros de estado", description = "Cadastro de estados")
    public Response cadastraEstados(@Valid List<EstadoDto> estados){

        MensagemResposta mensagemResposta = estadoService.cadastraEstados(estados);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Lista todos os estados", description = "Lista todos os estados")
    public Response listaEstados() {
        List<EstadoDto> estados = estadoService.buscaEstados();

        return Response.status(Response.Status.OK).entity(estados).build();
    }
}
