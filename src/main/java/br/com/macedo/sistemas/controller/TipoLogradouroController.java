package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.endereco.AlteraNomeTipoLogradouroDto;
import br.com.macedo.sistemas.domain.dto.endereco.CadastraTipoLogradouroDto;
import br.com.macedo.sistemas.domain.dto.endereco.TipoLogradouroDto;
import br.com.macedo.sistemas.services.TipoLogradouroService;
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
@Path("/tipologradouro")
@Produces(APPLICATION_JSON)
@Tag(name = "Tipo de Logradouro")
public class TipoLogradouroController {

    @Inject
    TipoLogradouroService tipoLogradouroService;

    @POST
    @Path("/")
    @Operation(summary = "Cadastra tipo de Logradouro", description = "Cadastra Tipo de logradouro")
    public Response cadastraTipoLogradouro(@Valid CadastraTipoLogradouroDto cadastraTipoLogradouroDto) {
        MensagemResposta mensagemResposta = tipoLogradouroService.cadastraTipoLogradouro(cadastraTipoLogradouroDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Lista Tipos de Logradouro", description = "Lista tipos de logradouros")
    public Response listaTipoLogradouro() {
        List<TipoLogradouroDto> tipoLogradouro = tipoLogradouroService.listaTipoLogradouro();

        return Response.status(Response.Status.OK).entity(tipoLogradouro).build();
    }

    @GET
    @Path("/{idTipoLogradouro}")
    @Operation(summary = "Lista tipo logradouro por id", description = "Lista tipo logradouro por id")
    public Response listaLogradouroPorId(@PathParam("idTipoLogradouro") Long idTipoLogradouro) {
        TipoLogradouroDto tipoLogradouroDto = tipoLogradouroService.buscaTipoLogradouroPorId(idTipoLogradouro);

        return Response.status(Response.Status.OK).entity(tipoLogradouroDto).build();
    }

    @PUT
    @Path("/{idTipoLogradouro}")
    @Operation(summary = "Altera o nome de um tipo de logradouro sem vinculos", description = "Altera o nome de um tipo de logradouro")
    public Response alteraNomeTipoLogradouro(@PathParam("idTipoLogradouro") Long idTipoLogradouro,
                                             @Valid AlteraNomeTipoLogradouroDto alteraNomeTipoLogradouro){
        MensagemResposta mensagemResposta = tipoLogradouroService.alteraNomeTipoLogradouro(idTipoLogradouro, alteraNomeTipoLogradouro);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();

    }

    @DELETE
    @Path("/{idTipoLogradouro}")
    @Operation(summary = "Deleta um tipo de logradouro sem vinculos", description = "Deletar um tipo de logradouro sem vinculos")
    public Response deletaTipoLogradouroSemVinculos(@PathParam("idTipoLogradouro") Long idTipoLogradouro) {
        MensagemResposta mensagemResposta = tipoLogradouroService.deletaTipoLogradouroSemVinculos(idTipoLogradouro);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }
}
