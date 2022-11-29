package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.membro.CadastraMembroDto;
import br.com.macedo.sistemas.domain.dto.membro.DetalhaMembroDto;
import br.com.macedo.sistemas.domain.dto.membro.EditaDadosMembroDto;
import br.com.macedo.sistemas.domain.dto.membro.ListaMembroDto;
import br.com.macedo.sistemas.services.MembroService;
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

@Path("/membros")
@ApplicationScoped
@Produces(APPLICATION_JSON)
@Tag(name = "Membros")
public class MembroController {

    @Inject
    MembroService membroService;
    
    @POST
    @Path("/")
    @Operation(summary = "Cadastro de Membros", description = "Cadastro de Membros")
    public Response cadastraMembro(@Valid CadastraMembroDto cadastraMembroDto){
        MensagemResposta mensagemResposta = membroService.cadastraMembro(cadastraMembroDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();

    }

    @GET
    @Path("/")
    @Operation(summary = "Lista todos os Membros", description = "Lista todos os Membros")
    public Response listaTodosMembros(){
        List<ListaMembroDto> listaMembroDto = membroService.listaTodosMembros();

        return Response.status(Response.Status.OK).entity(listaMembroDto).build();
    }

    @GET
    @Path("/{idMembro}")
    @Operation(summary = "Detalha um membro por Id", description = "Detalha um membro por Id")
    public Response detalhaMembro(@PathParam("idMembro") Long id) {
        DetalhaMembroDto detalhaMembroDto = membroService.detalhaMembro(id);

        return Response.status(Response.Status.OK).entity(detalhaMembroDto).build();
    }

    @PUT
    @Path("/{idMembro}")
    @Operation(summary = "Edita dados de um membro", description = "Edita dados de um membro")
    public Response editaDadosMembro(@PathParam("idMembro") Long idMembro,
                                    @Valid EditaDadosMembroDto editaDadosMembroDto) {

        MensagemResposta mensagemResposta = membroService.editaDadosMembro(idMembro, editaDadosMembroDto);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }

    @DELETE
    @Path("/{idMembro}")
    @Operation(summary = "Deleta um membro", description = "Deleta um membro")
    public Response deletaMembro(@PathParam("idMembro") Long idMembro){
        MensagemResposta mensagemResposta = membroService.deletaMembro(idMembro);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }
}
