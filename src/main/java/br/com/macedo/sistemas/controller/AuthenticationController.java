package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.token.DataDto;
import br.com.macedo.sistemas.domain.dto.token.TokenDto;
import br.com.macedo.sistemas.domain.dto.usuario.LoginDto;
import br.com.macedo.sistemas.services.GeraTokenJwt;
import br.com.macedo.sistemas.services.UsuarioService;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/auth")
@ApplicationScoped
@Produces(APPLICATION_JSON)
@Tag(name = "Autenticação")
public class AuthenticationController {

    @Inject
    UsuarioService usuarioService;

    @Inject
    GeraTokenJwt geraTokenJwt;

    @POST
    @Path("/")
    @Produces(APPLICATION_JSON)
    public Response login(@RequestBody LoginDto loginDto) {

        boolean validaUsuario = usuarioService.validaUsuario(loginDto);
        
        if(validaUsuario) {
            DataDto dataDto = geraTokenJwt.geraTokent();

            return Response.ok(dataDto).build();
        } else {
            MensagemResposta mensagemResposta = new MensagemResposta();
            mensagemResposta.setMensagem("Usuário ou senha incorretos");
            return Response.status(Response.Status.UNAUTHORIZED).entity(mensagemResposta).build();
        }


    }



}
