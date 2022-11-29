package br.com.macedo.sistemas.utils.mensagens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class MensagemResposta {

    private Long codigo;
    private String mensagem;
}
