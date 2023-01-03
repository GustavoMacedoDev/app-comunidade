package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.endereco.AlteraDadosLogradouroDto;
import br.com.macedo.sistemas.domain.dto.endereco.CadastraLogradouroDto;
import br.com.macedo.sistemas.domain.dto.endereco.ListaLogradouroDto;
import br.com.macedo.sistemas.services.LogradouroService;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;
import org.eclipse.microprofile.openapi.annotations.Operation;
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
        var mensagemResposta = logradouroService.cadastraLogradouro(cadastraLogradouroDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Lista de Logradouros", description = "Lista de Logradouros")
    public Response listaLogradouros() {
        var listaLogradouros = logradouroService.listaLogradouro();

        return Response.status(Response.Status.OK).entity(listaLogradouros).build();
    }

    @GET
    @Path("/{idLogradouro}")
    @Operation(summary = "Busca um logradouro por id", description = "Busca um logradouro por id")
    public Response buscaLogradouroPorId(@PathParam("idLogradouro") Long idLogradouro) {
        var listaLogradouroDto = logradouroService.buscaLogradouroPorId(idLogradouro);

        return Response.status(Response.Status.OK).entity(listaLogradouroDto).build();
    }

    @PUT
    @Path("/{idLogradouro}")
    @Operation(summary = "Altera dados de um logradouro", description = "Altera dados de um logradouro")
    public Response alteraDadosLogradouro(@PathParam("idLogradouro") Long idLogradouro,
                                          @Valid AlteraDadosLogradouroDto alteraDadosLogradouroDto) {
        var mensagemResposta = logradouroService.alteraDadosLogradouro(idLogradouro,  alteraDadosLogradouroDto);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }

    @DELETE
    @Path("/{idLogradouro}")
    @Operation(summary = "Deleta um logradouro", description = "Deleta um logradouro")
    public Response deletaLogradouro(@PathParam("idLogradouro") Long idLogradouro) {
        var mensagemResposta = logradouroService.deletaLogradouro(idLogradouro);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }
}
