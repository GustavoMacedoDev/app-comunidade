package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.ministerio.CadastraMinisterioDto;
import br.com.macedo.sistemas.domain.dto.ministerio.ListaMinisterioDto;
import br.com.macedo.sistemas.services.MinisterioService;
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
}
