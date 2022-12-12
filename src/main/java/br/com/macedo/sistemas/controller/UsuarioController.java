package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.usuario.CadastraUsuarioDto;
import br.com.macedo.sistemas.domain.dto.usuario.ListaUsuarioDto;
import br.com.macedo.sistemas.services.UsuarioService;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/usuario")
@ApplicationScoped
@Produces(APPLICATION_JSON)
@Tag(name = "Usuários")
public class UsuarioController {

    @Inject
    UsuarioService usuarioService;

    @POST
    @Path("/")
    @Operation(summary = "Cadastra um usuário", description = "Cadastra um Usuário")
    public Response cadastraUsuario(@RequestBody CadastraUsuarioDto cadastraUsuarioDto) {
        MensagemResposta mensagemResposta = usuarioService.cadastraUsuario(cadastraUsuarioDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/")
    @RolesAllowed("user")
    @Operation(summary = "Lista os usuários", description = "Lista os usuários")
    public Response listaUsuarios() {
        List<ListaUsuarioDto> listaUsuarios = usuarioService.listaUsuarios();

        return Response.status(Response.Status.OK).entity(listaUsuarios).build();
    }
}
