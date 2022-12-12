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

@Entity
@Table(name = "membro_ministerio")
@Getter
@Setter
public class MembroMinisterioEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_membro_ministerio", nullable = false)
    private Long idMembroMinisterio;

    private LocalDate dataEntrada;

    //private LocalDate dataSaida;

    private int responsavel;

    @ManyToOne
    @JoinColumn(name = "id_membro")
    private MembroEntity membro;

    @ManyToOne()
    @JoinColumn(name = "id_ministerio")
    private MinisterioEntity ministerio;

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private PerfilEntity perfil;
}
