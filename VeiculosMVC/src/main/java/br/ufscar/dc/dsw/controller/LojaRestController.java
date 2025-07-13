package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.ILojaService;

@CrossOrigin
@RestController
@RequestMapping("/api/lojas")
public class LojaRestController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ILojaService service;

    // Valida JSON
    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    // Constrói/atualiza o objeto Loja a partir do JSON recebido
    private void parse(Loja loja, JSONObject json) {
        Object id = json.get("id");
        if (id != null) {
            if (id instanceof Integer) {
                loja.setId(((Integer) id).longValue());
            } else {
                loja.setId((Long) id);
            }
        }
        loja.setNome((String) json.get("nome"));
        loja.setEmail((String) json.get("email"));
        loja.setCnpj((String) json.get("cnpj")); 
        loja.setDescricao((String) json.get("descricao"));
        loja.setPassword(encoder.encode((String) json.get("senha")));
        loja.setPapel("LOJA");
    }

    /** Read All */
    @GetMapping
    public ResponseEntity<List<Loja>> listar() {
        List<Loja> lista = service.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    /** Read by ID */
    @GetMapping("/{id}")
    public ResponseEntity<Loja> buscar(@PathVariable("id") long id) {
        Loja loja = service.buscarPorId(id);
        if (loja == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loja);
    }

    /** Create */
    @PostMapping
    public ResponseEntity<Loja> criar(@RequestBody JSONObject json) {
        try {
            if (!isJSONValid(json.toString())) {
                return ResponseEntity.badRequest().body(null);
            }
            Loja loja = new Loja();
            parse(loja, json);
            service.salvar(loja);
            return ResponseEntity.ok(loja);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    /** Update */
    @PutMapping("/{id}")
    public ResponseEntity<Loja> atualizar(@PathVariable("id") long id, @RequestBody JSONObject json) {
        try {
            if (!isJSONValid(json.toString())) {
                return ResponseEntity.badRequest().body(null);
            }
            Loja loja = service.buscarPorId(id);
            if (loja == null) {
                return ResponseEntity.notFound().build();
            }
            parse(loja, json);
            service.salvar(loja);
            return ResponseEntity.ok(loja);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    /** Delete */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") long id) {
        Loja loja = service.buscarPorId(id);
        if (loja == null) {
            return ResponseEntity.notFound().build();
        }
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
