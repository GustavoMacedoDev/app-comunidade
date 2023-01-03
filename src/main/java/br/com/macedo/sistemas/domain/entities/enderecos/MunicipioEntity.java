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

@Entity
@Table(name = "municipio")
@Getter
@Setter
public class MunicipioEntity extends PanacheEntityBase {

    @Id
    @Column(name = "id_municipio", nullable = false)
    private Long idMunicipio;

    private String nome;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_estado")
    private EstadoEntity estado;

}
