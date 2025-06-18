package br.ufscar.dc.dsw.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Loja;

@SuppressWarnings("unchecked")
public interface ILojaDAO extends CrudRepository<Loja, Long>{
    Optional<Loja> findById(Long id);
    Loja findByCnpj(String CNPJ);
    List<Loja> findAll();
    Loja save(Loja editora);
    void deleteById(Long id);
}