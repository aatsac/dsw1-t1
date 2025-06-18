package br.ufscar.dc.dsw.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.ILojaService;

@Controller
@RequestMapping("/lojas")
public class LojaController {
	
	@Autowired
	private ILojaService service;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Loja loja) {
		// garante que o hidden *{papel} saia com "LOJA"
		loja.setPapel("LOJA");
		return "loja/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("lojas",service.buscarTodos());
		return "loja/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Loja loja, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "loja/cadastro";
		}
		
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
		if (result.hasErrors()) {
			return "loja/cadastro";
		}

		Loja lojaExistente = service.buscarPorId(loja.getId());
		if (lojaExistente != null) {
			lojaExistente.setNome(loja.getNome());
			lojaExistente.setEmail(loja.getEmail());
			lojaExistente.setPassword(loja.getPassword());
			lojaExistente.setDescricao(loja.getDescricao());
			// Não altere o CNPJ!
			service.salvar(lojaExistente);
			attr.addFlashAttribute("sucess", "Loja editada com sucesso.");
		} else {
			attr.addFlashAttribute("fail", "Loja não encontrada.");
		}
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