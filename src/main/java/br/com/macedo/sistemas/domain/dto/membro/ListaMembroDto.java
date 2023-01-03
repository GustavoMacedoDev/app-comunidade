package br.com.macedo.sistemas.domain.dto.membro;

import br.com.macedo.sistemas.domain.dto.cargo.ListaCargosDto;
import br.com.macedo.sistemas.domain.dto.contato.ListaContatoDto;
import br.com.macedo.sistemas.domain.entities.CargoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaMembroDto implements Serializable {
    private Long idMembro;
    private String nome;
    private String cpf;
    private String endereco;
    private LocalDate dataNascimento;
    private ListaCargosDto cargo;
}
