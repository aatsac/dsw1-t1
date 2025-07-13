// br/ufscar/dc/dsw/controller/PropostaController.java
package br.ufscar.dc.dsw.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.security.UsuarioDetails;
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

        private Cliente getClienteLogado() {
        UsuarioDetails ud = (UsuarioDetails)
            SecurityContextHolder.getContext()
                                 .getAuthentication()
                                 .getPrincipal();
        return (Cliente) ud.getUsuario();
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Long veiculoId, Proposta proposta) {
        proposta.setCliente(getClienteLogado());
        proposta.setDataCompra(java.time.LocalDate.now());
        proposta.setVeiculo(veiculoService.buscarPorId(veiculoId));
        proposta.setStatus(Proposta.Status.ABERTO);
        return "proposta/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        // Se não estiver autenticado com nosso UserDetails, redireciona ao login
        if (!(principal instanceof UsuarioDetails)) {
            return "redirect:/login";
        }

        UsuarioDetails ud = (UsuarioDetails) principal;
        Object usuario = ud.getUsuario();
        List<Proposta> lista = new ArrayList<>();

        if (usuario instanceof Loja) {
            Loja loja = (Loja) usuario;
            lista = propostaService.buscarPorLoja(loja.getId());
        } else if (usuario instanceof Cliente) {
            // Clientes veem apenas suas próprias propostas
            Cliente cliente = (Cliente) usuario;
            lista = propostaService.buscarPorCliente(cliente.getId());
        }

        model.addAttribute("propostas", lista);
        return "proposta/lista";
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid Proposta proposta, 
            BindingResult result, 
            ModelMap model, 
            RedirectAttributes attr) {
        
        Cliente cliente = getClienteLogado();
        Long veiculoId = proposta.getVeiculo().getId();

        // 1) Se for criação (id == null) e já existir proposta em aberto -> erro
        if (proposta.getId() == null && propostaService.existePropostaAberta(cliente.getId(), veiculoId)) {
            attr.addFlashAttribute("fail", "proposta.existe");
            return "redirect:/propostas/listar";
        }

        // 2) Se houver erros (validação ou regra de negócio), retorna ao formulário
        if (result.hasErrors()) {
            model.addAttribute("statusList", Proposta.Status.values());
            return "proposta/cadastro";
        }

        // 3) Continua salvando normalmente
        proposta.setCliente(cliente);
        proposta.setDataCompra(LocalDate.now());
        propostaService.salvar(proposta);
        attr.addFlashAttribute("sucess", "proposta.save.sucess");
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
    public String editar(
            @ModelAttribute("proposta") Proposta proposta,
            BindingResult result,
            ModelMap model,
            RedirectAttributes attr) {

        // Recupera o usuário logado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UsuarioDetails ud = (UsuarioDetails) auth.getPrincipal();
        Object usuario = ud.getUsuario();

        // Se for LOJA, atualiza apenas o status
        if (usuario instanceof Loja) {
            Proposta existente = propostaService.buscarPorId(proposta.getId());
            existente.setStatus(proposta.getStatus());
            if (proposta.getStatus() == Proposta.Status.ACEITO) {
                // chama o método que rejeita as outras e salva esta como ACEITO
                propostaService.aceitarProposta(existente);
            } else {
                // apenas atualiza para ABERTO ou NAO_ACEITO
                propostaService.salvar(existente);
            }
            attr.addFlashAttribute("sucess", "proposta.status.edit.sucess");
            return "redirect:/propostas/listar";
        }

        // Caso CONTRÁRIO (é CLIENTE), faz validação normal de valor/condições
        if (result.hasErrors()) {
            // Recarrega a lista de status para o select, caso dê erro
            model.addAttribute("statusList", Proposta.Status.values());
            return "proposta/cadastro";
        }

        propostaService.salvar(proposta);
        attr.addFlashAttribute("sucess",
            proposta.getId() == null
                ? "proposta.save.sucess"
                : "proposta.edit.sucess");
        return "redirect:/propostas/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, ModelMap model) {
        propostaService.excluir(id);
        model.addAttribute("sucess", "proposta.delete.sucess");
        return listar(model);
    }

    // Listar propostas de um cliente específico (opcional)
    @GetMapping("/cliente/{idCliente}")
    public String listarPorCliente(@PathVariable Long idCliente, ModelMap model) {
        model.addAttribute("propostas", propostaService.buscarPorCliente(idCliente));
        return "proposta/lista";
    }
}
