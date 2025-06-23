package br.ufscar.dc.dsw.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.ILojaService;

@Controller
@RequestMapping("/lojas")
public class LojaController {
	
	@Autowired
	private ILojaService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Loja loja) {
		loja.setPapel("LOJA");
		return "loja/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("lojas", service.buscarTodos());
		return "loja/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Loja loja, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "loja/cadastro";
		}
		loja.setPassword(passwordEncoder.encode(loja.getPassword()));
		
		service.salvar(loja);
		attr.addFlashAttribute("sucess", "Loja inserida com sucesso.");
		return "redirect:/lojas/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("loja", service.buscarPorId(id));
		return "loja/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Loja loja, BindingResult result, RedirectAttributes attr) {
		
		// Apenas rejeita se o problema não for com o CNPJ (CNPJ campo read-only) 
		
		if (result.getFieldErrorCount() > 2 || (result.getFieldError("cnpj") == null && result.getFieldError("email") == null)) {
			return "loja/cadastro";
		}

		service.salvar(loja);
		attr.addFlashAttribute("sucess", "Loja editada com sucesso.");
		return "redirect:/lojas/listar";
	}

	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
	    if (service.lojaTemVeiculos(id)) {
	        model.addAttribute("fail", "Loja não excluída. Possui veículo(s) vinculado(s).");
	    } else {
	        service.excluir(id);
	        model.addAttribute("sucess", "Loja excluída com sucesso.");
	    }
	    return listar(model);
	}
}
