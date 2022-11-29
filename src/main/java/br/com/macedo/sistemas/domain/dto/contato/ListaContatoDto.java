package br.com.macedo.sistemas.domain.dto.contato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaContatoDto implements Serializable {
    private Long idContato;
    private String email;
    private String telefone;
}
