package br.com.macedo.sistemas.domain.dto.endereco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MunicipioDto implements Serializable {
    private Long id;
    private String nome;
    private MicrorregiaoDto microrregiao;

}
