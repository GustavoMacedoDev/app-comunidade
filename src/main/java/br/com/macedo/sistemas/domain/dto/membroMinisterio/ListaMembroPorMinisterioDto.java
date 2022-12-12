package br.com.macedo.sistemas.domain.dto.membroMinisterio;

import br.com.macedo.sistemas.domain.dto.membro.ListaMembroDto;
import br.com.macedo.sistemas.domain.dto.ministerio.ListaMinisterioDto;
import br.com.macedo.sistemas.domain.dto.perfil.ListaPerfilDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaMembroPorMinisterioDto implements Serializable {
    private Long idMembroMinisterio;
    private LocalDate dataEntrada;
    private int responsavel;
    private ListaMembroDto membro;
    private ListaPerfilDto perfilMembro;
}
