package br.ufscar.dc.dsw.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.domain.Cliente;

@Component
public class UniqueCpfValidator implements ConstraintValidator<UniqueCpf, String> {

	@Autowired
	private IClienteDAO dao;

	@Override
	public boolean isValid(String cpf, ConstraintValidatorContext context) {
		if (dao != null) {
			Cliente cliente = dao.findByCpf(cpf);
			return cliente == null;
		} else {
			return true;
		}
	}
}