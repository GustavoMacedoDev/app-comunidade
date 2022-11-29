package br.com.macedo.sistemas.domain.dto.membro;

import br.com.macedo.sistemas.domain.dto.cargo.ListaCargosDto;
import br.com.macedo.sistemas.domain.entities.CargoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaMembroDto implements Serializable {
    private Long idMembro;
    private String nome;
    private String cpf;
    private String endereco;
    private LocalDate dataNascimento;
    private CargoEntity cargo;
}
