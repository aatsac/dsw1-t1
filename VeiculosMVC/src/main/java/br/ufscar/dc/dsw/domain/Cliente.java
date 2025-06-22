// br/ufscar/dc/dsw/domain/Cliente.java
package br.ufscar.dc.dsw.domain;

import java.time.LocalDate;
import java.util.List;

//import br.ufscar.dc.dsw.validation.UniqueCpf;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

@SuppressWarnings("serial")
@Entity
@Table(name = "Cliente")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario {

    @NotBlank
    //@UniqueCpf(message = "{Unique.cliente.cpf}")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "{Pattern.cliente.cpf}")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{3,4}", message = "{Pattern.cliente.telefone}")
    @Column(length = 15)
    private String telefone;

    public enum Sexo { M, F, O }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private Sexo sexo = Sexo.O;


    @Past(message = "{Past.cliente.dataNascimento}")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "cliente")
    private List<Proposta> propostas;

    // getters e setters
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public Sexo getSexo() {
        return sexo;
    }
    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public List<Proposta> getPropostas() {
        return propostas;
    }
    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }
}
