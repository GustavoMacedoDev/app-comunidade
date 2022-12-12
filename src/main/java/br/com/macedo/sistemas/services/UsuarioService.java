package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.usuario.CadastraUsuarioDto;
import br.com.macedo.sistemas.domain.dto.usuario.ListaUsuarioDto;
import br.com.macedo.sistemas.domain.dto.usuario.LoginDto;
import br.com.macedo.sistemas.domain.entities.MembroEntity;
import br.com.macedo.sistemas.domain.entities.UsuarioEntity;
import br.com.macedo.sistemas.domain.enums.PerfilEnum;
import br.com.macedo.sistemas.repository.UsuarioRepository;
import br.com.macedo.sistemas.utils.exceptions.ObjectNotFoundException;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;
import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioService {

    @Inject
    MembroService membroService;

    @Inject
    UsuarioRepository usuarioRepository;

    @Transactional
    public MensagemResposta cadastraUsuario(CadastraUsuarioDto cadastraUsuarioDto) {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setEmail(cadastraUsuarioDto.getEmail());
        usuario.setPassword(cadastraUsuarioDto.getPassword());
        usuario.setMembro(buscaMembro(cadastraUsuarioDto.getIdMembro()));
        usuario.setRole(cadastraUsuarioDto.getRole());
        usuario.setPerfilEnum(PerfilEnum.ROLE_ADMIN);

        try {
            usuario.persist();
        } catch (PersistenceException e) {
            throw new PersistenceException("Erro ao executar transação" + e.getMessage());
        }

        return new MensagemResposta(usuario.getIdUsuario(), "Usuario cadastrado com sucesso");
    }

    private MembroEntity buscaMembro(Long idMembro) {
        return membroService.buscaMembroPorId(idMembro);
    }

    public List<ListaUsuarioDto> listaUsuarios() {
        List<UsuarioEntity> listaUsuarios = UsuarioEntity.listAll();

        List<ListaUsuarioDto> listaUsuariosResponse = new ArrayList<>();
        for(UsuarioEntity usuario : listaUsuarios) {
            ListaUsuarioDto listaUsuarioDto = new ListaUsuarioDto();
            listaUsuarioDto.setIdUsuario(usuario.getIdUsuario());
            listaUsuarioDto.setEmail(usuario.getEmail());
            listaUsuarioDto.setPassword(usuario.getPassword());
            listaUsuarioDto.setRole(usuario.getRole());

            listaUsuariosResponse.add(listaUsuarioDto);
        }

        return listaUsuariosResponse;
    }

    public boolean validaUsuario(LoginDto loginDto) {
        UsuarioEntity usuarioEntity = buscaUsuarioPorEmail(loginDto.getEmail());

        System.out.println(usuarioEntity.getPassword());
        System.out.println(loginDto.getPassword());


       return usuarioEntity.getPassword().equals(loginDto.getPassword());

    }

    public UsuarioEntity buscaUsuarioPorEmail(String loginDto) {
        return usuarioRepository.buscaUsuarioPeloEmail(loginDto)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
    }

    public Optional<UsuarioEntity> buscaUserPorEmail(String loginDto) {
        return usuarioRepository.buscaUsuarioPeloEmail(loginDto);
    }
}
