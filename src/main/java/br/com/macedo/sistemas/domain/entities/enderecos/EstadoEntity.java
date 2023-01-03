package br.com.macedo.sistemas.domain.entities.enderecos;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "estado")
@Entity
@Getter
@Setter
public class EstadoEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id_estado", nullable = false)
    private Long idEstado;

    private String nome;

    private String sigla;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_regiao")
    private RegiaoEntity regiao;
}
