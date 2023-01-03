package br.com.macedo.sistemas.domain.entities.enderecos;

import br.com.macedo.sistemas.domain.entities.MembroEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "endereco")
@Getter
@Setter
public class EnderecoEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco", nullable = false)
    private Long idEndereco;

    private String complemento;

    private String numero;

    private String cep;

    @ManyToOne
    @JoinColumn(name = "id_logradouro")
    private LogradouroEntity logradouro;

    @ManyToOne
    @JoinColumn(name = "id_bairro")
    private BairroEntity bairro;

    @ManyToOne
    @JoinColumn(name = "id_membro")
    private MembroEntity membro;

}
