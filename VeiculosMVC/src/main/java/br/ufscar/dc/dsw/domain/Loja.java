// br/ufscar/dc/dsw/domain/Loja.java
package br.ufscar.dc.dsw.domain;

import br.ufscar.dc.dsw.validation.UniqueCNPJ;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "Loja")
@PrimaryKeyJoinColumn(name = "id")
@UniqueCNPJ(message = "{Unique.loja.CNPJ}")
public class Loja extends Usuario {

    @NotBlank
    @Size(min = 18, max = 18, message = "{Size.loja.CNPJ}")
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @OneToMany(mappedBy = "loja")
    private List<Veiculo> veiculos;

    // getters e setters
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public List<Veiculo> getVeiculos() {
        return veiculos;
    }
    public void setVeiculos(List<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }
}
