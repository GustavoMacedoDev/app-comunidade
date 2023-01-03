package br.com.macedo.sistemas.domain.entities.enderecos;

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
@Table(name = "logradouro")
@Getter
@Setter
public class LogradouroEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_logradouro", nullable = false)
    private Long idLogradouro;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_tipo_logradouro")
    private TipoLogradouroEntity tipoLogradouro;

}
