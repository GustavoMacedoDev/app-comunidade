package br.com.macedo.sistemas.domain.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaEnderecoDto implements Serializable {
    private Long idEndereco;
    private String complemento;
    private String numero;
    private String cep;
    private String logradouro;
    private String tipoLogradouro;
    private String bairro;
    private String municipio;
    private String estado;
    private String siglaEstado;
}
