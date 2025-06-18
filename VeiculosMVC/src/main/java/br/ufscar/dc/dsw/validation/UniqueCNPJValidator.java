package br.ufscar.dc.dsw.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.dao.ILojaDAO;
import br.ufscar.dc.dsw.domain.Loja;

@Component
public class UniqueCNPJValidator implements ConstraintValidator<UniqueCNPJ, Loja> {

    @Autowired
    private ILojaDAO dao;

    @Override
    public boolean isValid(Loja lojaAtual, ConstraintValidatorContext context) {
        if (lojaAtual == null || dao == null) {
            return true;
        }
        Loja loja = dao.findByCnpj(lojaAtual.getCnpj());
        if (loja == null) {
            return true; // Não existe, é único
        }
        // Permite se for o mesmo id (edição)
        return loja.getId().equals(lojaAtual.getId());
    }
}