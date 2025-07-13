package br.ufscar.dc.dsw.service.spec;

import java.util.List;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.domain.Proposta.Status;

public interface IVeiculoService {
    Veiculo buscarPorId(Long id);
    List<Veiculo> buscarTodos();
    List<Veiculo> buscarPorLoja(Long lojaId);
    List<Veiculo> buscarPorModelo(String modelo);
    List<Veiculo> buscarDisponiveis(Status statusAceito);
    List<Veiculo> buscarDisponiveisPorModelo(String modelo, Status statusAceito);
    void salvar(Veiculo veiculo);
    void excluir(Long id);
    boolean veiculoTemProposta(Long id);
}
