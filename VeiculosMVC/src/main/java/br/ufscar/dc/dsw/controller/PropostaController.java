// br/ufscar/dc/dsw/controller/PropostaController.java
package br.ufscar.dc.dsw.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.spec.IPropostaService;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

@Controller
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private IPropostaService propostaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IVeiculoService veiculoService;

    @ModelAttribute("clientes")
    public List<Cliente> listaClientes() {
        return clienteService.buscarTodos();
    }

    @ModelAttribute("veiculos")
    public List<Veiculo> listaVeiculos() {
        return veiculoService.buscarTodos();
    }

    @ModelAttribute("statusList")
    public Proposta.Status[] getStatusList() {
        return Proposta.Status.values();
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Proposta proposta) {
        return "proposta/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("propostas", propostaService.buscarTodos());
        return "proposta/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Proposta proposta,
                         BindingResult result,
                         ModelMap model,
                         RedirectAttributes attr) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", listaClientes());
            model.addAttribute("veiculos", listaVeiculos());
            return "proposta/cadastro";
        }
        propostaService.salvar(proposta);
        attr.addFlashAttribute("sucess", "Proposta enviada com sucesso.");
        return "redirect:/propostas/listar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("proposta", propostaService.buscarPorId(id));
        model.addAttribute("clientes", listaClientes());
        model.addAttribute("veiculos", listaVeiculos());
        return "proposta/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid Proposta proposta,
                         BindingResult result,
                         ModelMap model,
                         RedirectAttributes attr) {
        if (result.hasErrors()) {
            model.addAttribute("clientes", listaClientes());
            model.addAttribute("veiculos", listaVeiculos());
            return "proposta/cadastro";
        }
        propostaService.salvar(proposta);
        attr.addFlashAttribute("sucess", "Proposta atualizada com sucesso.");
        return "redirect:/propostas/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, ModelMap model) {
        propostaService.excluir(id);
        model.addAttribute("sucess", "Proposta excluída com sucesso.");
        return listar(model);
    }

    // Listar propostas de um cliente específico (opcional)
    @GetMapping("/cliente/{idCliente}")
    public String listarPorCliente(@PathVariable Long idCliente, ModelMap model) {
        model.addAttribute("propostas", propostaService.buscarPorCliente(idCliente));
        return "proposta/lista";
    }
}
