package br.com.macedo.sistemas.domain.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CadastraUsuarioDto implements Serializable {
    private String email;
    private String password;
    private String role;
    private Long idMembro;
}
