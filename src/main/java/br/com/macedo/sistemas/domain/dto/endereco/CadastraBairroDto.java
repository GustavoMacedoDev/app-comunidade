package br.com.macedo.sistemas.domain.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastraBairroDto implements Serializable {
    private Long idBairro;
    private String nome;
    private Long idMunicipio;
}
