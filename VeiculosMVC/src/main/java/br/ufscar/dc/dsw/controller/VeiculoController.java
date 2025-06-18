package br.ufscar.dc.dsw.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Veiculo;
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
    
    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("veiculos", veiculoService.buscarTodos());
        return "veiculo/lista";
    }
    
    @PostMapping("/salvar")
    public String salvar(
            @Valid Veiculo veiculo, 
            BindingResult result, 
            RedirectAttributes attr,
            ModelMap model) {
        
        if (result.hasErrors()) {
            model.addAttribute("lojas", listaLojas());
            return "veiculo/cadastro";
        }
        
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
