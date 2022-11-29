package br.com.macedo.sistemas.domain.entities;

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

@Table(name = "contato")
@Entity
@Getter
@Setter
public class ContatoEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contato", nullable = false)
    private Long idContato;

    private String email;

    private String telefone;

    @ManyToOne
    @JoinColumn(name = "id_membro")
    private MembroEntity membro;
}
