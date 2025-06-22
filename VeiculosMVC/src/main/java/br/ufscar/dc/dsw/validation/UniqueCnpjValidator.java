package br.ufscar.dc.dsw.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.dao.ILojaDAO;
import br.ufscar.dc.dsw.domain.Loja;

@Component
public class UniqueCnpjValidator implements ConstraintValidator<UniqueCnpj, String> {

	@Autowired
	private ILojaDAO dao;

	@Override
	public boolean isValid(String cnpj, ConstraintValidatorContext context) {
		if (dao != null) {
			Loja loja = dao.findByCnpj(cnpj);
			return loja == null;
		} else {
			return true;
		}
	}
}