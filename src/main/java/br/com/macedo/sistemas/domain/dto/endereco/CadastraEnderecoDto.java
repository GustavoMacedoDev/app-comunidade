package br.com.macedo.sistemas.domain.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastraEnderecoDto implements Serializable {
    private String complemento;
    private String numero;
    private String cep;
    private Long idLogradouro;
    private Long idBairro;
    private Long idMembro;
}
