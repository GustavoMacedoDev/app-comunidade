package br.com.macedo.sistemas.domain.dto.membro;

import br.com.macedo.sistemas.domain.dto.contato.ListaContatoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalhaMembroDto implements Serializable {
    private Long idMembro;
    private String nome;
    private String cpf;
    private String endereco;
    private LocalDate dataNascimento;
    private String cargo;
}
