package br.com.macedo.sistemas.domain.dto.perfil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListaPerfilDto implements Serializable {
    private Long idPerfil;
    private String nome;
}
