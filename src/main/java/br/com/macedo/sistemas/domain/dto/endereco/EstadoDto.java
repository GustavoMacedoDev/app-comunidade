package br.com.macedo.sistemas.domain.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EstadoDto implements Serializable {
    private Long id;
    private String nome;
    private String sigla;
    private RegiaoDto regiao;
}
