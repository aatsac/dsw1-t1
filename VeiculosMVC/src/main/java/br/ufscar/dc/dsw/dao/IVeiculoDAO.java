package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import br.ufscar.dc.dsw.domain.Proposta.Status;

import br.ufscar.dc.dsw.domain.Veiculo;

public interface IVeiculoDAO extends CrudRepository<Veiculo, Long> {
    List<Veiculo> findAll();
    List<Veiculo> findAllByLojaId(Long lojaId);
    Veiculo findByPlaca(String placa);
    Veiculo findByChassi(String chassi);
        @Query("""
        SELECT v
        FROM Veiculo v
        WHERE NOT EXISTS (
          SELECT 1
          FROM Proposta p
          WHERE p.veiculo = v
            AND p.status = :statusAceito
        )
        """)
    List<Veiculo> findDisponiveis(@Param("statusAceito") Status statusAceito);

    @Query("""
        SELECT v
        FROM Veiculo v
        WHERE v.modelo LIKE %:modelo%
          AND NOT EXISTS (
            SELECT 1
            FROM Proposta p
            WHERE p.veiculo = v
              AND p.status = :statusAceito
          )
        """)
    List<Veiculo> findDisponiveisByModelo(
        @Param("modelo") String modelo,
        @Param("statusAceito") Status statusAceito);
}
