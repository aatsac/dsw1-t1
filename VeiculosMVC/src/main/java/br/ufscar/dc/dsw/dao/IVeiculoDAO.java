package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufscar.dc.dsw.domain.Veiculo;

public interface IVeiculoDAO extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByModeloContaining(String modelo);
    List<Veiculo> findAllByLojaId(Long lojaId);
    Veiculo findByPlaca(String placa);
    Veiculo findByChassi(String chassi);

}
