// br/ufscar/dc/dsw/domain/Proposta.java
package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "Proposta")
public class Proposta extends AbstractEntity<Long> {

    @NotNull
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(columnDefinition = "TEXT")
    private String condicoesPgto;

    @NotNull
    @Column(name = "dataCompra", nullable = false)
    private LocalDate dataCompra;

    /**
     * Relacionamento com Veiculo via placa
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "idVeiculo", referencedColumnName = "id", nullable = false)
    private Veiculo veiculo;

    /**
     * Relacionamento com Cliente via CPF
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "idCliente", referencedColumnName = "id", nullable = false)
    private Cliente cliente;

    // --- getters e setters ---
    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public String getCondicoesPgto() {
        return condicoesPgto;
    }
    public void setCondicoesPgto(String condicoesPgto) {
        this.condicoesPgto = condicoesPgto;
    }
    public LocalDate getDataCompra() {
        return dataCompra;
    }
    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }
    public Veiculo getVeiculo() {
        return veiculo;
    }
    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
