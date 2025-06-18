// br/ufscar/dc/dsw/dao/IVeiculoDAO.java
package br.ufscar.dc.dsw.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import br.ufscar.dc.dsw.domain.Veiculo;

@SuppressWarnings("unchecked")
public interface IVeiculoDAO extends CrudRepository<Veiculo, Long> {
    Veiculo findById(long id);
    Veiculo findByPlaca(String placa);
    List<Veiculo> findAll();
    List<Veiculo> findByLojaCnpj(String cnpjLoja);
    Veiculo save(Veiculo veiculo);
    void deleteById(Long id);
}
