// br/ufscar/dc/dsw/domain/Cliente.java
package br.ufscar.dc.dsw.domain;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Cliente")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario {

    @NotBlank(message = "{NotBlank.cliente.cpf}")
    @Size(min = 14, max = 14)
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(length = 20)
    private String telefone;

    public enum Sexo { M, F, O }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private Sexo sexo = Sexo.O;

    @PastOrPresent
    private LocalDate dataNascimento;

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
}
