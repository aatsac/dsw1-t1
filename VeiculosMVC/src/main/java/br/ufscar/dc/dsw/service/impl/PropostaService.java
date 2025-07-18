// br/ufscar/dc/dsw/service/impl/PropostaService.java
package br.ufscar.dc.dsw.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IPropostaService;

@Service
@Transactional(readOnly = true)
public class PropostaService implements IPropostaService {

    @Autowired
    private IPropostaDAO dao;

    @Override
    public Proposta buscarPorId(Long id) {
        return dao.findById(id.longValue());
    }

    @Override
    @Transactional
    public void aceitarProposta(Proposta proposta) {
        // 1) rejeita todas as outras
        dao.rejeitarOutras(
            proposta.getVeiculo().getId(),
            proposta.getId() == null ? -1L : proposta.getId());
        // 2) marca esta como aceita
        proposta.setStatus(Proposta.Status.ACEITO);
        dao.save(proposta);
    }

    @Override
    public List<Proposta> buscarTodos() {
        return dao.findAll();
    }

    @Override
    public List<Proposta> buscarPorCliente(Long idCliente) {
        return dao.findByClienteId(idCliente);
    }

    @Override
    public List<Proposta> buscarPorVeiculo(Long idVeiculo) {
        return dao.findByVeiculoId(idVeiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proposta> buscarPorLoja(Long idLoja) {
        return dao.findByVeiculoLojaId(idLoja);
    }

    @Override
    public boolean existePropostaAberta(Long clienteId, Long veiculoId) {
        return dao.existsByClienteIdAndStatusAndVeiculoId(clienteId, Proposta.Status.ABERTO, veiculoId);
    }

    @Override
    @Transactional(readOnly = false)
    public void salvar(Proposta proposta) {
        dao.save(proposta);
    }

    @Override
    @Transactional(readOnly = false)
    public void excluir(Long id) {
        dao.deleteById(id);
    }
}
