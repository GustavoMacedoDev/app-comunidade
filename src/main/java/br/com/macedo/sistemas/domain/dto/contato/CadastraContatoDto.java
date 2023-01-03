package br.com.macedo.sistemas.domain.dto.contato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastraContatoDto implements Serializable {
    private String email;
    private String telefone;
    private Long idMembro;
}
