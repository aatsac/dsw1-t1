package br.ufscar.dc.dsw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.domain.Proposta.Status;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
	
    @Autowired
    private IVeiculoService service;

    @GetMapping("")
    public String listar(
            @RequestParam(value = "modelo", required = false) String modelo,
            ModelMap model) {

        List<Veiculo> lista;
        if (modelo == null || modelo.isBlank()) {
            lista = service.buscarDisponiveis(Status.ACEITO);
        } else {
            lista = service.buscarDisponiveisPorModelo(modelo, Status.ACEITO);
        }
        model.addAttribute("veiculos", lista);
        return "index";
    }
}
