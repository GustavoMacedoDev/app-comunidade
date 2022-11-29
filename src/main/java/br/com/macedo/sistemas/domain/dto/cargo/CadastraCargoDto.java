package br.com.macedo.sistemas.domain.dto.cargo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastraCargoDto implements Serializable {
    private String nome;
}
