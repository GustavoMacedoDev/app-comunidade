package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.contato.CadastraContatoDto;
import br.com.macedo.sistemas.domain.dto.contato.ListaContatoDto;
import br.com.macedo.sistemas.services.ContatoService;
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
@Path("/contato")
@Produces(APPLICATION_JSON)
@Tag(name = "Contatos")
public class ContatoController {

    @Inject
    ContatoService contatoService;

    @POST
    @Path("/")
    @Operation(summary = "Cadastra novo contato", description = "Cadastra novo contato")
    public Response cadastraContato(@Valid CadastraContatoDto cadastraContatoDto) {
        MensagemResposta mensagemResposta = contatoService.cadastraContato(cadastraContatoDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/{idMembro}")
    @Operation(summary = "Lista contatos por Id do Membro", description = "Lista contatos por Id do membro")
    public Response listaContatoPorIdMembro(@PathParam("idMembro") Long idMembro) {
        List<ListaContatoDto> listaContatos = contatoService.buscaContatoPorIdMembro(idMembro);

        return Response.status(Response.Status.OK).entity(listaContatos).build();
    }
}
