package br.com.macedo.sistemas.domain.dto.membroMinisterio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastraMembroMinisterioDto implements Serializable {
    private LocalDate dataEntrada;
    private int responsavel;
    private Long idMembro;
    private Long idMinisterio;
    private Long idPerfil;
}
