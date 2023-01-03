package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.endereco.CadastraEnderecoDto;
import br.com.macedo.sistemas.domain.dto.endereco.ListaEnderecoDto;
import br.com.macedo.sistemas.services.EnderecoService;
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

@ApplicationScoped
@Path("/endereco")
@Produces(APPLICATION_JSON)
@Tag(name = "Endereços")
public class EnderecoController {

    @Inject
    EnderecoService enderecoService;

    @POST
    @Path("/")
    @Operation(summary = "Cadastro de Endereço", description = "Cadastro de Endereço")
    public Response cadastraEndereco(@Valid CadastraEnderecoDto cadastraEnderecoDto) {
        MensagemResposta mensagemResposta = enderecoService.cadastraEndereco(cadastraEnderecoDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/{idMembro}")
    @Operation(summary = "Lista Endereço por Id Membro", description = "Lista Endereço por Id Membro")
    public Response listaEnderecoPorIdMembro(@PathParam("idMembro") Long idMembro) {
        List<ListaEnderecoDto> listaEndereco = enderecoService.buscaEnderecoPorIdMembro(idMembro);

        return Response.status(Response.Status.OK).entity(listaEndereco).build();
    }
}
