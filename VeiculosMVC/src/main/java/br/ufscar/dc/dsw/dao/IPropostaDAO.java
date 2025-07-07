// br/ufscar/dc/dsw/dao/IPropostaDAO.java
package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Proposta;

@SuppressWarnings("unchecked")
public interface IPropostaDAO extends CrudRepository<Proposta, Long> {
    Proposta findById(long id);

    @Modifying
    @Query("UPDATE Proposta p "
         + "SET p.status = br.ufscar.dc.dsw.domain.Proposta.Status.NAO_ACEITO "
         + "WHERE p.veiculo.id = :veiculoId AND p.id <> :acceptedId")
    void rejeitarOutras( @Param("veiculoId") Long veiculoId, @Param("acceptedId") Long acceptedId);
    
    List<Proposta> findAll();
    List<Proposta> findByClienteId(Long idCliente);
    List<Proposta> findByVeiculoId(Long idVeiculo);
    List<Proposta> findByVeiculoLojaId(Long lojaId);
    boolean existsByClienteIdAndStatusAndVeiculoId(Long clienteId, Proposta.Status status, Long veiculoId);
    Proposta save(Proposta proposta);
    void deleteById(Long id);
}
