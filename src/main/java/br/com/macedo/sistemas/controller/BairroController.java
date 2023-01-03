package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.endereco.CadastraBairroDto;
import br.com.macedo.sistemas.domain.dto.endereco.ListaBairroDto;
import br.com.macedo.sistemas.services.BairroService;
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
        MensagemResposta mensagemResposta = bairroService.cadastraBairro(cadastraBairroDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Lista Bairros", description = "Lista Bairros")
    public Response listaBairros() {
        List<ListaBairroDto> listaBairros = bairroService.listaBairros();

        return Response.status(Response.Status.OK).entity(listaBairros).build();
    }
}
