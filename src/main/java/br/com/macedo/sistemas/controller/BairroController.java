package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.endereco.AlteraDadosBairroDto;
import br.com.macedo.sistemas.domain.dto.endereco.CadastraBairroDto;
import br.com.macedo.sistemas.domain.dto.endereco.ListaBairroDto;
import br.com.macedo.sistemas.services.BairroService;
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
@Path("/bairro")
@Produces(APPLICATION_JSON)
@Tag(name = "Bairros")
public class BairroController {

    @Inject
    BairroService bairroService;

    @POST
    @Path("/")
    @Operation(summary = "Cadastro de Bairros", description = "Cadastro de Bairros")
    public Response cadastraBairro(@Valid CadastraBairroDto cadastraBairroDto) {
        var mensagemResposta = bairroService.cadastraBairro(cadastraBairroDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Lista Bairros", description = "Lista Bairros")
    public Response listaBairros() {
        var listaBairros = bairroService.listaBairros();

        return Response.status(Response.Status.OK).entity(listaBairros).build();
    }

    @GET
    @Path("/{idBairro}")
    @Operation(summary = "Lista Bairro por Id", description = "Lista Bairro por Id")
    public Response listaBairroPorId(@PathParam("idBairro") Long idBairro) {
        var listaBairroDto = bairroService.listaBairroPorId(idBairro);

        return Response.status(Response.Status.OK).entity(listaBairroDto).build();
    }

    @PUT
    @Path("/{idBairro}")
    @Operation(summary = "Altera dados do bairro", description = "Altera dados do Bairro")
    public Response alteraDadosBairro(@PathParam("idBairro") Long idBairro, @Valid AlteraDadosBairroDto alteraDadosBairroDto) {
        var mensagemResposta = bairroService.alteraDadosBairro(idBairro, alteraDadosBairroDto);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }

    @DELETE
    @Path("/{idBairro}")
    @Operation(summary = "Deleta um bairro", description = "Deleta um bairro")
    public Response deletaBairro(@PathParam("idBairro") Long idBairro) {
        MensagemResposta mensagemResposta = bairroService.deletaBairro(idBairro);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }


}
