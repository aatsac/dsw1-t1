package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Proposta")
public class Proposta extends AbstractEntity<Long> {

    @NotNull
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @NotNull
    @Column(columnDefinition = "TEXT", nullable = false)
    private String condicoesPgto;

    @NotNull
    @Column(name = "dataCompra", nullable = false)
    private LocalDate dataCompra;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idVeiculo", referencedColumnName = "id", nullable = false)
    private Veiculo veiculo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idCliente", referencedColumnName = "id", nullable = false)
    private Cliente cliente;

    public enum Status { ABERTO, ACEITO, NAO_ACEITO }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ABERTO;

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
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
}
