package br.com.macedo.sistemas.domain.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastraLogradouroDto implements Serializable {
    private Long idLogradouro;
    private String nome;
    private Long idTipoLogradouro;
}
