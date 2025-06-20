package br.ufscar.dc.dsw.service.spec;

import java.util.List;
import br.ufscar.dc.dsw.domain.Veiculo;

public interface IVeiculoService {
    Veiculo buscarPorId(Long id);
    List<Veiculo> buscarTodos();
    List<Veiculo> buscarPorModelo(String modelo);
    List<Veiculo> buscarPorLoja(Long lojaId);
    void salvar(Veiculo veiculo);
    void excluir(Long id);
    boolean veiculoTemProposta(Long id);
}
