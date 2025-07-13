package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.time.LocalDate;
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

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.service.spec.IClienteService;

@CrossOrigin
@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private IClienteService service;

    @Autowired
    private ObjectMapper objectMapper;  // para validar JSON

    private boolean isJSONValid(String json) {
        try {
            return objectMapper.readTree(json) != null;
        } catch (IOException e) {
            return false;
        }
    }

    private void parse(Cliente cliente, JSONObject json) {
        Object id = json.get("id");
        if (id != null) {
            if (id instanceof Integer) {
                cliente.setId(((Integer) id).longValue());
            } else {
                cliente.setId((Long) id);
            }
        }
        cliente.setEmail((String) json.get("email"));
        cliente.setPassword(encoder.encode((String) json.get("senha")));
        cliente.setNome((String) json.get("nome"));
        cliente.setPapel("CLIENTE");
        cliente.setCpf((String) json.get("cpf"));
        cliente.setTelefone((String) json.get("telefone"));
        cliente.setSexo(Cliente.Sexo.valueOf((String) json.get("sexo"))); // supondo que o sexo seja passado como string
        // dataNascimento no formato "yyyy-MM-dd"
        if (json.get("dataNascimento") != null) {
            cliente.setDataNascimento(LocalDate.parse((String) json.get("dataNascimento")));
        }
        // papel fixo como CLIENTE
        cliente.setPapel("CLIENTE");
        // senha: só se vier explicitamente
        if (json.get("password") != null) {
            cliente.setPassword((String) json.get("password"));
        }
    }

    /** READ ALL */
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> lista = service.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    /** READ by ID */
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable("id") long id) {
        Cliente cliente = service.buscarPorId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    /** CREATE */
    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody JSONObject json) {
        try {
            if (!isJSONValid(json.toString())) {
                return ResponseEntity.badRequest().body(null);
            }
            Cliente cliente = new Cliente();
            parse(cliente, json);
            service.salvar(cliente);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    /** UPDATE */
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable("id") long id, @RequestBody JSONObject json) {
        try {
            if (!isJSONValid(json.toString())) {
                return ResponseEntity.badRequest().body(null);
            }
            Cliente cliente = service.buscarPorId(id);
            if (cliente == null) {
                return ResponseEntity.notFound().build();
            }
            parse(cliente, json);
            service.salvar(cliente);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    /** DELETE */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable("id") long id) {
        Cliente cliente = service.buscarPorId(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
