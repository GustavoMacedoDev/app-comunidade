package br.com.macedo.sistemas.controller;

import br.com.macedo.sistemas.domain.dto.cargo.CadastraCargoDto;
import br.com.macedo.sistemas.domain.dto.cargo.EditaCargoDto;
import br.com.macedo.sistemas.domain.dto.cargo.ListaCargosDto;
import br.com.macedo.sistemas.services.CargoService;
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
@Path("/cargos")
@Produces(APPLICATION_JSON)
@Tag(name = "Cargos")
public class CargoController {

    @Inject
    CargoService cargoService;

    @POST
    @Path("/")
    @Operation(summary = "Cadastra um cargo", description = "Cadastra um cargo")
    public Response cadastraCargo(@Valid CadastraCargoDto cadastraCargoDto) {
        MensagemResposta mensagemResposta = cargoService.cadastraCargo(cadastraCargoDto);

        return Response.status(Response.Status.CREATED).entity(mensagemResposta).build();
    }

    @GET
    @Path("/")
    @Operation(summary = "Lista todos os cargos", description = "Lista todos os cargos")
    public Response listaCargos() {
        List<ListaCargosDto> listaCargosDto = cargoService.listaCargos();

        return Response.status(Response.Status.OK).entity(listaCargosDto).build();
    }

    @PUT
    @Path("/{idCargo}")
    @Operation(summary = "Edita os dados do cargo", description = "Edita os dados do cargo")
    public Response editaDadosCargo(@PathParam("idCargo") Long idCargo, @Valid EditaCargoDto editaCargoDto) {
        MensagemResposta mensagemResposta = cargoService.editaDadosCargo(idCargo, editaCargoDto);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }

    @DELETE
    @Path("/{idCargo}")
    @Operation(description = "Deleta um cargo", summary = "Deleta um cargo")
    public Response deletaCargo(@PathParam("idCargo") Long idCargo) {
        MensagemResposta mensagemResposta = cargoService.deletaCargo(idCargo);

        return Response.status(Response.Status.OK).entity(mensagemResposta).build();
    }
}
