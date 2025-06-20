package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IVeiculoDAO;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.spec.IVeiculoService;

@Service
@Transactional(readOnly = false)
public class VeiculoService implements IVeiculoService {

    @Autowired
    private IVeiculoDAO dao;

    @Override
    @Transactional(readOnly = true)
    public Veiculo buscarPorId(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Veiculo> buscarTodos() {
        return dao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Veiculo> buscarPorModelo(String modelo) {
        if (modelo == null || modelo.isBlank()) {
            return dao.findAll();
        }
        return dao.findByModeloContaining(modelo);
    }

    @Override
    public List<Veiculo> buscarPorLoja(Long lojaId) {
        return dao.findAllByLojaId(lojaId);
    }

    @Override
    public void salvar(Veiculo veiculo) {
        dao.save(veiculo);
    }

    @Override
    public void excluir(Long id) {
        dao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean veiculoTemProposta(Long id) {
        Veiculo v = dao.findById(id).orElse(null);
        return v != null && v.getPropostas() != null && !v.getPropostas().isEmpty();
    }
}
