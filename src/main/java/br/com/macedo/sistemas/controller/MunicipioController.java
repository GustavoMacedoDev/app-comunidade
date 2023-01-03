package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.endereco.MunicipioDto;
import br.com.macedo.sistemas.services.MunicipioService;
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
@Path("/municipio")
@Produces(APPLICATION_JSON)
@Tag(name = "Municipios")
public class MunicipioController {

    @Inject
    MunicipioService municipioService;

    @POST
    @Path("/")
    @Operation(summary = "Cadastro de Municipios", description = "Cadastro de Municipios")
    public Response cadastroDeMunicipios(@Valid List<MunicipioDto> municipios) {

        MensagemResposta mensagemResposta = municipioService.cadastraMunicipios(municipios);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Lista todos os Municipios", description = "Lista todos os municipios")
    public Response listaMunicipios() {
        List<MunicipioDto> municipios = municipioService.buscaMunicipios();

        return Response.status(Response.Status.OK).entity(municipios).build();
    }
}
