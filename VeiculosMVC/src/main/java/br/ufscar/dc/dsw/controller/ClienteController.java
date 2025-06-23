package br.ufscar.dc.dsw.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.service.spec.IClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private IClienteService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Cliente cliente) {
	    // garante que o hidden *{papel} saia com "CLIENTE"
	    cliente.setPapel("CLIENTE");
	    return "cliente/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("clientes",service.buscarTodos());
		return "cliente/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "cliente/cadastro";
		}
		cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
		
		service.salvar(cliente);
		attr.addFlashAttribute("sucess", "Cliente inserida com sucesso.");
		return "redirect:/clientes/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cliente", service.buscarPorId(id));
		return "cliente/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		
		// Apenas rejeita se o problema não for com o CPF (CPF campo read-only) 
		if (result.getFieldErrorCount() > 2 || (result.getFieldError("cpf") == null && result.getFieldError("email") == null)) {
			return "cliente/cadastro";
		}

		service.salvar(cliente);
		attr.addFlashAttribute("sucess", "Cliente editado com sucesso.");
		return "redirect:/clientes/listar";
	}
		
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {
        if (service.clienteTemProposta(id)) {
            attr.addFlashAttribute("fail", "Cliente não pode ser excluído pois possui propostas associadas.");
        } else {
            service.excluir(id);
            attr.addFlashAttribute("sucess", "Cliente excluído com sucesso.");
        }
        return "redirect:/clientes/listar";
    }
}