// br/ufscar/dc/dsw/service/spec/IPropostaService.java
package br.ufscar.dc.dsw.service.spec;

import java.util.List;
import br.ufscar.dc.dsw.domain.Proposta;

public interface IPropostaService {
    Proposta buscarPorId(Long id);
    List<Proposta> buscarTodos();
    List<Proposta> buscarPorCliente(Long idCliente);
    List<Proposta> buscarPorVeiculo(Long idVeiculo);
    List<Proposta> buscarPorLoja(Long lojaId);
    boolean existePropostaAberta(Long clienteId, Long veiculoId);
    void salvar(Proposta proposta);
    void excluir(Long id);
}
