package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.token.DataDto;
import br.com.macedo.sistemas.domain.dto.token.TokenDto;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class GeraTokenJwt {

    @Inject
    JsonWebToken jwt;

    public DataDto geraTokent() {

        String jwt =
                Jwt.claims()
                .issuer("comunidade-app")
                .subject("comunidade")
                .expiresAt(System.currentTimeMillis() + 3600)
                .groups("admin")
                .sign();

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwt);
        DataDto dataDto = new DataDto();
        dataDto.setToken(tokenDto);

        return dataDto;

    }
}
