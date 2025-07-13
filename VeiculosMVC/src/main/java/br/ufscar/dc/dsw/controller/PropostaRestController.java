package br.ufscar.dc.dsw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IPropostaService;

@CrossOrigin
@RestController
@RequestMapping("/api/propostas")
public class PropostaRestController {

    @Autowired
    private IPropostaService service;

    /**
     * Retorna todas as propostas de compra para um veículo específico.
     * GET /api/propostas/veiculos/{id}
     */
    @GetMapping("/veiculos/{id}")
    public ResponseEntity<List<Proposta>> listarPorVeiculo(@PathVariable("id") long veiculoId) {
        List<Proposta> lista = service.buscarPorVeiculo(veiculoId);
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    /**
     * Retorna todas as propostas feitas por um cliente específico.
     * GET /api/propostas/clientes/{id}
     */
    @GetMapping("/clientes/{id}")
    public ResponseEntity<List<Proposta>> listarPorCliente(@PathVariable("id") long clienteId) {
        List<Proposta> lista = service.buscarPorCliente(clienteId);
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }
}
