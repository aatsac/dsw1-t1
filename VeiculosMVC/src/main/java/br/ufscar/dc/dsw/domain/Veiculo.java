// br/ufscar/dc/dsw/domain/Veiculo.java
package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Veiculo")
public class Veiculo extends AbstractEntity<Long> {

    @NotBlank
    @Size(max = 10)
    @Column(nullable = false, unique = true, length = 10)
    private String placa;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String modelo;

    @NotBlank
    @Size(max = 17)
    @Column(nullable = false, unique = true, length = 17)
    private String chassi;

    private Integer ano;

    private Integer quilometragem;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @NotNull
    @Column(precision = 15, scale = 2)
    private BigDecimal valor;

    @OneToMany(mappedBy = "veiculo")
    private List<Proposta> propostas;

    /**
     * Armazena o JSON das URLs das fotos, ex: ["url1","url2",...]
     */
    @Column(columnDefinition = "JSON")
    private String fotos;
    
    @NotNull(message = "Loja é obrigatória")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "idLoja", nullable = false)
    private Loja loja;

    // --- getters e setters ---
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getChassi() {
        return chassi;
    }
    public void setChassi(String chassi) {
        this.chassi = chassi;
    }
    public Integer getAno() {
        return ano;
    }
    public void setAno(Integer ano) {
        this.ano = ano;
    }
    public Integer getQuilometragem() {
        return quilometragem;
    }
    public void setQuilometragem(Integer quilometragem) {
        this.quilometragem = quilometragem;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public String getFotos() {
        return fotos;
    }
    public void setFotos(String fotos) {
        this.fotos = fotos;
    }
    public Loja getLoja() {
        return loja;
    }
    public void setLoja(Loja loja) {
        this.loja = loja;
    }
    public List<Proposta> getPropostas() {
        return propostas;
    }
    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }
}