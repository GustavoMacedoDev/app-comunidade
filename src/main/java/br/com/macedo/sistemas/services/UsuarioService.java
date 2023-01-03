package br.com.macedo.sistemas.services;

import br.com.macedo.sistemas.domain.dto.usuario.AlteraDadosUsuarioDto;
import br.com.macedo.sistemas.domain.dto.usuario.CadastraUsuarioDto;
import br.com.macedo.sistemas.domain.dto.usuario.ListaUsuarioDto;
import br.com.macedo.sistemas.domain.dto.usuario.LoginDto;
import br.com.macedo.sistemas.domain.entities.MembroEntity;
import br.com.macedo.sistemas.domain.entities.UsuarioEntity;
import br.com.macedo.sistemas.domain.enums.PerfilEnum;
import br.com.macedo.sistemas.repository.UsuarioRepository;
import br.com.macedo.sistemas.utils.exceptions.ObjectNotFoundException;
import br.com.macedo.sistemas.utils.mensagens.MensagemResposta;

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
        validaEmailCadastrado(cadastraUsuarioDto.getEmail());

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

    private void validaEmailCadastrado(String email) {
        Optional<UsuarioEntity> usuario = usuarioRepository.buscaUsuarioPeloEmail(email);

        if(usuario.isPresent()) {
            throw new ObjectNotFoundException("Usuario já cadastrado");
        }

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

       return usuarioEntity.getPassword().equals(loginDto.getPassword());

    }

    public UsuarioEntity buscaUsuarioPorEmail(String loginDto) {
        return usuarioRepository.buscaUsuarioPeloEmail(loginDto)
                .orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
    }

    public Optional<UsuarioEntity> buscaUserPorEmail(String loginDto) {
        return usuarioRepository.buscaUsuarioPeloEmail(loginDto);
    }

    public ListaUsuarioDto buscaUsuarioPorId(Long idUsuario) {
        UsuarioEntity usuario = findUsuarioById(idUsuario);

        ListaUsuarioDto listaUsuarioDto = new ListaUsuarioDto();
        listaUsuarioDto.setIdUsuario(usuario.getIdUsuario());
        listaUsuarioDto.setEmail(usuario.getEmail());
        listaUsuarioDto.setRole(usuario.getRole());

        return listaUsuarioDto;

    }

    private UsuarioEntity findUsuarioById(Long idUsuario) {
        Optional<UsuarioEntity> usuarioEntity = UsuarioEntity.findByIdOptional(idUsuario);

        return usuarioEntity.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public MensagemResposta alteraDadosUsuario(Long idUsuario, AlteraDadosUsuarioDto alteraDadosUsuarioDto) {
        UsuarioEntity usuario = findUsuarioById(idUsuario);

        usuario.setEmail(alteraDadosUsuarioDto.getEmail());
        usuario.setPassword(alteraDadosUsuarioDto.getPassword());
        usuario.setRole(alteraDadosUsuarioDto.getRole());

        try {
            usuario.persistAndFlush();
        } catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }

        return new MensagemResposta(usuario.getIdUsuario(), "Dados alterados com sucesso");
    }

    @Transactional
    public MensagemResposta deletaUsuario(Long idUsuario) {
        UsuarioEntity usuario = findUsuarioById(idUsuario);

        try{
            usuario.delete();
        }catch (PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        }

        return new MensagemResposta(999L, "Usuário deletado com sucesso");
    }
}
