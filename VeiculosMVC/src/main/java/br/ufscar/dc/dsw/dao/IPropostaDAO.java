// br/ufscar/dc/dsw/dao/IPropostaDAO.java
package br.ufscar.dc.dsw.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import br.ufscar.dc.dsw.domain.Proposta;

@SuppressWarnings("unchecked")
public interface IPropostaDAO extends CrudRepository<Proposta, Long> {
    Proposta findById(long id);
    List<Proposta> findAll();
    List<Proposta> findByClienteId(Long idCliente);
    List<Proposta> findByVeiculoId(Long idVeiculo);
    List<Proposta> findByVeiculoLojaId(Long lojaId);
    boolean existsByClienteIdAndStatusAndVeiculoId(Long clienteId, Proposta.Status status, Long veiculoId);
    Proposta save(Proposta proposta);
    void deleteById(Long id);
}
