package br.com.macedo.sistemas.domain.dto.ministerio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaMinisterioDto implements Serializable {
    private Long idMinisterio;
    private String nome;
    private String sigla;
    private LocalDate dataCriacao;
}
