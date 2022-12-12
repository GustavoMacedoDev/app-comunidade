package br.com.macedo.sistemas.domain.dto.usuario;

import br.com.macedo.sistemas.domain.enums.PerfilEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto implements Serializable {
    private String email;
    private String password;
    private Set<PerfilEnum> roles;
}
