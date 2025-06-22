// br/ufscar/dc/dsw/dao/IUsuarioDAO.java
package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Usuario;

@SuppressWarnings("unchecked")
public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
    Usuario findById(long id);
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario findByEmail(String email);
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    void deleteById(Long id);
}
