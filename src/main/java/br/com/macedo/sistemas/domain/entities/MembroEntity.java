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
import java.time.LocalDate;

@Table(name = "membro")
@Entity
@Getter @Setter
public class MembroEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_membro", nullable = false)
    private Long idMembro;

    private String nome;

    private String cpf;

    private String endereco;

    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private CargoEntity cargo;
}
