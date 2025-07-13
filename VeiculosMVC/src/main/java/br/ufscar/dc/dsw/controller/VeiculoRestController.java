package br.ufscar.dc.dsw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

@CrossOrigin
@RestController
@RequestMapping("/api/veiculos")
public class VeiculoRestController {

    @Autowired
    private IVeiculoService service;

    /**
     * Cria um novo veículo para a loja de id = {id}.
     * POST /api/veiculos/lojas/{id}
     */
    @PostMapping("/lojas/{id}")
    public ResponseEntity<Veiculo> criarParaLoja(
            @PathVariable("id") long lojaId,
            @RequestBody Veiculo veiculo) {
        // associa o veículo à loja
        Loja loja = new Loja();
        loja.setId(lojaId);
        veiculo.setLoja(loja);

        service.salvar(veiculo);
        return ResponseEntity.ok(veiculo);
    }

    /**
     * Retorna a lista de veículos da loja de id = {id}.
     * GET /api/veiculos/lojas/{id}
     */
    @GetMapping("/lojas/{id}")
    public ResponseEntity<List<Veiculo>> listarPorLoja(@PathVariable("id") long lojaId) {
        List<Veiculo> lista = service.buscarPorLoja(lojaId);
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    /**
     * Retorna a lista de veículos cujo modelo contém {nome}.
     * GET /api/veiculos/modelos/{nome}
     */
    @GetMapping("/modelos/{nome}")
    public ResponseEntity<List<Veiculo>> listarPorModelo(@PathVariable("nome") String modelo) {
        List<Veiculo> lista = service.buscarPorModelo(modelo);
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }
}
