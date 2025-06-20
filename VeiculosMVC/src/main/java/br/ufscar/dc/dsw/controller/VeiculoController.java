package br.ufscar.dc.dsw.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;
import br.ufscar.dc.dsw.service.spec.ILojaService;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {
    
    @Autowired
    private IVeiculoService veiculoService;
    
    @Autowired
    private ILojaService lojaService;

    @ModelAttribute("lojas")
    public List<Loja> listaLojas() {
        return lojaService.buscarTodos();
    }
    
    @GetMapping("/cadastrar")
    public String cadastrar(Veiculo veiculo) {
        return "veiculo/cadastro";
    }

    private Loja getLojaLogada() {
        UsuarioDetails ud = (UsuarioDetails)
            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (Loja) ud.getUsuario();
    }
    
    @GetMapping("/listar")
    public String listar(ModelMap model) {

        // pega o usuário logado e sua role
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UsuarioDetails ud = (UsuarioDetails) auth.getPrincipal();
        String role = ud.getAuthorities().iterator().next().getAuthority();

        List<Veiculo> lista;
        if ("ADMIN".equals(role)) {
            // se for admin, lista todos
            lista = veiculoService.buscarTodos();
        } else {
            // se for loja, lista só os da loja logada
            Loja loja = (Loja) ud.getUsuario();
            lista = veiculoService.buscarPorLoja(loja.getId());
        }

        model.addAttribute("veiculos", lista);
        return "veiculo/lista";
    }
    
@PostMapping("/salvar")
public String salvar(
        @Valid Veiculo veiculo,
        BindingResult result,
        RedirectAttributes attr,
        ModelMap model) {
    
    if (result.hasErrors()) {
        // Só reexibe o dropdown para o ADMIN, não para a loja
        return "veiculo/cadastro";
    }
    
    // força associação à loja logada:
    veiculo.setLoja(getLojaLogada());
    veiculoService.salvar(veiculo);
    attr.addFlashAttribute("sucess", "Veículo inserido com sucesso.");
    return "redirect:/veiculos/listar";
}

    @GetMapping("/editar/{id}")
    public String preEditar(
            @PathVariable("id") Long id, 
            ModelMap model) {
        
        model.addAttribute("veiculo", veiculoService.buscarPorId(id));
        model.addAttribute("lojas", listaLojas());
        return "veiculo/cadastro";
    }
    
    @PostMapping("/editar")
    public String editar(
            @Valid Veiculo veiculo, 
            BindingResult result, 
            RedirectAttributes attr,
            ModelMap model) {
        
        if (result.hasErrors()) {
            model.addAttribute("lojas", listaLojas());
            return "veiculo/cadastro";
        }
        
        veiculoService.salvar(veiculo);
        attr.addFlashAttribute("sucess", "Veículo editado com sucesso.");
        return "redirect:/veiculos/listar";
    }
    
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, ModelMap model, RedirectAttributes attr) {
        if (veiculoService.veiculoTemProposta(id)) {
            attr.addFlashAttribute("fail", "Veículo não pode ser excluído pois possui propostas associadas.");
        } else {
            veiculoService.excluir(id);
            attr.addFlashAttribute("sucess", "Veículo excluído com sucesso.");
        }
        return "redirect:/veiculos/listar";
    }
}
