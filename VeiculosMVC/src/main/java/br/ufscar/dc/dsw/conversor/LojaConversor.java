// br/ufscar/dc/dsw/conversor/LojaConversor.java
package br.ufscar.dc.dsw.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.service.spec.ILojaService;

@Component
public class LojaConversor implements Converter<String, Loja> {

    @Autowired
    private ILojaService lojaService;

    @Override
    public Loja convert(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        try {
            Long lojaId = Long.valueOf(id);
            return lojaService.buscarPorId(lojaId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
