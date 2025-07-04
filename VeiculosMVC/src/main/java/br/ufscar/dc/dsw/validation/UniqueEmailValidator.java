package br.ufscar.dc.dsw.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Usuario;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private IUsuarioDAO dao;

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		if (dao != null) {
			Usuario usuario = dao.findByEmail(email);
			return usuario == null;
		} else {
			return true;
		}
	}
}