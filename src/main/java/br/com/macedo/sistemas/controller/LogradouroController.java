package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.endereco.CadastraLogradouroDto;
import br.com.macedo.sistemas.domain.dto.endereco.ListaLogradouroDto;
import br.com.macedo.sistemas.services.LogradouroService;
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
@Path("/logradouros")
@Produces(APPLICATION_JSON)
@Tag(name = "Logradouros")
public class LogradouroController {

    @Inject
    LogradouroService logradouroService;

    @POST
    @Path("/")
    @Operation(summary = "Cadastro de Logradouro", description = "Cadastro de Logradouro")
    public Response cadastraLogradouro(@Valid CadastraLogradouroDto cadastraLogradouroDto) {
        MensagemResposta mensagemResposta = logradouroService.cadastraLogradouro(cadastraLogradouroDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Lista de Logradouros", description = "Lista de Logradouros")
    public Response listaLogradouros() {
        List<ListaLogradouroDto> listaLogradouros = logradouroService.listaLogradouro();

        return Response.status(Response.Status.OK).entity(listaLogradouros).build();
    }
}
