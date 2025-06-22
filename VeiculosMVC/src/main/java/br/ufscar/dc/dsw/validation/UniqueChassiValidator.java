package br.ufscar.dc.dsw.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.dao.IVeiculoDAO;
import br.ufscar.dc.dsw.domain.Veiculo;

@Component
public class UniqueChassiValidator implements ConstraintValidator<UniqueChassi, String> {

	@Autowired
	private IVeiculoDAO dao;

	@Override
	public boolean isValid(String chassi, ConstraintValidatorContext context) {
		if (dao != null) {
			Veiculo veiculo = dao.findByChassi(chassi);
			return veiculo == null;
		} else {
			return true;
		}
	}
}