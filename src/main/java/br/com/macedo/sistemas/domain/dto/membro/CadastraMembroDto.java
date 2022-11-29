package br.com.macedo.sistemas.domain.dto.membro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastraMembroDto implements Serializable {
    private String nome;
    
    private String cpf;
    private String endereco;
    private LocalDate dataNascimento;
    private Long idCargo;
    private String telefone;
    private String email;
}
