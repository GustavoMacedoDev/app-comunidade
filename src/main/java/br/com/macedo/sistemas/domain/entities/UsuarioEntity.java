package br.com.macedo.sistemas.domain.entities;

import br.com.macedo.sistemas.domain.enums.PerfilEnum;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "usuario")
@Entity
@Getter
@Setter
@UserDefinition
public class UsuarioEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Username
    private String email;
    @Password
    private String password;
    @Roles
    private String role;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private PerfilEnum perfilEnum;

    @ManyToOne
    @JoinColumn(name = "id_membro")
    private MembroEntity membro;

    public static void add(String email, String password, String role){
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.email = email;
        //usuario.password = BcryptUtil.bcryptHash(password);
        usuario.role = role;
        usuario.persist();
    }
}
