// br/ufscar/dc/dsw/config/MvcConfig.java
package br.ufscar.dc.dsw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import br.ufscar.dc.dsw.conversor.LojaConversor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private LojaConversor lojaConversor;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(lojaConversor);
    }
}
