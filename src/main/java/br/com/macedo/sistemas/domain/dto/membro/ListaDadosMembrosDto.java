package br.com.macedo.sistemas.domain.dto.membro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaDadosMembrosDto implements Serializable {
    private Long idMembro;
    private String nome;
    private String cpf;
}
